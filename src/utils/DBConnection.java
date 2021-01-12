package utils;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import java.util.logging.Level;
import java.util.logging.Logger;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

// A class to initialize the connection to MongoDB atlas

public class DBConnection {

    // Assign the connection string needed for a connection to MongoDB
    // shhhh... don't share this to public if you don't want your database in danger
    // Hide the username and password before sharing!!!!!
    private static final ConnectionString connectionString
            = new ConnectionString("mongodb+srv://<username>:<password>@cluster0.cyk1y.mongodb.net/test");
    private static MongoClient mongoClient = null;
    private static MongoDatabase ihealthDB = null;

    // Method to start the connection to MongoDB atlas
    public static void startConnection() {

        // Disable the logging messages of MongoDB in our application's console
        Logger mongoLogger = Logger.getLogger("org.mongodb.driver");
        mongoLogger.setLevel(Level.OFF);

        try {
            // Define the MongoClientsSetting to create a MongoClient
            // Current configuration: we map MongoDB document (Bson data file) directly to our models by defining it to be a POJO class
            // With this configuration, we can perform CRUD operation with the database directly using our well defined objects (POJOs)
            CodecRegistry pojoCodecRegistry = fromProviders(PojoCodecProvider.builder().automatic(true).build());
            CodecRegistry codecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), pojoCodecRegistry);
            MongoClientSettings clientSettings = MongoClientSettings.builder()
                    .applyConnectionString(connectionString)
                    .codecRegistry(codecRegistry)
                    .build();

            mongoClient = MongoClients.create(clientSettings);
            ihealthDB = mongoClient.getDatabase("ihealth_db");      // Obtain ihealth_db, which is the database for this application
            System.out.println("Connection to MongoDB succeeded.");
        } catch (Exception e) {
            System.out.println("Connection to MongoDB failed.");
        }
    }

    // Method to stop the connection to MongoDB atlas
    public static void stopConnection() {
        try {
            mongoClient.close();
            System.out.println("Connection to MongoDB closed.");
        } catch (Exception e) {
            System.out.println("Connection to MongoDB cannot be closed properly.");
        }
    }

    // A getter method to get the connection in the form of MongoDatabase
    public static MongoDatabase getConnection() {
        return ihealthDB;
    }

}
