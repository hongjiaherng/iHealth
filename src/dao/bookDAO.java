package dao;


import com.mongodb.BasicDBObject;
import com.mongodb.client.*;
import com.mongodb.client.result.UpdateResult;
import models.book;
import models.Patient;
import org.bson.Document;
import org.bson.conversions.Bson;
import utils.DBConnection;
import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import org.bson.Document;

public class bookDAO {

    public static book findbook(String date, String time) {

            MongoDatabase ihealthDB = DBConnection.getConnection();

            if (ihealthDB == null) {
                System.out.println("Connection to MongoDB to find book date is not properly set (bookDao)");
                return null;
            }

//            MongoCollection<Patient> patientsCollection = ihealthDB.getCollection("patients", Patient.class);
//            Patient bookdate = patientsCollection.find(and(eq("date", date), eq("time", time))).first();

        MongoCollection patientsCollection = ihealthDB.getCollection("patients");
        book bookdate = (book) patientsCollection.find(and(eq("date", date), eq("time", time))).first();

            if (bookdate == null)
                return null;
            else
                return bookdate;
        }

        public static  void  booksucessfull(String date, String time, String reason) {

            MongoDatabase ihealthDB = DBConnection.getConnection();
            String id = Patient.getId().toString();

            if (ihealthDB == null) {
                System.out.println("Connection to MongoDB to save booked date is not properly set (bookDao)");
            }
            MongoCollection collection = ihealthDB.getCollection("patients");
            Document found  = (Document) collection.find(new Document("_id", id)).first();

            Bson updateDate = new Document("date", date);
            Bson updatedate = new Document("$set", updateDate);
            collection.updateOne(found, updatedate);
            System.out.println("Date updated");

            Bson updateTime= new Document("time", time);
            Bson updatetime = new Document("$set", updateTime);
            collection.updateOne(found, updatetime);
            System.out.println("Time updated");

            Bson updateReason = new Document("reason", reason);
            Bson updatereason = new Document("$set", updateReason);
            collection.updateOne(found, updatereason);
            System.out.println("Reason updated");

//            patientsCollection.updateMany(new BasicDBObject("_id", id),
//                    new BasicDBObject("$set", new BasicDBObject("date", date)),
//                    new BasicDBObject("$set", new BasicDBObject("time", time)),
//                    new BasicDBObject("$set", new BasicDBObject("reason", reason)));
        }
}




