package models;

import java.util.Objects;

// POJO class

public class OperatingDetails {

    private String date;
    private String openingTime;
    private String closingTime;
    private String remark;

    public String getDate() {
        return date;
    }

    public OperatingDetails setDate(String date) {
        this.date = date;
        return this;
    }

    public String getOpeningTime() {
        return openingTime;
    }

    public OperatingDetails setOpeningTime(String openingTime) {
        this.openingTime = openingTime;
        return this;
    }

    public String getClosingTime() {
        return closingTime;
    }

    public OperatingDetails setClosingTime(String closingTime) {
        this.closingTime = closingTime;
        return this;
    }

    public String getRemark() {
        return remark;
    }

    public OperatingDetails setRemark(String remark) {
        this.remark = remark;
        return this;
    }

    @Override
    public String toString() {
        return "OperatingDetails{" +
                "date='" + date + '\'' +
                ", openingTime='" + openingTime + '\'' +
                ", closingTime='" + closingTime + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OperatingDetails that = (OperatingDetails) o;
        return Objects.equals(date, that.date) &&
                Objects.equals(openingTime, that.openingTime) &&
                Objects.equals(closingTime, that.closingTime) &&
                Objects.equals(remark, that.remark);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, openingTime, closingTime, remark);
    }
}
