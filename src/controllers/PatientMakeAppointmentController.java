package controllers;


import dao.PatientDao;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.util.ArrayList;

import models.Patient;


public class PatientMakeAppointmentController {

    private String date,time, reason;
    ArrayList<String> datebooked = new ArrayList<String>();
    ArrayList<String> timebooked = new ArrayList<String>();
    ArrayList<String> reasonToVisit = new ArrayList<String>();


    @FXML
    private TextField reasontovisit;

    @FXML
    private DatePicker datepicker;

    @FXML
    private TextField showtime;

    @FXML
    private Text bookfail;

    @FXML
    public void datepicker(){
        date = datepicker.getValue().toString();

    }

    @FXML
    public void time8(ActionEvent actionEvent)  {
        time="8.00am";
        showtime.setText(time);
    }

    @FXML
    public void time9(ActionEvent actionEvent)  {
        time="9.00am";showtime.setText(time);
    }

    @FXML
    public void time10(ActionEvent actionEvent) {
        time="10.00am";showtime.setText(time);
    }

    @FXML
    public void time11(ActionEvent actionEvent)  {
        time="11.00am";showtime.setText(time);
    }

    @FXML
    public void time6(ActionEvent actionEvent)  {
        time="6.00pm";showtime.setText(time);
    }

    @FXML
    public void time7(ActionEvent actionEvent)  {
        time="7.00pm";showtime.setText(time);
    }

    @FXML
    public void time1(ActionEvent actionEvent)  {
        time="1.00pm";showtime.setText(time);
    }

    @FXML
    public void time2(ActionEvent actionEvent)  {
        time="2.00pm";showtime.setText(time);
    }

    @FXML
    public void time3(ActionEvent actionEvent)  {
        time="3.00pm";showtime.setText(time);
    }

    @FXML
    public void time4(ActionEvent actionEvent)  {
        time="4.00pm";showtime.setText(time);
    }

    @FXML
    public void time5(ActionEvent actionEvent)  {
        time="5.00pm";showtime.setText(time);
    }

    @FXML
    public void cancelonAction(ActionEvent actionEvent)  {
        unsuceccfull();
    }

    @FXML
    public void backOnAction(ActionEvent actionEvent) throws IOException {
        Parent booksucessfully = FXMLLoader.load(getClass().getResource("../views/patientMainPageView.fxml"));
        Scene booksucessfullyScene = new Scene(booksucessfully);
        Stage appStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        appStage.setScene(booksucessfullyScene);
        appStage.show();
    }


    @FXML
    public void booknowOnAction(ActionEvent actionEvent) throws IOException{

        reason = reasontovisit.getText();
        datebooked.add(date);
        timebooked.add(time);
        reasonToVisit.add(reason);

        Patient validatedDate = PatientDao.findbook(date, time); // data access object

        if (validatedDate == null){
            sucessbook();
           Parent booksucessfully = FXMLLoader.load(getClass().getResource("../views/bookSuccessView.fxml"));
           Scene booksucessfullyScene = new Scene(booksucessfully);
           Stage appStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
           appStage.setScene(booksucessfullyScene);
           appStage.show();

        }
        else {
            unsuceccfull();
            System.out.println("Date booked by other ");
            bookfail.setText("Sorry, date booked by other");

        }
    }

    public void sucessbook() {
        Patient newPatient = PatientDao.booksucessfull(datebooked, timebooked, reasonToVisit);
        System.out.println("Book successfully");
    }

    public void unsuceccfull(){
        reasontovisit.setText("");
        time=null;
        date=null;
        datepicker.getEditor().clear();
        showtime.setText(null);
    }
}