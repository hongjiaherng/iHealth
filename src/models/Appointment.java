package models;

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
}
