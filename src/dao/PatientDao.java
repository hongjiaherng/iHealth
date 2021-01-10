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

public class PatientDao {

    private static final MongoDatabase ihealthDB = DBConnection.getConnection();
    private static final MongoCollection<Patient> patientsCollection = ihealthDB.getCollection("patients", Patient.class);

    public static Patient findPatient(String username, String password) {

        Patient patient = patientsCollection.find((eq("username", username))).first();

        if (patient == null) {
            // Username incorrect
            return null;
        } else if (Argon2Factory.create().verify(patient.getPassword(), password.toCharArray())) {
            System.out.println("Patient found:\t" + patient);
            return patient;
        } else {
            // Password incorrect
            return null;
        }
    }

    public static boolean isExist(String icNo, String username) {

        Patient patient = patientsCollection.find(or(eq("icNo", icNo), eq("username", username))).first();

        return patient != null;
    }

    public static void createPatient(List<String> patientInfo, List<ArrayList<String>> patientBook) {
        // Create instance
        Argon2 argon2 = Argon2Factory.create();

        // Read password from user
        char[] password = patientInfo.get(5).toCharArray();

        try {
            // Hash password
            String hash = argon2.hash(10, 65536, 1, password);

            // create a new patient
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

    public static boolean isAvailable(String date, String time) {
        Patient newPatient = patientsCollection.find(and(eq("confirmDate", date), eq("bookedTime", time))).first();
        return newPatient == null;
    }

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

    public static Patient findAppointments(String username) {
        Patient currentPatient = patientsCollection.find(eq("username", username)).first();
        return currentPatient;
    }

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

    public static boolean dateIsBooked(OperatingDetails operatingDetails) {
        Patient existingPatient = patientsCollection.find(eq("confirmDate", operatingDetails.getDate())).first();
        return existingPatient != null;
    }

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

    public static Patient findIcOrName(String nameOrIc) {

        MongoCollection<Patient> patientMongoCollection = ihealthDB.getCollection("patients", Patient.class);
        Patient currentIcOrName = patientMongoCollection.find(or(eq("icNo", nameOrIc),eq("name",nameOrIc))).first();

        return currentIcOrName;
    }

    public static Iterator<Patient> findAll() {

        FindIterable<Patient> allPatients = patientsCollection.find();

        Iterator<Patient> iterator = allPatients.iterator();
        return iterator;

    }

    public static void editAppointmentList(AppointmentList previousVer, AppointmentList newVer) {
        Patient previousRecord = patientsCollection.find(eq("icNo",previousVer.getIc())).first();
        List<String> date = previousRecord.getConfirmDate();
        List<String> time = previousRecord.getBookedTime();
        List<String> reason = previousRecord.getReason();
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