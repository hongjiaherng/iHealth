package models;

import java.util.Objects;

// Normal Appointment object class
// - to contain the details of a appointment
// - not a POJO class which maps with MongoDB collection

public class Appointment {

    // Class fields
    private String username;
    private String reason;
    private String confirmDate;
    private String bookedTime;
    private String remarks;

    // Constructor method
    public Appointment(String username, String reason, String confirmDate, String bookedTime, String remarks) {
        this.username = username;
        this.reason = reason;
        this.confirmDate = confirmDate;
        this.bookedTime = bookedTime;
        this.remarks = remarks;
    }

    // Setters and Getters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getConfirmDate() {
        return confirmDate;
    }

    public void setConfirmDate(String confirmDate) {
        this.confirmDate = confirmDate;
    }

    public String getBookedTime() {
        return bookedTime;
    }

    public void setBookedTime(String bookedTime) {
        this.bookedTime = bookedTime;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    // toString
    @Override
    public String toString() {
        return "Appointment{" +
                "username='" + username + '\'' +
                ", reason='" + reason + '\'' +
                ", confirmDate='" + confirmDate + '\'' +
                ", bookedTime='" + bookedTime + '\'' +
                ", remarks='" + remarks + '\'' +
                '}';
    }

    // equals method for comparing equality of Appointment object
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Appointment that = (Appointment) o;
        return Objects.equals(username, that.username) &&
                Objects.equals(reason, that.reason) &&
                Objects.equals(confirmDate, that.confirmDate) &&
                Objects.equals(bookedTime, that.bookedTime) &&
                Objects.equals(remarks, that.remarks);
    }

    // hashcode
    @Override
    public int hashCode() {
        return Objects.hash(username, reason, confirmDate, bookedTime, remarks);
    }
}
