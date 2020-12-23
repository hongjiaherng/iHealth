package dao;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import models.Patient;
import utils.DBConnection;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;

public class PatientDao {

    public static Patient findPatient(String username, String password) {

        MongoDatabase ihealthDB = DBConnection.getConnection();

        if (ihealthDB == null) {
            System.out.println("Connection to MongoDB is not properly set (PatientDao)");
            return null;
        }

        MongoCollection<Patient> patientsCollection = ihealthDB.getCollection("patients", Patient.class);
        Patient patient = patientsCollection.find(and(eq("username", username), eq("password", password))).first();
        System.out.println("Patient found:\t" + patient);

        if (patient == null) {
            return null;
        }

        return patient;

    }

    // TODO: an overload version of findPatient to include ic for the account registration class
}
