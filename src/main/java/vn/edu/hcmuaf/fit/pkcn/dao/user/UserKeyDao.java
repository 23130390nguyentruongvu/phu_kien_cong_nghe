package vn.edu.hcmuaf.fit.pkcn.dao.user;

import org.jdbi.v3.core.Jdbi;
import vn.edu.hcmuaf.fit.pkcn.model.user.json.request.UserKeyDTO;
import vn.edu.hcmuaf.fit.pkcn.utils.enums.StatusUserKey;

import java.time.LocalDateTime;
import java.util.List;

public class UserKeyDao {
    private Jdbi jdbi;

    public UserKeyDao(Jdbi jdbi) {
        this.jdbi = jdbi;
    }

    public boolean revokedAllUserKey(UserKeyDTO userKeyDTO) {
        String sql = """
                UPDATE user_keys
                SET status = :status, revoked_at = :revokedAt
                WHERE user_id = :userId AND status = 'ACTIVE'
                """;
        return jdbi.withHandle(handle ->{
           return handle.createUpdate(sql)
                   .bind("revokedAt", LocalDateTime.now())
                   .bind("status", StatusUserKey.REVOKED.name())
                   .bind("userId", userKeyDTO.getUserId())
                   .execute() > 0;
        });
    }

    public List<UserKeyDTO> getAllUserKeyByUserId(Integer userId) {
        String sql = """
                 SELECT *
                 FROM user_keys
                 WHERE user_id = :userId
                 ORDER BY created_at DESC
                """;
        return jdbi.withHandle(handle -> {
            return handle.createQuery(sql)
                    .bind("userId", userId)
                    .mapToBean(UserKeyDTO.class)
                    .stream().toList();
        });
    }

    public boolean isAnyUserKeyActive(Integer userId) {
        String sql = """
            SELECT *
            FROM user_keys
            WHERE user_id = :userId AND status = 'ACTIVE'
            """;

        return jdbi.withHandle(handle -> {
            return handle.createQuery(sql)
                    .bind("userId", userId)
                    .mapToMap()
                    .findFirst()
                    .isPresent();
        });
    }

    public boolean revokedUserKeyById(Integer userId, Integer id) {
        String sql = """
                UPDATE user_keys
                SET status = :status, revoked_at = :revokedAt
                WHERE user_id = :userId AND status = 'ACTIVE' AND id = :id
                """;

        return jdbi.withHandle(handle ->{
            return handle.createUpdate(sql)
                    .bind("revokedAt", LocalDateTime.now())
                    .bind("status", StatusUserKey.REVOKED.name())
                    .bind("userId", userId)
                    .bind("id", id)
                    .execute() > 0;
        });
    }

    public boolean addUserKey(UserKeyDTO userKeyDTO) {
        String sql = """
                INSERT INTO user_keys 
                    (user_id, key_name, name_algorithm, 
                    public_key, status, created_at, revoked_at
                    ) 
                VALUES 
                (:userId, :keyName, :nameAlgorithm, :publicKey, :status, :createdAt, :revokedAt)
                """;
        return jdbi.withHandle(handle -> {
            return handle.createUpdate(sql)
                    .bindBean(userKeyDTO)
                    .execute() > 0;
        });
    }
}
