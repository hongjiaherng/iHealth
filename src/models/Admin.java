package models;

import org.bson.types.ObjectId;

import java.util.Objects;

public class Admin {

    // username, password, name

    private ObjectId id;
    private String adminid;
    private String password;
    private String name;

    // Getter and setter
    public Admin setId(ObjectId id) {
        this.id = id;
        return this;
    }

    public ObjectId getId() {
        return id;
    }

    public Admin setAdminID(String adminid) {
        this.adminid = adminid;
        return this;
    }

    public String getAdminID() {
        return adminid;
    }

    public Admin setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public Admin setName(String name) {
        this.name = name;
        return this;
    }

    public String getName() {
        return name;
    }

    // toString()
    @Override
    public String toString() {
        return "Admin{" + "id=" + id +
                ", adminid=" + adminid +
                ", password=" + password +
                ", name=" + name +
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
        Admin admin = (Admin) o;
        return Objects.equals(id, admin.id) && Objects.equals(adminid, admin.adminid)
                && Objects.equals(password, admin.password) && Objects.equals(name, admin.name);
    }

    // hashcode()
    @Override
    public int hashCode() {
        return Objects.hash(id, adminid, password, name);
    }
}
