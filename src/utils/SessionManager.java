package utils;

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
