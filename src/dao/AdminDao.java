package dao;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import models.Admin;
import utils.DBConnection;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;

public class AdminDao {
    public static Admin findAdmin(String adminid, String password) {

        MongoDatabase ihealthDB = DBConnection.getConnection();

        if (ihealthDB == null) {
            System.out.println("Connection to MongoDB is not properly set (AdminDao)");
            return null;
        }

        MongoCollection<Admin> adminsCollection = ihealthDB.getCollection("admins", Admin.class);
        Admin admin = adminsCollection.find(and(eq("adminid", adminid), eq("password", password))).first();
        System.out.println("Admin found:\t" + admin);

        if (admin == null) {
            return null;
        }

        return admin;

    }
}
