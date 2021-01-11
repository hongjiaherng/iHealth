package dao;


import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.*;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import models.*;
import org.bson.Document;
import utils.DBConnection;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.mongodb.client.model.Filters.*;

// Data access object class of models.Patient to perform CRUD operation in patients collection inside ihealth_db

public class PatientDao {

    // Obtain the MongoDatabase object of database
    private static final MongoDatabase ihealthDB = DBConnection.getConnection();

    // Obtain patients collection
    private static final MongoCollection<Patient> patientsCollection = ihealthDB.getCollection("patients", Patient.class);

    // Method to validate if the username and password of a login user is correct in patient login page
    public static Patient findPatient(String username, String password) {

        Patient patient = patientsCollection.find((eq("username", username))).first();

        if (patient == null) {
            // Username incorrect
            return null;
        } else if (Argon2Factory.create().verify(patient.getPassword(), password.toCharArray())) {
            // Hash the passed in password and check if it is the same as the recorded hashed password in database
            // Password correct
            System.out.println("Patient found:\t" + patient);
            return patient;
        } else {
            // Password incorrect
            return null;
        }
    }

    // Method to check if a patient account is already exist with his/her unique identifiers, ic number & username
    public static boolean isExist(String icNo, String username) {

        Patient patient = patientsCollection.find(or(eq("icNo", icNo), eq("username", username))).first();

        return patient != null;
    }

    // Method to create a Patient document in database when patient is creating a new account
    public static void createPatient(List<String> patientInfo, List<ArrayList<String>> patientBook) {
        // Create instance
        Argon2 argon2 = Argon2Factory.create();

        // Read password from user
        char[] password = patientInfo.get(5).toCharArray();

        try {
            // Hash password
            String hash = argon2.hash(10, 65536, 1, password);

            // create a new patient where the password is being hashed
            Patient newPatient = new Patient().setName(patientInfo.get(0))
                    .setIcNo(patientInfo.get(1))
                    .setEmail(patientInfo.get(2))
                    .setPhoneNum(patientInfo.get(3))
                    .setUsername(patientInfo.get(4))
                    .setPassword(hash)
                    .setBookedTime(patientBook.get(0))
                    .setConfirmDate(patientBook.get(1))
                    .setReason(patientBook.get(2))
                    .setRemarks(patientBook.get(3));

            patientsCollection.insertOne(newPatient);
            System.out.println("Patient inserted");

        } finally {
            // Wipe confidential data
            argon2.wipeArray(password);
        }
    }

    // Method to check if a selected timeslot is available for making new appointment
    public static boolean isAvailable(String date, String time) {
        Patient newPatient = patientsCollection.find(and(eq("confirmDate", date), eq("bookedTime", time))).first();
        return newPatient == null;
    }

    // Method to book the appointment by insert new appointment records into the existing patient document on database
    public static Patient bookAppointment(Appointment appointment) {
        Patient newPatient = patientsCollection.find(eq("username", appointment.getUsername())).first();

        if (newPatient == null) {
            return null;
        } else {
            newPatient.setBookedTime(List.of(appointment.getBookedTime())).
                    setConfirmDate(List.of(appointment.getConfirmDate())).
                    setReason(List.of(appointment.getReason())).
                    setRemarks(List.of(appointment.getRemarks()));
            patientsCollection.findOneAndUpdate(Filters.eq("username", appointment.getUsername()),Updates.pushEach("bookedTime", List.of(appointment.getBookedTime())));
            patientsCollection.findOneAndUpdate(Filters.eq("username", appointment.getUsername()),Updates.pushEach("confirmDate", List.of(appointment.getConfirmDate())));
            patientsCollection.findOneAndUpdate(Filters.eq("username", appointment.getUsername()),Updates.pushEach("reason", List.of(appointment.getReason())));
            patientsCollection.findOneAndUpdate(Filters.eq("username", appointment.getUsername()),Updates.pushEach("remarks", List.of(appointment.getRemarks())));
            return newPatient;
        }
    }

