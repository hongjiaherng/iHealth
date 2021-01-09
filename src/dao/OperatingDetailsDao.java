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
    private static final MongoCollection<OperatingDetails> operatingDetailsCollection = ihealthDB.getCollection("operatingDetails", OperatingDetails.class);

    public static OperatingDetails findOperatingHours(String date) {

        OperatingDetails workingDay = operatingDetailsCollection.find((eq("date", date))).first();

        return workingDay;
    }

    public static Iterator<OperatingDetails> findAll() {

        FindIterable<OperatingDetails> operatingDetails = operatingDetailsCollection.find();

        Iterator<OperatingDetails> iterator = operatingDetails.iterator();
        return iterator;

    }

    public static boolean dateIsSet(OperatingDetails operatingDetails) {

        OperatingDetails specifiedDate = operatingDetailsCollection.find(eq("date", operatingDetails.getDate())).first();
        return specifiedDate != null;
    }

    public static void setOperatingHours(OperatingDetails operatingDetails) {
        operatingDetailsCollection.insertOne(operatingDetails);
        System.out.println("Operating details inserted");
    }
}
