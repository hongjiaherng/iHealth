package controller;

//import dao.PatientDao;
import javafx.scene.Node;
import javafx.stage.Stage;
import model.Patient;
import utils.Logger;
import utils.SessionManager;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;



public class PatientLoginController implements Initializable {

    @FXML
    private Label usernameLabel;
    @FXML
    private Label passwordLabel;
    @FXML
    private Label patientLoginLabel;
    @FXML
    private Label iHealthLabel;
    @FXML
    private Label errorLoginLabel;
    @FXML
    private ImageView clinicIconImageView;
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button loginButton;
    @FXML
    private Button registerButton;
    @FXML
    private Button adminPageButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        File clinicIconFile = new File("src/images/clinic-icon.png");
        Image clinicIconImage = new Image(clinicIconFile.toURI().toString());
        clinicIconImageView.setImage(clinicIconImage);
    }

    @FXML
    public void loginButtonOnAction(ActionEvent event) throws IOException {
        if (!validateLogin()) {
            return;
        }

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/patientDashboardView.fxml"));
        Parent addProductParent = loader.load();
        Scene addProductScene = new Scene(addProductParent);
//        PatientDashboardController controller = loader.getController();
//        controller.checkForAppointments();
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(addProductScene);
        window.show();
    }


    private boolean validateLogin() throws IOException {
        String username = this.usernameTextField.getText();
        String password = this.passwordField.getText();
        this.errorLoginLabel.setText("");

        if (username.isEmpty() || password.isEmpty()) {
            this.errorLoginLabel.setText("Incorrect username or password.");
            return false;
        }

//        Patient validated = PatientDao.findPatient(username, password);
//
//        if (validated == null) {
//            this.errorLoginLabel.setText("Incorrect username or password.");
//            Logger.loginFailure(username);
//            return false;
//        }
//
//        SessionManager.setSessionUser(validated);
        Logger.loginSuccess(username);
        return true;

    }


}
