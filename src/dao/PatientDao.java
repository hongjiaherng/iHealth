package dao;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import models.Patient;
import utils.DBConnection;

import java.util.List;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.or;

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

    public static void createPatient(List<String> patientInfo) {
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
                    .setPassword(hash);
            patientCollection.insertOne(newPatient);
            System.out.println("Patient inserted");

        } finally {
            // Wipe confidential data
            argon2.wipeArray(password);
        }

    }
}
