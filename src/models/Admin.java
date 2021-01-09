package models;

import org.bson.types.ObjectId;

import java.util.Objects;

// POJO class

public class Admin {

    private ObjectId id;
    private String adminId;
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

    public Admin setAdminId(String adminId) {
        this.adminId = adminId;
        return this;
    }

    public String getAdminId() {
        return adminId;
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

    @Override
    public String toString() {
        return "Admin{" +
                "id=" + id +
                ", adminId='" + adminId + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Admin admin = (Admin) o;
        return Objects.equals(id, admin.id) &&
                Objects.equals(adminId, admin.adminId) &&
                Objects.equals(password, admin.password) &&
                Objects.equals(name, admin.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, adminId, password, name);
    }
}
