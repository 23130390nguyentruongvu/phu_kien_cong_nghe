package vn.edu.hcmuaf.fit.pkcn.dao.user;

import jakarta.servlet.ServletException;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import vn.edu.hcmuaf.fit.pkcn.model.admin.add.JsonAddUser;
import vn.edu.hcmuaf.fit.pkcn.model.admin.edit.JsonUpdateUser;
import vn.edu.hcmuaf.fit.pkcn.model.user.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class UserDao {
    private Jdbi jdbi;

    public UserDao(Jdbi jdbi) {
        this.jdbi = jdbi;
    }

    public User login(String usernameoremail) {
        String sql = "select * from users where user_name = :loginInfo or email = :loginInfo";
        return jdbi.withHandle(handle -> {
            return handle.createQuery(sql)
                    .bind("loginInfo", usernameoremail)
                    .mapToBean(User.class)
                    .findOne()
                    .orElse(null);
        });
    }

    public boolean register(User user) {
        String sql = "INSERT INTO users (uid, avatar,email,full_name,id,password,role_id,status,user_name) " +
                "values (:uid, :avatar,:email,:fullName,:id,:password,:role,:status,:userName) ";
        return jdbi.withHandle(handle -> {
            return handle.createUpdate(sql)
                    .bindBean(user)
                    .execute() > 0;
        });
    }

    public boolean checkExist(String username, String email) {
        String sql = "SELECT COUNT(*) FROM users WHERE user_name = :username OR email = :email";
        return jdbi.withHandle(handle -> {
            Long count = handle.createQuery(sql)
                    .bind("username", username)
                    .bind("email", email)
                    .mapTo(Long.class)
                    .one();
            return count > 0;
        });
    }

    public void updateProfile(int id, String fullName, String password) {
        jdbi.useHandle(handle ->
                handle.createUpdate("UPDATE users SET full_name = :fullName, password = :password WHERE id = :id")
                        .bind("fullName", fullName)
                        .bind("password", password)
                        .bind("id", id)
                        .execute()
        );
    }

    //  danh sach user
    public List<User> getAllUsers() {
        String sql = "SELECT id, avatar, full_name , user_name , status,role_id FROM users";
        return jdbi.withHandle(handle -> {
            List<User> listUser = new ArrayList<>();
            Iterator<User> iter = handle.createQuery(sql)
                    .mapToBean(User.class)
                    .stream().iterator();
            while (iter.hasNext()) {
                User user = iter.next();
                listUser.add(user);
            }
            return listUser;
        });
    }

    // tim kiem theo ten user
    public List<User> getUserByName(String fullName) {
        String sql = "SELECT id, avatar, full_name , user_name , status FROM users WHERE full_name LIKE :fullName";
        return jdbi.withHandle(handle -> {
            List<User> listUser = new ArrayList<>();
            Iterator<User> Iter = handle.createQuery(sql)
                    .bind("fullName", "%" + fullName + "%")
                    .mapToBean(User.class)
                    .stream().iterator();
            while (Iter.hasNext()) {
                User user = Iter.next();
                listUser.add(user);
            }
            return listUser;
        });
    }

    public boolean updateStatus(int id, String status) {
        String sql = "UPDATE users SET status = :status WHERE id = :id";
        return jdbi.withHandle(handle -> {
            return handle.createUpdate(sql)
                    .bind("status", status)
                    .bind("id", id)
                    .execute() > 0;
        });
    }

    public boolean deleteUser(int id) {
        String sql = "DELETE FROM users WHERE id = :id";
        return jdbi.withHandle(handle ->
                handle.createUpdate(sql)
                        .bind("id", id)
                        .execute() > 0
        );
    }

    public User getUserById(int id) {
        String sql = "SELECT id, user_name, email, full_name, avatar, status, role_id FROM users WHERE id = :id";
        return jdbi.withHandle(handle ->
                handle.createQuery(sql)
                        .bind("id", id)
                        .mapToBean(User.class)
                        .findOne()
                        .orElse(null)
        );
    }

    public boolean updateUser(User user) {
        String sql = "UPDATE users SET full_name = :fullName, avatar = :avatar, role_id = :roleId WHERE id = :id";
        return jdbi.withHandle(handle ->
                handle.createUpdate(sql)
                        .bind("fullName", user.getFullName())
                        .bind("avatar", user.getAvatar())
                        .bind("roleId", user.getRole())
                        .bind("id", user.getId())
                        .execute() > 0
        );
    }


    public boolean checkUsername(String username) {
        String sql = """
                SELECT user_name
                FROM users
                WHERE user_name = :username
                """;
        return jdbi.withHandle(handle -> handle.createQuery(sql)
                .bind("username", username)
                .mapTo(String.class)
                .findOne()
                .orElse(null)
        ) != null;
    }

    public boolean checkEmail(String email) {
        String sql = """
                SELECT email
                FROM users
                WHERE email = :email
                """;
        return jdbi.withHandle(handle -> handle.createQuery(sql)
                .bind("email", email)
                .mapTo(String.class)
                .findOne()
                .orElse(null)
        ) != null;
    }

    public int insertUser(JsonAddUser user) {
        String sql = "INSERT INTO users (avatar, email, full_name, folder_id, password, role_id, status, user_name) " +
                "VALUES (:avatar, :email, :fullName, :folderId, :password, :role, :status, :userName)";
        return jdbi.withHandle(handle -> handle.createUpdate(sql)
                .bind("avatar", user.getUrl())
                .bind("email", user.getEmail())
                .bind("fullName", user.getFullName())
                .bind("folderId", user.getFolderId())
                .bind("password", user.getPassword())
                .bind("role", user.getRole())
                .bind("status", "active")
                .bind("userName", user.getUsername())
                .execute()
        );
    }

    public String getFolderId(int userId) {
        String sql = """
                SELECT folder_id
                FROM users
                WHERE id = :id
                """;
        return jdbi.withHandle(handle -> handle.createQuery(sql)
                .bind("id", userId)
                .mapTo(String.class)
                .findOne()
                .orElse(null)
        );
    }

    public int updateUserWithTransaction(Handle handle, JsonUpdateUser user) {
        String updateUrl = user.isHasImage() ? " avatar = :url " : " avatar = avatar ";
        String sql = """
                UPDATE users
                SET role_id = :role,
                    full_name = :name,
                    folder_id = :folderId,
                """
                +
                updateUrl
                + """
                WHERE id = :userId
                AND (:url IS NULL OR :url IS NOT NULL)
                """;
        return handle.createUpdate(sql)
                .bind("role", user.getRole())
                .bind("name", user.getFullName())
                .bind("url", user.getUrl())
                .bind("userId", user.getUserId())
                .bind("folderId", user.getFolderId())
                .execute();
    }

    public String getUid(int id) {
        String sql = """
                SELECT uid
                FROM users
                WHERE id = :id
                """;
        return jdbi.withHandle(handle -> handle.createQuery(sql)
                .bind("id", id)
                .mapTo(String.class)
                .findOne()
                .orElse(null)
        );
    }

    public User findByEmail(String email) {
        String sql = """
                SELECT *
                FROM users
                WHERE email = :email
                """;
        return jdbi.withHandle(handle -> handle.createQuery(sql)
                .bind("email", email)
                .mapToBean(User.class)
                .findOne()
                .orElse(null)
        );
    }
}
