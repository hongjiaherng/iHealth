package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class PatientRegistrationController {

    @FXML
    private TextField nameTextField;
    @FXML
    private TextField icNoTextField;
    @FXML
    private TextField emailTextField;
    @FXML
    private TextField phoneNumTextField;
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField confirmPasswordField;

    public void confirmButtonOnAction(ActionEvent actionEvent) {
        if (!(nameTextField.getText().isEmpty() || icNoTextField.getText().isEmpty() || emailTextField.getText().isEmpty() || phoneNumTextField.getText().isEmpty() || usernameTextField.getText().isEmpty() || passwordField.getText().isEmpty() || confirmPasswordField.getText().isEmpty())) {
            // TODO: here
            // use patientdao to search for if username or ic repeatedly used
            // if ok, add it as a bson file to mongodb
        }
    }

    public void backButtonOnAction(ActionEvent actionEvent) {

    }
}
