package models;

import java.util.Objects;

public class book {
    private String date,time;

    public String getdate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Booking time \nDate : " +date+ "\nTime : " +this.time;

    }
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        book patient = (book) o;
        return Objects.equals(date, patient.date)&& Objects.equals(time, patient.time);
    }
    @Override
    public int hashCode() {
        return Objects.hash(date, time);
    }
}

