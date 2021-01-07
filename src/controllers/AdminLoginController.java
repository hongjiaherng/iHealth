package controllers;//package controllers;
//
//import dao.AdminDao;
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Node;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.scene.control.Label;
//import javafx.scene.control.PasswordField;
//import javafx.scene.control.TextField;
//import javafx.stage.Stage;
//import models.Admin;
//import utils.Logger;
//import utils.SessionManager;
//
//import java.io.IOException;
//
//public class AdminLoginController {
//    @FXML
//    private Label loginErrorLabel;
//    @FXML
//    private TextField adminidTextField;
//    @FXML
//    private PasswordField passwordField;
//
//    @FXML
//    public void loginButtonOnAction(ActionEvent actionEvent) throws IOException {
//        if (adminidTextField.getText().isEmpty() || passwordField.getText().isEmpty()) {
//            loginErrorLabel.setText("Admin ID and Password cannot be empty!");
//        } else if (!validateLogin()) {
//            loginErrorLabel.setText("Invalid Admin ID or Password");
//        } else {
//            loginErrorLabel.setText("");
//
//            // Switch to admin main page here
//            Parent adminMainPageRoot = FXMLLoader.load(getClass().getResource("../views/adminMainPageView.fxml"));
//            Scene adminMainPageScene = new Scene(adminMainPageRoot);
//            Stage appStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow(); // obtain the current stage (in the adminloginview)
//            appStage.setScene(adminMainPageScene); // set the scene of the obtained stage into the new scene
//            appStage.show();
//        }
//    }
//
//    @FXML
//    public void backToUserLoginButtonOnAction(ActionEvent actionEvent) throws IOException {
//        // Switch to User Login page here
//        Parent userLoginRoot = FXMLLoader.load(getClass().getResource("../views/patientLoginView.fxml"));
//        Scene userLoginScene = new Scene(userLoginRoot);
//        Stage appStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
//        appStage.setScene(userLoginScene);
//        appStage.show();
//    }
//
//    private boolean validateLogin() throws IOException {
//        String adminid = adminidTextField.getText();
//        String password = passwordField.getText();
//
//        Admin validatedAdmin = AdminDao.findAdmin(adminid, password); // data access object
//
//        if (validatedAdmin == null) {
//            Logger.loginFailure(adminid);
//            return false;
//        }
//
//        SessionManager.setSessionAdmin(validatedAdmin);
//        Logger.loginSuccess(adminid);
//        return true;
//    }
//}
