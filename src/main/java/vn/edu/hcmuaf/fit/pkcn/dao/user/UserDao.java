    package vn.edu.hcmuaf.fit.pkcn.dao.user;
    import jakarta.servlet.ServletException;
    import org.jdbi.v3.core.Jdbi;
    import vn.edu.hcmuaf.fit.pkcn.model.user.User;

    import java.io.IOException;

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
    }
