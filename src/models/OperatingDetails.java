package models;

import java.util.Objects;

public class OperatingDetails {

    private String date;
    private String openingTime;
    private String closingTime;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getOpeningTime() {
        return openingTime;
    }

    public void setOpeningTime(String openingTime) {
        this.openingTime = openingTime;
    }

    public String getClosingTime() {
        return closingTime;
    }

    public void setClosingTime(String closingTime) {
        this.closingTime = closingTime;
    }

    @Override
    public String toString() {
        return "OperatingDetails{" +
                "date='" + date + '\'' +
                ", openingTime='" + openingTime + '\'' +
                ", closingTime='" + closingTime + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OperatingDetails that = (OperatingDetails) o;
        return Objects.equals(date, that.date) &&
                Objects.equals(openingTime, that.openingTime) &&
                Objects.equals(closingTime, that.closingTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, openingTime, closingTime);
    }
}
