package dao;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import models.OperatingDetails;
import utils.DBConnection;

import java.util.Iterator;

import static com.mongodb.client.model.Filters.eq;

// Data access object class of models.OperatingDetails to perform CRUD operation in operatingDetails collection inside ihealth_db

public class OperatingDetailsDao {

    // Obtain the MongoDatabase object of database
    private static final MongoDatabase ihealthDB = DBConnection.getConnection();

    // Obtain operatingDetails collection
    private static final MongoCollection<OperatingDetails> operatingDetailsCollection = ihealthDB.getCollection("operatingDetails", OperatingDetails.class);

    // Method to determine the operating hours of the selected date when patient are making appointment
    public static OperatingDetails findOperatingHours(String date) {

        OperatingDetails workingDay = operatingDetailsCollection.find((eq("date", date))).first();

        return workingDay;
    }

    // Method to return all the OperatingDetails document on database in the form of Iterator
    public static Iterator<OperatingDetails> findAll() {

        FindIterable<OperatingDetails> operatingDetails = operatingDetailsCollection.find();

        Iterator<OperatingDetails> iterator = operatingDetails.iterator();
        return iterator;

    }

    // Method to determine if a operating hours with a given date is already set by admin
    public static boolean dateIsSet(OperatingDetails operatingDetails) {

        OperatingDetails specifiedDate = operatingDetailsCollection.find(eq("date", operatingDetails.getDate())).first();
        return specifiedDate != null;
    }

    // Method to insert the operating hours with a date to the database
    public static void setOperatingHours(OperatingDetails operatingDetails) {
        operatingDetailsCollection.insertOne(operatingDetails);
        System.out.println("Operating details inserted");
    }

    // Method to edit the operating hours of a date (change the previous version into the new version of OperatingDetails)
    public static void editOperatingHours(OperatingDetails previousVer, OperatingDetails newVer) {
        String date = previousVer.getDate();
        OperatingDetails previousOperatingDetails = operatingDetailsCollection.findOneAndReplace(eq("date", date), newVer);
    }

    // Method to delete the document of the given OperatingDetails object if admin change the operating hours back to default operating hours
    public static void deleteOperatingDetails(OperatingDetails previousVer) {
        OperatingDetails deletedOperatingDetails = operatingDetailsCollection.findOneAndDelete(eq("date", previousVer.getDate()));
    }
}
