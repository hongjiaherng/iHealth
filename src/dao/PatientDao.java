package dao;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.*;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import models.Patient;
import org.bson.Document;
import org.bson.conversions.Bson;
import utils.DBConnection;
import utils.SessionManager;

import java.util.ArrayList;
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

    public static void createPatient(List<String> patientInfo, List<ArrayList<String>> patientbook) {
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
                    .setBookedTime(patientbook.get(0))
                    .setConfirmDate(patientbook.get(1))
                    .setReason(patientbook.get(2));

            patientCollection.insertOne(newPatient);
            System.out.println("Patient inserted");

        } finally {
            // Wipe confidential data
            argon2.wipeArray(password);
        }

    }

    public static Patient findbook( String date, String time) {
        int i =0;

        if (ihealthDB == null) {
            System.out.println("Connection to MongoDB to find book date is not properly set (PatientDao)");
            return null;
        }

        MongoCollection<Patient> patientsCollection = ihealthDB.getCollection("patients", Patient.class);
        Patient bookdate = patientsCollection.find(and(eq("confirmDate", date), eq("bookedTime", time))).first();
        return bookdate;

    }

    public static Patient booksucessfull( ArrayList<String> date,  ArrayList<String>  time,  ArrayList<String> reason) {

        if (ihealthDB == null) {
            System.out.println("Connection to MongoDB to save booked date is not properly set (bookDao)");
        }

        String username = SessionManager.getSessionUser().getUsername();
        MongoCollection<Patient> patientsCollection = ihealthDB.getCollection("patients", Patient.class);
        Patient patient = patientsCollection.find((eq("username", username))).first();

        if (patient == null) {
            System.out.println("The patients did not exits");
            return null;
        } else {
            System.out.println("Patients exits");

            patient.setBookedTime(time).setConfirmDate(date).setReason(reason);

//            Document filterByUsername = new Document("username", patient.getUsername());
//            FindOneAndReplaceOptions returnAfterReplace = new FindOneAndReplaceOptions().returnDocument(ReturnDocument.AFTER);
//            Patient updatedPatient = patientsCollection.findOneAndReplace(filterByUsername, patient, returnAfterReplace);
//            System.out.println("Patient replaced : " + updatedPatient);
//            return updatedPatient;

             patientsCollection.findOneAndUpdate(Filters.eq("username", patient.getUsername()),Updates.pushEach("bookedTime", time));
             patientsCollection.findOneAndUpdate(Filters.eq("username", patient.getUsername()),Updates.pushEach("confirmDate", date));
             patientsCollection.findOneAndUpdate(Filters.eq("username", patient.getUsername()),Updates.pushEach("reason", reason));
             return null;
        }
    }
}



