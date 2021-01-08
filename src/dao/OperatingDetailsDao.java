package dao;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import models.OperatingDetails;
import utils.DBConnection;

import java.util.Iterator;

import static com.mongodb.client.model.Filters.eq;

public class OperatingDetailsDao {

    private static final MongoDatabase ihealthDB = DBConnection.getConnection();

    public static OperatingDetails findOperatingHours(String date) {

        if (ihealthDB == null) {
            System.out.println("Connection to MongoDB is not properly set (OperatingDetailsDao)");
            return null;
        }

        MongoCollection<OperatingDetails> operatingDetailsCollection = ihealthDB.getCollection("operatingDetails", OperatingDetails.class);
        OperatingDetails workingDay = operatingDetailsCollection.find((eq("date", date))).first();

        return workingDay;
    }

    public static Iterator<OperatingDetails> findAll() {

        if (ihealthDB == null) {
            System.out.println("Connection to MongoDB is not properly set (OperatingDetailsDao)");
            return null;
        }

        MongoCollection<OperatingDetails> operatingDetailsCollection = ihealthDB.getCollection("operatingDetails", OperatingDetails.class);
        FindIterable<OperatingDetails> operatingDetails = operatingDetailsCollection.find();

        Iterator<OperatingDetails> iterator = operatingDetails.iterator();
        return iterator;

    }
}
