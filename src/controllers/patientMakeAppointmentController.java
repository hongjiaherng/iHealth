package controllers;


import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import java.io.IOException;
import dao.bookDAO;
import models.book;

public class patientMakeAppointmentController{

    private String date,time, reason;

    @FXML
    private TextField reasontovisit;

    @FXML
    private DatePicker datepicker;

    @FXML
    public void datepicker(){
        date = datepicker.getValue().toString();
    }

    @FXML
    public void time8(ActionEvent actionEvent)  {
        time="8.00am";
    }

    @FXML
    public void time9(ActionEvent actionEvent)  {
        time="9.00am";
    }

    @FXML
    public void time10(ActionEvent actionEvent) {
        time="10.00am";
    }

    @FXML
    public void time11(ActionEvent actionEvent)  {
        time="11.00am";
    }

    @FXML
    public void time6(ActionEvent actionEvent)  {
        time="6.00pm";
    }

    @FXML
    public void time7(ActionEvent actionEvent)  {
        time="7.00pm";
    }

    @FXML
    public void time1(ActionEvent actionEvent)  {
        time="1.00pm";
    }

    @FXML
    public void time2(ActionEvent actionEvent)  {
        time="2.00pm";
    }

    @FXML
    public void time3(ActionEvent actionEvent)  {
        time="3.00pm";
    }

    @FXML
    public void time4(ActionEvent actionEvent)  {
        time="4.00pm";
    }

    @FXML
    public void time5(ActionEvent actionEvent)  {
        time="5.00pm";
    }

    @FXML
    public void cancelonAction(ActionEvent actionEvent)  {
        unsuceccfull();
    }

    @FXML
    public void gettext(){
        reason = reasontovisit.getText();
    }

    @FXML
    public void booknowOnAction(ActionEvent actionEvent) throws IOException{

        book validatedDate = bookDAO.findbook(date, time); // data access object

        if (validatedDate == null){
            sucessbook();
           Parent booksucessfully = FXMLLoader.load(getClass().getResource("../views/bookSuccessView.fxml"));
           Scene booksucessfullyScene = new Scene(booksucessfully);
           Stage appStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
           appStage.setScene(booksucessfullyScene);
           appStage.show();

        }
        else
            unsuceccfull();
        System.out.println("Date booked by other ");
    }

    public void sucessbook() {
       bookDAO.booksucessfull(date, time, reason);
        System.out.println("Book successfully");
    }

    public void unsuceccfull(){
        reasontovisit.setText("");
        time=null;
        date=null;
        datepicker.setValue(null);
    }
}