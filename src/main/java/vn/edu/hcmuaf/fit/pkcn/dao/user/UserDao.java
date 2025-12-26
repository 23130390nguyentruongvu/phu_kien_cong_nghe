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

        public boolean register(User user){
            String sql = "INSERT INTO users (avatar,email,full_name,id,password,role_id,status,user_name) values (:avatar,:email,:fullName,:id,:password,:role,:status,:userName) ";
            return jdbi.withHandle(handle -> {
                return   handle.createUpdate(sql)
                        .bindBean(user)
                        .execute() > 0;
            });
        }
        public boolean checkExist(String username, String email){
            String sql = "Select Count(*) From Users where user_name =:user_name or email = :email";
            return jdbi.withHandle(handle ->{
                return handle.createQuery(sql)
                        .bind("user_name", username)
                        .bind("email", email)
                        .mapTo(Integer.class)
                        .one() > 0;
            });
        }
    }
