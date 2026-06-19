package vn.edu.hcmuaf.fit.pkcn.dao.user;

import org.jdbi.v3.core.Jdbi;
import vn.edu.hcmuaf.fit.pkcn.model.user.KeyRevokeRequest;

public class KeyRevokeRequestDao {
    private Jdbi jdbi;

    public KeyRevokeRequestDao(Jdbi jdbi) {
        this.jdbi = jdbi;
    }

    public KeyRevokeRequest getRevokeKeyRequestByToken(String token) {
        String sql = """
                SELECT * 
                FROM key_revoke_requests
                WHERE token = :token
                """;

        return jdbi.withHandle(handle -> {
            return handle.createQuery(sql)
                    .bind("token", token)
                    .mapToBean(KeyRevokeRequest.class)
                    .findFirst()
                    .orElse(null);
        });
    }

    public boolean updateIsCompleted(boolean isComplete, String token) {
        String sql = """
                UPDATE key_revoke_requests 
                SET is_completed = :isComplete
                WHERE token = :token
                """;

        return jdbi.withHandle(handle -> {
            return handle.createUpdate(sql)
                    .bind("isComplete", isComplete)
                    .bind("token", token)
                    .execute() > 0;
        });
    }

    public boolean insertKeyRevokeRequest(KeyRevokeRequest keyRevokeRequest) {
        String sql = """
                INSERT INTO key_revoke_requests (
                    user_key_id, token, expired_at, is_completed
                ) VALUES (
                    :userKeyId, :token, :expiredAt, :isCompleted
                )
                """;
        return jdbi.withHandle(handle -> {
            return handle.createUpdate(sql)
                    .bind("userKeyId", keyRevokeRequest.getUserKeyId())
                    .bind("token", keyRevokeRequest.getToken())
                    .bind("expiredAt", keyRevokeRequest.getExpiredAt())
                    .bind("isCompleted", keyRevokeRequest.getCompleted())
                    .execute() > 0;
        });
    }
}
