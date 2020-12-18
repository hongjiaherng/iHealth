package model;

import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

import java.util.Date;
import java.util.Objects;

public class Patient {

    // username, password, name, email address, date of birth

    private ObjectId id;
    private String username;
    private String password;
    private String email;
    @BsonProperty(value = "first_name")
    private String firstName;
    @BsonProperty(value = "last_name")
    private String lastName;
    private Date dob;

    // Getter and setter
    public ObjectId getId() {
        return id;
    }

    public Patient setId(ObjectId id) {
        this.id = id;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public Patient setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public Patient setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public Patient setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public Patient setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public Patient setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public Date getDob() {
        return dob;
    }

    public Patient setDob(Date dob) {
        this.dob = dob;
        return this;
    }

    // toString()
    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Patient{");
        sb.append("id=").append(id);
        sb.append(", username=").append(username);
        sb.append(", password=").append(password);
        sb.append(", email=").append(email);
        sb.append(", first_name=").append(firstName).append(", last_name=").append(lastName);
        sb.append(", dob=").append(dob);
        sb.append("}");
        return sb.toString();
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
                && Objects.equals(password, patient.password) && Objects.equals(email, patient.email)
                && Objects.equals(firstName, patient.firstName) && Objects.equals(lastName, patient.lastName)
                && Objects.equals(dob, patient.dob);
    }

    // hashcode()
    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, email, firstName, lastName, dob);
    }
}
