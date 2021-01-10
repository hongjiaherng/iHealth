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

public class Main extends Application {

    @Override
    public void init() {
        DBConnection.startConnection();
    }

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

    @Override
    public void stop() {
        if (SessionManager.getSessionUser() != null) {
            CurrentOnlineUserDao.destroyOnlineSession(SessionManager.getSessionUser().getUsername());
        }
        DBConnection.stopConnection();
        SessionManager.destroySession();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
