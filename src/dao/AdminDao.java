package dao;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import models.Admin;
import utils.DBConnection;

import java.util.List;

import static com.mongodb.client.model.Filters.eq;

// Data access object class of models.Admin to perform CRUD operation in admins collection inside ihealth_db

public class AdminDao{

    // Obtain the MongoDatabase object of database
    private static final MongoDatabase ihealthDB = DBConnection.getConnection();

    // Obtain admins collection
    private static final MongoCollection<Admin> adminsCollection = ihealthDB.getCollection("admins", Admin.class);

    // Method to validate if adminId and password are correct during admin login process
    public static Admin findAdmin(String adminId, String password) {

        Admin admin = adminsCollection.find(eq("adminId", adminId)).first();
        System.out.println("Admin found:\t" + admin);

        if (admin == null) {
            // adminId invalid
            return null;
        }
        else if (Argon2Factory.create().verify(admin.getPassword(), password.toCharArray())) {
            // Hash the passed in password and check if it is the same as the recorded hashed password in database
            // Password correct
            return admin;
        } else {
            // Password incorrect
            return null;
        }
    }

    // Method to create an account credential of an admin
    public static void createAdmin(List<String> adminInfo) {

        // Create instance
        Argon2 argon2 = Argon2Factory.create();

        // Read password from user
        char[] password = adminInfo.get(1).toCharArray();

        try {
            // Hash password
            String hash = argon2.hash(10, 65536, 1, password);

            // create a new admin
            Admin admin = new Admin().setAdminId(adminInfo.get(0)).setPassword(hash).setName(adminInfo.get(2));
            adminsCollection.insertOne(admin);
            System.out.println("Admin inserted");

        } finally {
            // Wipe confidential data
            argon2.wipeArray(password);
        }

    }
}