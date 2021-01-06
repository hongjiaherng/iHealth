package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.awt.event.ActionEvent;
import java.io.IOException;

    public class BookSuccessController {

        @FXML
        public void backOnAction(javafx.event.ActionEvent actionEvent) throws IOException {
            Parent patientMakeAppointmentRoot = FXMLLoader.load(getClass().getResource("../views/patientMakeAppointmentView.fxml"));
            Scene patientMakeAppointmentScene = new Scene(patientMakeAppointmentRoot);
            Stage appStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            appStage.setScene(patientMakeAppointmentScene);
            appStage.show();
        }
    }

