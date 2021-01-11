package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminMainPageController {

    // Method that switch to admin login page if the log out button is clicked
    @FXML
    private void logOutButtonOnAction(ActionEvent actionEvent) throws IOException {
        Parent adminLoginRoot = FXMLLoader.load(getClass().getResource("../views/adminLoginView.fxml"));
        Scene adminLoginScene = new Scene(adminLoginRoot);
        Stage appStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        appStage.setScene(adminLoginScene);
        appStage.show();
    }

    // Method that switch to appointment list page if the edit appointment list button is clicked
    @FXML
    private void editAppointmentListButtonOnAction(MouseEvent mouseEvent) throws IOException{
        Parent appointmentListRoot = FXMLLoader.load(getClass().getResource("../views/appointmentListView.fxml"));
        Scene appointmentListScene = new Scene(appointmentListRoot);
        Stage appStage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        appStage.setScene(appointmentListScene);
        appStage.show();
    }

    // Method that switch to clinic details page if the edit operating hours button is clicked
    @FXML
    private void editOperatingHoursButtonOnAction(MouseEvent mouseEvent) throws IOException{
        Parent clinicDetailsRoot = FXMLLoader.load(getClass().getResource("../views/clinicDetailsView.fxml"));
        Scene clinicDetailsScene = new Scene(clinicDetailsRoot);
        Stage appStage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        appStage.setScene(clinicDetailsScene);
        appStage.show();
    }
}
