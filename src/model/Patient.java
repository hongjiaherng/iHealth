package model;

public class Patient {
// access modifier
    private int userID;
    private String username;

    public Patient(int userID, String username) {
        this.userID = userID;
        this.username = username;
    }
// getter method
    public int getUserID() {
        return userID;
    }

    public String getUsername() {
        return username;
    }
}
