package utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;

// An extra class to write the logging message of Patient to a text file in his/her own machine

public class Logger {

    // Method to write the success logging message
    public static void loginSuccess(String username) throws IOException {
        String output = createLogString(username, "SUCCESS");
        writeStringToLog(output);
    }

    // Method to write the failure logging message
    public static void loginFailure(String username) throws IOException {
        String output = createLogString(username, "FAILURE");
        writeStringToLog(output);
    }

    // Method to add on and write the given logging message into text file
    private static void writeStringToLog(String output) throws IOException {
        File file = new File("logging.txt");
        if(!file.exists()) {
            file.createNewFile();
        }
        BufferedWriter writer = new BufferedWriter(new FileWriter(file.getAbsoluteFile(), true));
        writer.write(output);
        writer.newLine();
        writer.close();
    }

    // Method to create the logging message given username and the login status
    private static String createLogString(String username, String status) {
        return String.format("%32s : Username = %s (%s)", Instant.now().toString(), username, status);
    }
}
