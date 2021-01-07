package utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;

public class Logger {

    public static void loginSuccess(String username) throws IOException {
        String output = createLogString(username, "SUCCESS");
        writeStringToLog(output);
    }

    public static void loginFailure(String username) throws IOException {
        String output = createLogString(username, "FAILURE");
        writeStringToLog(output);
    }

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

    private static String createLogString(String username, String status) {
        return String.format("%32s : Username = %s (%s)", Instant.now().toString(), username, status);
    }
}
