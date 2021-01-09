package models;

import java.util.Objects;

// Not a POJO class

public class Appointment {

    private String username;
    private String reason;
    private String confirmDate;
    private String bookedTime;
    private String remarks;

    public Appointment(String username, String reason, String confirmDate, String bookedTime, String remarks) {
        this.username = username;
        this.reason = reason;
        this.confirmDate = confirmDate;
        this.bookedTime = bookedTime;
        this.remarks = remarks;
    }

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

    @Override
    public int hashCode() {
        return Objects.hash(username, reason, confirmDate, bookedTime, remarks);
    }
}
