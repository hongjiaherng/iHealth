package controllers;

import dao.AdminDao;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;
import models.Admin;

import java.io.IOException;

public class AdminLoginController {

    @FXML
    private Label loginErrorLabel;
    @FXML
    private TextField adminIdTextField;
    @FXML
    private PasswordField passwordField;

    @FXML
    public void loginButtonOnAction(ActionEvent actionEvent) throws IOException {
        if (adminIdTextField.getText().isEmpty() || passwordField.getText().isEmpty()) {
            loginErrorLabel.setText("Admin ID and Password cannot be empty!");
        } else if (!validateLogin()) {
            loginErrorLabel.setText("Invalid Admin ID or Password");
        } else {
            loginErrorLabel.setText("");

            PauseTransition delay = new PauseTransition(Duration.millis(1000));
            delay.setOnFinished(e -> {
                        try {
                            // Switch to admin main page here
                            Parent adminMainPageRoot = FXMLLoader.load(getClass().getResource("../views/adminMainPageView.fxml"));
                            Scene adminMainPageScene = new Scene(adminMainPageRoot);
                            Stage appStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                            appStage.setScene(adminMainPageScene);
                            appStage.show();
                        } catch (IOException ioException) {
                            System.out.println("Unable to load FXML file");
                        }
                    }
            );
            delay.play();
        }
    }

    @FXML
    public void backToPatientLoginButtonOnAction(ActionEvent actionEvent) throws IOException {
        // Switch to User Login page here
        Parent userLoginRoot = FXMLLoader.load(getClass().getResource("../views/patientLoginView.fxml"));
        Scene userLoginScene = new Scene(userLoginRoot);
        Stage appStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        appStage.setScene(userLoginScene);
        appStage.show();
    }

    private boolean validateLogin() {
        String adminId = adminIdTextField.getText();
        String password = passwordField.getText();

        Admin validatedAdmin = AdminDao.findAdmin(adminId, password);

        if (validatedAdmin == null) {
            return false;
        }

        return true;
    }

//    @Override
//    public void initialize(URL url, ResourceBundle resourceBundle) {
//        List<String> adminInfo=new ArrayList<>();
//        adminInfo.add("admin0");
//        adminInfo.add("admin0");
//        adminInfo.add("Admin");
//        AdminDao.createAdmin(adminInfo);
//    }
}

