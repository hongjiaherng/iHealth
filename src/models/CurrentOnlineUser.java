package models;

import org.bson.types.ObjectId;

// POJO class

public class CurrentOnlineUser {

    private ObjectId id;
    private String username;
    private String loginTime;

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
