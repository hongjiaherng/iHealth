package models;

import org.bson.types.ObjectId;
import java.util.Objects;

public class Patient {
    private static ObjectId id;

    // username, password, name, email address, date of birth

//    private ObjectId id;
    private String username;
    private String password;
    private String name;
    private String icNo;
    private String email;
    private String phoneNum;

    // Getter and setter
    public Patient setId(ObjectId id) {
        this.id = id;
        return this;
    }

    public static ObjectId getId() {
        return id;
    }

    public Patient setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public Patient setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public Patient setName(String name) {
        this.name = name;
        return this;
    }

    public String getName() {
        return name;
    }

    public Patient setIcNo(String icNo) {
        this.icNo = icNo;
        return this;
    }

    public String getIcNo() {
        return icNo;
    }

    public Patient setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public Patient setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
        return this;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    // toString()
    @Override
    public String toString() {
        return "Patient{" + "id=" + id +
                ", username=" + username +
                ", password=" + password +
                ", name=" + name +
                ", icNo=" + icNo +
                ", email=" + email +
                ", phoneNum=" + phoneNum +
                "}";
    }

    // equals()
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Patient patient = (Patient) o;
        return Objects.equals(id, patient.id) && Objects.equals(username, patient.username)
                && Objects.equals(password, patient.password) && Objects.equals(name, patient.name)
                && Objects.equals(icNo, patient.icNo) && Objects.equals(email, patient.email)
                && Objects.equals(phoneNum, patient.phoneNum);
    }

    // hashcode()
    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, name, icNo, email, phoneNum);
    }
}
