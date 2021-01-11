package dao;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import models.CurrentOnlineUser;
import utils.DBConnection;

import static com.mongodb.client.model.Filters.eq;

// Data access object class of models.CurrentOnlineUser to perform CRUD operation in currentOnlineUsers collection inside ihealth_db
// - to ensure a patient can't log into the app on multiple devices simultaneously

public class CurrentOnlineUserDao {

    // Obtain the MongoDatabase object of database
    private static final MongoDatabase ihealthDB = DBConnection.getConnection();

    // Obtain currentOnlineUsers collection
    private static final MongoCollection<CurrentOnlineUser> currentOnlineUsersCollection = ihealthDB.getCollection("currentOnlineUsers", CurrentOnlineUser.class);

    // Method to insert an online user document on ihealth_db when patient is logged into the client side of application
    public static void createOnlineUser(String username, String loginTime) {
        CurrentOnlineUser currentOnlineUser = new CurrentOnlineUser()
                .setUsername(username)
                .setLoginTime(loginTime);

        currentOnlineUsersCollection.insertOne(currentOnlineUser);
    }

    // Method to check if a patient is currently online to ensure patient can't log into the app on multiple devices
    public static boolean isOnline(String username) {
        CurrentOnlineUser currentOnlineUser = currentOnlineUsersCollection.find(eq("username", username)).first();
        return currentOnlineUser != null;
    }

    // Method to delete the online user document on ihealth_db when patient is logged out from the client side of application
    public static void destroyOnlineSession(String username) {
        currentOnlineUsersCollection.findOneAndDelete(eq("username", username));
    }
}
