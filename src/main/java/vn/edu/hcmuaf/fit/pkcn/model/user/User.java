package vn.edu.hcmuaf.fit.pkcn.model.user;
import org.jdbi.v3.core.mapper.reflect.ColumnName;

import java.util.Objects;

public class User {
    private int id;
    private String userName;
    private String email;
    private String password;
    private String fullName;
    private String avatar;
    private String status;
    private int role;

    public User() {
    }

    public User(int id, String userName, String email, String password, String fullName, String status, String avatar, int role) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.fullName = fullName;
        this.status = status;
        this.avatar = avatar;
        this.role = role;
    }

    public int getId() {
        return id;
    }
    @ColumnName("id")
    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }
    @ColumnName("user_name")
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }
    @ColumnName("email")
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }
    @ColumnName("password")
    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }
    @ColumnName("full_name")
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getStatus() {
        return status;
    }
    @ColumnName("status")
    public void setStatus(String status) {
        this.status = status;
    }

    public String getAvatar() {
        return avatar;
    }
    @ColumnName("avatar")
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getRole() {
        return role;
    }
    @ColumnName("role")
    public void setRole(int role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && role == user.role && Objects.equals(userName, user.userName) && Objects.equals(email, user.email) && Objects.equals(password, user.password) && Objects.equals(fullName, user.fullName) && Objects.equals(avatar, user.avatar) && Objects.equals(status, user.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userName, email, password, fullName, avatar, status, role);
    }
}
