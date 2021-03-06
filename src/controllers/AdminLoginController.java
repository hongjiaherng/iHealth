package controllers;

import dao.AdminDao;
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
import models.Admin;

import java.io.IOException;

public class AdminLoginController {

    @FXML
    private Label loginErrorLabel;
    @FXML
    private TextField adminIdTextField;
    @FXML
    private PasswordField passwordField;

    // Method that switch to admin main page if login button is clicked
    @FXML
    private void loginButtonOnAction(ActionEvent actionEvent) throws IOException {
        // Check whether the admin id or password is empty and the validity of the admin id or password
        if (adminIdTextField.getText().isEmpty() || passwordField.getText().isEmpty()) {
            loginErrorLabel.setText("Admin ID or Password cannot be empty!");
        } else if (!validateLogin()) {
            loginErrorLabel.setText("Invalid Admin ID or Password");
        } else {
            loginErrorLabel.setText("");
            // Switch to admin main page here
            Parent adminMainPageRoot = FXMLLoader.load(getClass().getResource("../views/adminMainPageView.fxml"));
            Scene adminMainPageScene = new Scene(adminMainPageRoot);
            Stage appStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            appStage.setScene(adminMainPageScene);
            appStage.show();
        }
    }

    // Method that switch to patient login page if the back button is clicked
    @FXML
    private void backToPatientLoginButtonOnAction(ActionEvent actionEvent) throws IOException {
        // Switch to User Login page here
        Parent userLoginRoot = FXMLLoader.load(getClass().getResource("../views/patientLoginView.fxml"));
        Scene userLoginScene = new Scene(userLoginRoot);
        Stage appStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        appStage.setScene(userLoginScene);
        appStage.show();
    }

    // Method to validate the admin id and password
    private boolean validateLogin() {
        String adminId = adminIdTextField.getText();
        String password = passwordField.getText();

        Admin validatedAdmin = AdminDao.findAdmin(adminId, password);

        if (validatedAdmin == null) {
            return false;
        }

        return true;
    }
}

