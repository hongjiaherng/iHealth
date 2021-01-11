package utils;

import models.Patient;

// An useful helper class to obtain the current instance of Patient when we are in the runtime

public class SessionManager {
    // Class field
    private static Patient currentUser = null;

    // Setter
    public static void setSessionUser(Patient patient) {
        currentUser = patient;
    }

    // Getter
    public static Patient getSessionUser() {
        return currentUser;
    }

    // Method to destroy the instance when patient logs out
    public static void destroySession() {
        currentUser = null;
    }
}
