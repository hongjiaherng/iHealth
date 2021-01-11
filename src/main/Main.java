package main;

import dao.CurrentOnlineUserDao;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import utils.DBConnection;
import utils.SessionManager;

// Entry point of the application (main method)

public class Main extends Application {

    // Execute init() to start the DBConnection to MongoDB before calling start()
    @Override
    public void init() {
        DBConnection.startConnection();
    }

    // Start the application
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../views/patientLoginView.fxml"));
        primaryStage.initStyle(StageStyle.DECORATED);
        primaryStage.setTitle("iHealth");
        primaryStage.getIcons().add(new Image(Main.class.getResourceAsStream("../res/images/patient.png")));
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    // Execute stop() whenever program is closed to disconnect with MongoDB
    @Override
    public void stop() {
        if (SessionManager.getSessionUser() != null) {
            // Delete the document of current user in MongoDB's currentOnlineUser collection to show offline status
            CurrentOnlineUserDao.destroyOnlineSession(SessionManager.getSessionUser().getUsername());
        }
        DBConnection.stopConnection();
        SessionManager.destroySession();    // Destroy the Patient object
    }

    public static void main(String[] args) {
        launch(args);
    }
}
