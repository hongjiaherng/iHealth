package dao;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import models.Admin;
import utils.DBConnection;

import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class AdminDao{

    private static final MongoDatabase ihealthDB = DBConnection.getConnection();

    public static Admin findAdmin(String adminId, String password) {

        MongoCollection<Admin> adminsCollection = ihealthDB.getCollection("admins", Admin.class);
        Admin admin = adminsCollection.find(eq("adminId", adminId)).first();
        System.out.println("Admin found:\t" + admin);

        if (admin == null) {
            return null;
        }
        else if (Argon2Factory.create().verify(admin.getPassword(), password.toCharArray())) {
            return admin;
        } else {
            // Password incorrect
            return null;
        }
    }

    public static void createAdmin(List<String> adminInfo) {
        MongoCollection<Admin> adminsCollection = ihealthDB.getCollection("admins", Admin.class);

        // Create instance
        Argon2 argon2 = Argon2Factory.create();

        // Read password from user
        char[] password = adminInfo.get(1).toCharArray();

        try {
            // Hash password
            String hash = argon2.hash(10, 65536, 1, password);

            // create a new patient
            Admin admin = new Admin().setAdminId(adminInfo.get(0)).setPassword(hash).setName(adminInfo.get(2));
            adminsCollection.insertOne(admin);
            System.out.println("Admin inserted");

        } finally {
            // Wipe confidential data
            argon2.wipeArray(password);
        }

    }
}