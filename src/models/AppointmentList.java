package models;

import java.util.Objects;

public class AppointmentList implements Comparable{
    private String date;
    private String time;
    private String name;
    private String ic;
    private String reason;
    private String remark;

    public AppointmentList() {
    }

    public AppointmentList(String date, String time, String name, String ic, String reason, String remark) {
        this.date = date;
        this.time = time;
        this.name = name;
        this.ic = ic;
        this.reason = reason;
        this.remark = remark;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setIc(String ic) {
        this.ic = ic;
    }

    public String getIc() {
        return ic;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getReason() {
        return reason;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRemark() {
        return remark;
    }

    @Override
    public String toString() {
        return "AppointmentList{" +
                "date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", name='" + name + '\'' +
                ", ic='" + ic + '\'' +
                ", reason='" + reason + '\'' +
                "remark='" + remark + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AppointmentList that = (AppointmentList) o;
        return Objects.equals(date, that.date) &&
                Objects.equals(time, that.time) &&
                Objects.equals(name, that.name) &&
                Objects.equals(ic, that.ic) &&
                Objects.equals(reason, that.reason) &&
                Objects.equals(remark, that.remark);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, time, name, ic, reason, remark);
    }

    @Override
    public int compareTo(Object o) {
        return this.date.compareTo(((AppointmentList) o).getDate());
    }
}