    // Method to find the existing appointments of a patient by using username and return it in the form of Patient
    public static Patient findAppointments(String username) {
        Patient currentPatient = patientsCollection.find(eq("username", username)).first();
        return currentPatient;
    }

    // Method to delete selected appointment from database
    public static Patient removeSelectedAppointment(Appointment appointment) {

        String username = appointment.getUsername();
        String confirmDate = appointment.getConfirmDate();
        String bookedTime = appointment.getBookedTime();
        String reason = appointment.getReason();
        String remarks = appointment.getRemarks();

        Patient existingPatient = patientsCollection.find(eq("username", username)).first();

        if (existingPatient != null) {

            existingPatient.getConfirmDate().remove(confirmDate);
            existingPatient.getBookedTime().remove(bookedTime);
            existingPatient.getReason().remove(reason);
            existingPatient.getRemarks().remove(remarks);

            Document filterByUsername = new Document("username", username);
            FindOneAndReplaceOptions returnDocAfterReplace = new FindOneAndReplaceOptions().returnDocument(ReturnDocument.AFTER);
            existingPatient = patientsCollection.findOneAndReplace(filterByUsername, existingPatient, returnDocAfterReplace);

        }
        return existingPatient;
    }

    // Method to determine if a date has already contained any appointments from the patients
    public static boolean dateIsBooked(OperatingDetails operatingDetails) {
        Patient existingPatient = patientsCollection.find(eq("confirmDate", operatingDetails.getDate())).first();
        return existingPatient != null;
    }

    // Method to find the appointment dates which are within a week from current time of a patient
    public static List<LocalDate> findDatesWithinAWeek(String username, LocalDate currentDate) {

        Patient existingPatient = patientsCollection.find(eq("username", username)).first();
        List<LocalDate> datesWithinAWeek = new ArrayList<>();

        if (existingPatient != null) {
            List<String> allConfirmDate = existingPatient.getConfirmDate();

            for (String date : allConfirmDate) {
                // check if date is within the currentDate to the next 7 days
                LocalDate appointmentDate = LocalDate.parse(date);
                LocalDate curentWeek = currentDate.plusWeeks(1);
                if (currentDate.compareTo(appointmentDate) <= 0 && appointmentDate.compareTo(curentWeek) < 0) {
                    // It's within the next 7 days
                    datesWithinAWeek.add(appointmentDate);
                }
            }
        }
        return datesWithinAWeek;
    }

    // Method to get the patient instance by using name or ic
    public static Patient findIcOrName(String nameOrIc) {

        Patient currentIcOrName = patientsCollection.find(or(eq("icNo", nameOrIc),eq("name",nameOrIc))).first();
        return currentIcOrName;
    }

    // Method to return all the documents in patients collection in the form of Iterator
    public static Iterator<Patient> findAll() {

        FindIterable<Patient> allPatients = patientsCollection.find();
        Iterator<Patient> iterator = allPatients.iterator();
        return iterator;
    }

    // Method to edit the appointment details of a patient from the previous version to new version
    public static void editAppointmentList(AppointmentList previousVer, AppointmentList newVer) {
        Patient previousRecord = patientsCollection.find(eq("icNo",previousVer.getIc())).first();
        assert previousRecord != null;
        List<String> date = previousRecord.getConfirmDate();
        List<String> time = previousRecord.getBookedTime();
        List<String> remarks = previousRecord.getRemarks();
        int index = 0;
        for (int i = 0; i < date.size(); i++) {
            if(time.get(i).equals(previousVer.getTime()) && date.get(i).equals(previousVer.getDate())){
                index = i;
                break;
            }
        }
        remarks.add(index, newVer.getRemark());
        remarks.remove(index+1);
        previousRecord.setRemarks(remarks);
        String ic = previousVer.getIc();
        Patient previousAppointmentList = patientsCollection.findOneAndReplace(and(eq("confirmDate", date),eq("icNo",ic)),previousRecord);
    }
}