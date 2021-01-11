package controllers;

import dao.CurrentOnlineUserDao;
import dao.PatientDao;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import models.Patient;
import utils.Logger;
import utils.SessionManager;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.time.LocalDateTime;

public class PatientLoginController {

    @FXML
    private Label loginErrorLabel;
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordField;

    @FXML
    private void loginButtonOnAction(ActionEvent actionEvent) throws IOException {
        if (usernameTextField.getText().isEmpty() || passwordField.getText().isEmpty()) {
            loginErrorLabel.setText("Username or Password cannot be empty!");
        } else if (validateLogin()) {
            loginErrorLabel.setText("");
            // Switch to patient dashboard here
            Parent patientMainPageRoot = FXMLLoader.load(getClass().getResource("../views/patientMainPageView.fxml"));
            Scene patientMainPageScene = new Scene(patientMainPageRoot);
            Stage appStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow(); // obtain the current stage (in the patientloginview)
            appStage.setScene(patientMainPageScene); // set the scene of the obtained stage into the new scene
            appStage.show();
        }
    }

    @FXML
    private void createAccButtonOnAction(ActionEvent actionEvent) throws IOException {
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
            loginErrorLabel.setText("Invalid Username or Password");
            return false;
        }
        if (CurrentOnlineUserDao.isOnline(username)) {
            loginErrorLabel.setText("You had already signed into the system");
            return false;
        }
        CurrentOnlineUserDao.createOnlineUser(username, LocalDateTime.now().toString());
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
