package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class PatientMainPageController {

    public Text welcomeMsgText;

    public void logoutOnAction(ActionEvent actionEvent) throws IOException {
        Parent accountRegisterRoot = FXMLLoader.load(getClass().getResource("../views/patientMainPageView.fxml"));
        Scene accountRegisterScene = new Scene(accountRegisterRoot);
        Stage appStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        appStage.setScene(accountRegisterScene);
        appStage.show();
    }

    public void makeReservationOnAction(ActionEvent actionEvent) {

    }

    public void checkDetailsOnAction(ActionEvent actionEvent) {

    }
}
