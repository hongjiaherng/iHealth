//package dao;
//
//import model.Patient;
//
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Statement;
//
//public class PatientDao {
//    public static Patient findPatient(String username, String password) {
//        try {
//            Statement connection = DBConnection.getConnection().createStatement();
//            String patientQuery = "SELECT * FROM patient WHERE username='" + username + "' AND password='" + password + "';" ;
//            ResultSet patientResult = connection.executeQuery(patientQuery);
//
//            if(patientResult.next()) {
//                try{
//                    Patient currentUser = new Patient(patientResult.getInt("userID"), patientResult.getString("username"));
//                    connection.close();
//                    return currentUser;
//                } catch(Exception e) {
//                    System.err.println(e.getLocalizedMessage());
//                    return null;
//                }
//            } else {
//                return null;
//            }
//        }
//        catch(SQLException e) {
//            System.err.println(e.getLocalizedMessage());
//            return null;
//        }
//    }
//}
