package controllers;

import dao.PatientDao;
import javafx.animation.PauseTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;
import models.Patient;
import utils.Logger;
import utils.SessionManager;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class PatientLoginController {

    @FXML
    private Label loginErrorLabel;
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordField;

    @FXML
    public void loginButtonOnAction(ActionEvent actionEvent) throws IOException {
        if (usernameTextField.getText().isEmpty() || passwordField.getText().isEmpty()) {
            loginErrorLabel.setText("Username and Password cannot be empty!");
        } else if (!validateLogin()) {
            loginErrorLabel.setText("Invalid Username or Password");
        } else {
            loginErrorLabel.setText("");

            PauseTransition delay = new PauseTransition(Duration.millis(1000));
            delay.setOnFinished(
                    e -> {
                        // Back to login page
                        try {
                            // Switch to patient dashboard here
                            Parent patientMainPageRoot = FXMLLoader.load(getClass().getResource("../views/patientMainPageView.fxml"));
                            Scene patientMainPageScene = new Scene(patientMainPageRoot);
                            Stage appStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow(); // obtain the current stage (in the patientloginview)
                            appStage.setScene(patientMainPageScene); // set the scene of the obtained stage into the new scene
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
    public void createAccButtonOnAction(ActionEvent actionEvent) throws IOException {
        // Switch to account registration here
        Parent accountRegisterRoot = FXMLLoader.load(getClass().getResource("../views/patientRegistrationView.fxml"));
        Scene accountRegisterScene = new Scene(accountRegisterRoot);
        Stage appStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        appStage.setScene(accountRegisterScene);
        appStage.show();
    }

    private boolean validateLogin() throws IOException {
        String username = usernameTextField.getText();
        String password = passwordField.getText();

        Patient validatedPatient = PatientDao.findPatient(username, password); // data access object

        if (validatedPatient == null) {
            Logger.loginFailure(username);
            return false;
        }

        SessionManager.setSessionUser(validatedPatient);
        Logger.loginSuccess(username);
        return true;
    }

    public void adminLoginButtonOnAction(ActionEvent actionEvent) throws IOException {
        Parent adminLoginRoot = FXMLLoader.load(getClass().getResource("../views/adminLoginView.fxml"));
        Scene adminLoginScene = new Scene(adminLoginRoot);
        Stage appStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        appStage.setScene(adminLoginScene);
        appStage.show();
    }
}
