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

public class DBConnection {

    private static ConnectionString connectionString = new ConnectionString("mongodb+srv://jherng630:jherng630@cluster0.cyk1y.mongodb.net/ihealth_db?retryWrites=true&w=majority");
    private static MongoClient mongoClient = null;
    private static MongoDatabase ihealthDB = null;

    public static MongoDatabase startConnection() {

        Logger mongoLogger = Logger.getLogger("org.mongodb.driver");
        mongoLogger.setLevel(Level.OFF);

        try {

            CodecRegistry pojoCodecRegistry = fromProviders(PojoCodecProvider.builder().automatic(true).build());
            CodecRegistry codecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), pojoCodecRegistry);
            MongoClientSettings clientSettings = MongoClientSettings.builder()
                    .applyConnectionString(connectionString)
                    .codecRegistry(codecRegistry)
                    .build();

            mongoClient = MongoClients.create(clientSettings);
            ihealthDB = mongoClient.getDatabase("ihealth_db");
            System.out.println("Connection to MongoDB succeeded.");
        } catch (Exception e) {
            System.out.println("Connection to MongoDB failed.");
        }
        return ihealthDB;
    }

    public static void stopConnection() {
        try {
            mongoClient.close();
            System.out.println("Connection to MongoDB closed.");
        } catch (Exception e) {
            System.out.println("Connection to MongoDB cannot be closed properly.");
        }
    }

    public static MongoDatabase getConnection() {
        return ihealthDB;
    }

}
