package dao;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import models.CurrentOnlineUser;
import utils.DBConnection;

import static com.mongodb.client.model.Filters.eq;

public class CurrentOnlineUserDao {

    private static final MongoDatabase ihealthDB = DBConnection.getConnection();
    private static final MongoCollection<CurrentOnlineUser> currentOnlineUsersCollection = ihealthDB.getCollection("currentOnlineUsers", CurrentOnlineUser.class);

    public static void createOnlineUser(String username, String loginTime) {
        CurrentOnlineUser currentOnlineUser = new CurrentOnlineUser()
                .setUsername(username)
                .setLoginTime(loginTime);

        currentOnlineUsersCollection.insertOne(currentOnlineUser);
    }

    public static boolean isOnline(String username) {
        CurrentOnlineUser currentOnlineUser = currentOnlineUsersCollection.find(eq("username", username)).first();
        return currentOnlineUser != null;
    }

    public static void destroyOnlineSession(String username) {
        currentOnlineUsersCollection.findOneAndDelete(eq("username", username));
    }

}
