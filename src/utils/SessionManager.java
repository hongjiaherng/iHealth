package utils;

// might need to do it for storing the current logged in user in database
// then, everytime a user log into the app, it'll check if the user is currently logged in
// not exactly sure what it does yet

import models.Patient;

public class SessionManager {
    private static Patient currentUser = null;

    public static void setSessionUser(Patient patient) {
        currentUser = patient;
    }

    public static Patient getSessionUser() {
        return currentUser;
    }

    public static void destroySession() {
        currentUser = null;
    }
}
