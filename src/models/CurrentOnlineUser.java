package models;

import org.bson.types.ObjectId;

// POJO class (Plain Old Java Object) of currentOnlineUsers (a collection in ihealth_db database)
// - to automatically map MongoDB documents (in currentOnlineUsers collection) to Plain Old Java Objects (POJOs)

public class CurrentOnlineUser {

    // Class fields
    private ObjectId id;
    private String username;
    private String loginTime;

    // Getters and setters
    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public CurrentOnlineUser setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getLoginTime() {
        return loginTime;
    }

    public CurrentOnlineUser setLoginTime(String loginTime) {
        this.loginTime = loginTime;
        return this;
    }
}
