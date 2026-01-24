package vn.edu.hcmuaf.fit.pkcn.dao.reset_password;

import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;

import java.time.LocalDateTime;

public class ResetPasswordDao {
    private Jdbi jdbi;

    public ResetPasswordDao(Jdbi jdbi) {
        this.jdbi = jdbi;
    }

    public int removeEmailTokenWithTransaction(Handle handle, String email) {
        String sql = """
                DELETE 
                FROM password_resets
                WHERE email = :email
                """;
        return handle.createUpdate(sql)
                .bind("email", email)
                .execute();
    }

    public boolean checkEmailHasTokenWithTransaction(Handle handle, String email) {
        String sql = """
                SELECT COUNT(*)
                FROM password_resets
                WHERE email = :email
                """;
        return handle.createQuery(sql)
                .bind("email", email)
                .mapTo(Integer.class)
                .findOne()
                .orElse(0) != 0;
    }

    public int insertPasswordWithTransaction(Handle handle, String email, String token, LocalDateTime expiry) {
        String sql = """
                INSERT INTO password_resets (email, token, expiry_date)
                VALUES (:email, :token, :expiry)
                """;
        return handle.createUpdate(sql)
                .bind("email", email)
                .bind("token", token)
                .bind("expiry", expiry)
                .execute();
    }

    /*
    Kiểm tra token có tồn tại và còn hạn dưới 15p không? Nếu có trả về email tương
     */
    public String getEmailByToken(String token) {
        String sql = """
                SELECT email
                FROM password_resets
                WHERE token = :token
                  AND expiry_date > NOW();
                """;
        return jdbi.withHandle(handle -> handle.createQuery(sql)
                .bind("token", token)
                .mapTo(String.class)
                .findOne()
                .orElse(null)
        );
    }

    public int deleteTokenWithTransaction(Handle handle, String email) {
        String sql = """
                DELETE
                FROM password_resets
                WHERE email = :email
                """;
        return handle.createUpdate(sql)
                .bind("email", email)
                .execute();
    }
}
