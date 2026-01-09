    package vn.edu.hcmuaf.fit.pkcn.dao.user;
    import jakarta.servlet.ServletException;
    import org.jdbi.v3.core.Jdbi;
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
            return  jdbi.withHandle(handle -> {
                return handle.createQuery(sql)
                        .bind("loginInfo", usernameoremail)
                        .mapToBean(User.class)
                        .findOne()
                        .orElse(null);
            });
        }
        public boolean register(User user){
            String sql = "INSERT INTO users (avatar,email,full_name,id,password,role_id,status,user_name) values (:avatar,:email,:fullName,:id,:password,:role,:status,:userName) ";
            return jdbi.withHandle(handle -> {
                return   handle.createUpdate(sql)
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
            String sql = "SELECT id, avatar, full_name , user_name , status FROM users";
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
            List<User> listUser = new ArrayList<>();
            return listUser;
        }
        public boolean updateStatus(int id, String status) {
            String sql = "UPDATE users SET status = :status WHERE id = :id";
            return jdbi.withHandle( handle -> {
                return   handle.createUpdate(sql)
                        .bind("status", status)
                        .bind("id", id)
                        .execute() > 0;
            });
        }
        public boolean deleteUser(int id) {
            String sql = "DELETE FROM users WHERE id = :id";
            return jdbi.withHandle(handle -> {
                return handle.createUpdate(sql)
                        .bind("id", id)
                        .execute()>0;
            });
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
            String sql = "UPDATE users SET full_name = :fullName, avatar = :avatar, role_id = :role WHERE id = :id";
            return jdbi.withHandle(handle ->
                    handle.createUpdate(sql)
                            .bindBean(user)
                            .execute() > 0
            );
        }
    }
