package controllers;

import dao.PatientDao;
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
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;

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
    @FXML
    private Label errorMessageLabel;

    @FXML
    private void confirmButtonOnAction(ActionEvent actionEvent) {
        if (!(nameTextField.getText().isEmpty() || icNoTextField.getText().isEmpty() ||
                emailTextField.getText().isEmpty() || phoneNumTextField.getText().isEmpty() ||
                usernameTextField.getText().isEmpty() || passwordField.getText().isEmpty() ||
                confirmPasswordField.getText().isEmpty())) {

            errorMessageLabel.setText("");
            String name = nameTextField.getText();
            String icNo = icNoTextField.getText();
            String email = emailTextField.getText();
            String phoneNum = phoneNumTextField.getText();
            String username = usernameTextField.getText();
            String password = passwordField.getText();
            String confirmPassword = confirmPasswordField.getText();
            ArrayList<String> date = new ArrayList<>();
            ArrayList<String> time = new ArrayList<>();
            ArrayList<String> reason = new ArrayList<>();
            ArrayList<String> remark = new ArrayList<>();

            // Check if username and icNo have existed in database
            if (!PatientDao.isExist(icNo, username)) {
                // Write it to database
                if (password.equals(confirmPassword)) {
                    ArrayList<String> patientInfo = new ArrayList<>();
                    patientInfo.add(name);
                    patientInfo.add(icNo);
                    patientInfo.add(email);
                    patientInfo.add(phoneNum);
                    patientInfo.add(username);
                    patientInfo.add(password);

                    ArrayList<ArrayList<String>> patientBook = new ArrayList<>();
                    patientBook.add(date);
                    patientBook.add(time);
                    patientBook.add(reason);
                    patientBook.add(remark);

                    PatientDao.createPatient(patientInfo,patientBook);

                    errorMessageLabel.setTextFill(Color.rgb(12, 12, 101));
                    errorMessageLabel.setText("Account registered successfully!");

                    PauseTransition delay = new PauseTransition(Duration.millis(500));
                    delay.setOnFinished(
                            e -> {
                                // Back to login page
                                try {
                                    Parent patientLoginRoot = FXMLLoader.load(getClass().getResource("../views/patientLoginView.fxml"));
                                    Scene patientLoginScene = new Scene(patientLoginRoot);
                                    Stage appStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                                    appStage.setScene(patientLoginScene);
                                    appStage.show();
                                } catch (IOException ioException) {
                                    System.out.println("Unable to load FXML file");
                                }
                            }
                    );
                    delay.play();

                } else {
                    errorMessageLabel.setText("Password is not matched");
                }
            } else {
                errorMessageLabel.setText("You already have an existing account");
            }
        } else {
            errorMessageLabel.setText("Please fill up all the fields");
        }
    }

    private void backButtonOnAction(ActionEvent actionEvent) throws IOException {
        Parent patientLoginRoot = FXMLLoader.load(getClass().getResource("../views/patientLoginView.fxml"));
        Scene patientLoginScene = new Scene(patientLoginRoot);
        Stage appStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        appStage.setScene(patientLoginScene);
        appStage.show();
    }
}
