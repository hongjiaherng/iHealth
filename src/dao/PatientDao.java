package dao;


import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.*;
import com.mongodb.client.result.UpdateResult;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import models.Appointment;
import models.Patient;
import org.bson.Document;
import org.bson.conversions.Bson;
import utils.DBConnection;
import utils.SessionManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.mongodb.client.model.Filters.*;

public class PatientDao {

    private static final MongoDatabase ihealthDB = DBConnection.getConnection();

    public static Patient findPatient(String username, String password) {

        if (ihealthDB == null) {
            System.out.println("Connection to MongoDB is not properly set (PatientDao)");
            return null;
        }

        MongoCollection<Patient> patientsCollection = ihealthDB.getCollection("patients", Patient.class);
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

        if (ihealthDB == null) {
            System.out.println("Connection to MongoDB is not properly set (PatientDao)");
            return true;
        }

        MongoCollection<Patient> patientsCollection = ihealthDB.getCollection("patients", Patient.class);
        Patient patient = patientsCollection.find(or(eq("icNo", icNo), eq("username", username))).first();

        if (patient == null) {
            return false;
        }

        return true;
    }

    public static void createPatient(List<String> patientInfo, List<ArrayList<String>> patientBook) {
        MongoCollection<Patient> patientCollection = ihealthDB.getCollection("patients", Patient.class);

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

            patientCollection.insertOne(newPatient);
            System.out.println("Patient inserted");

        } finally {
            // Wipe confidential data
            argon2.wipeArray(password);
        }
    }

    public static boolean isAvailable(String date, String time) {
        if (ihealthDB == null) {
            System.out.println("Connection to MongoDB to find book date is not properly set (PatientDao)");
            return false;
        }

        MongoCollection<Patient> patientsCollection = ihealthDB.getCollection("patients", Patient.class);
        Patient newPatient = patientsCollection.find(and(eq("confirmDate", date), eq("bookedTime", time))).first();

        if (newPatient == null) {
            return true;
        } else {
            return false;
        }
    }

    public static Patient bookAppointment(String username, String date, String time, String reason) {

        if (ihealthDB == null) {
            System.out.println("Connection to MongoDB to save booked date is not properly set (PatientDao)");
            return null;
        }

        MongoCollection<Patient> patientsCollection = ihealthDB.getCollection("patients", Patient.class);
        Patient newPatient = patientsCollection.find(eq("username", username)).first();

        if (newPatient == null) {
            return null;
        } else {
            newPatient.setBookedTime(List.of(time)).setConfirmDate(List.of(date)).setReason(List.of(reason)).setRemarks(List.of(""));
            patientsCollection.findOneAndUpdate(Filters.eq("username", username),Updates.pushEach("bookedTime", List.of(time)));
            patientsCollection.findOneAndUpdate(Filters.eq("username", username),Updates.pushEach("confirmDate", List.of(date)));
            patientsCollection.findOneAndUpdate(Filters.eq("username", username),Updates.pushEach("reason", List.of(reason)));
            patientsCollection.findOneAndUpdate(Filters.eq("username", username),Updates.pushEach("remarks", List.of("")));
            return newPatient;
        }
    }

    public static Patient findAppointment(String username) {

        if (ihealthDB == null) {
            System.out.println("Connection to MongoDB is not properly set (PatientDao)");
            return null;
        }

        MongoCollection<Patient> patientsCollection = ihealthDB.getCollection("patients", Patient.class);
        Patient currentPatient = patientsCollection.find(eq("username", username)).first();

        return currentPatient;
    }

    public static Patient removeSelectedAppointment(Appointment appointment) {

        String username = appointment.getUsername();
        String confirmDate = appointment.getConfirmDate();
        String bookedTime = appointment.getBookedTime();
        String reason = appointment.getReason();
        String remarks = appointment.getRemarks();

        MongoCollection<Patient> patientsCollection = ihealthDB.getCollection("patients", Patient.class);
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
}



