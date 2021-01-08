package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import utils.DBConnection;
import utils.SessionManager;

public class Main extends Application {

    @Override
    public void init() {
        DBConnection.startConnection();
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../views/patientLoginView.fxml"));
        primaryStage.initStyle(StageStyle.DECORATED);
        primaryStage.setTitle("iHealth v0");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    @Override
    public void stop() {
        DBConnection.stopConnection();
        SessionManager.destroySession();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
