package vn.edu.hcmuaf.fit.pkcn.model.user;

import org.jdbi.v3.core.mapper.reflect.ColumnName;

import java.time.LocalDateTime;
import java.util.Objects;

public class UserKey {
    private int id;
    private int userId;
    private String publicKey;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime revokedAt;
    public UserKey() {
    }

    public UserKey(int id, int userId, String publicKey, String status, LocalDateTime createdAt, LocalDateTime revokedAt) {
        this.id = id;
        this.userId = userId;
        this.publicKey = publicKey;
        this.status = status;
        this.createdAt = createdAt;
        this.revokedAt = revokedAt;
    }

    public int getId() {
        return id;
    }

    @ColumnName("id")
    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    @ColumnName("user_id")
    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getPublicKey() {
        return publicKey;
    }

    @ColumnName("public_key")
    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getStatus() {
        return status;
    }

    @ColumnName("status")
    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @ColumnName("created_at")
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getRevokedAt() {
        return revokedAt;
    }

    @ColumnName("revoked_at")
    public void setRevokedAt(LocalDateTime revokedAt) {
        this.revokedAt = revokedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        UserKey userKey = (UserKey) o;
        return id == userKey.id && userId == userKey.userId
                && Objects.equals(publicKey, userKey.publicKey)
                && Objects.equals(status, userKey.status)
                && Objects.equals(createdAt, userKey.createdAt)
                && Objects.equals(revokedAt, userKey.revokedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, publicKey, status, createdAt, revokedAt);
    }

    @Override
    public String toString() {
        return "UserKey{" +
                "id=" + id +
                ", userId=" + userId +
                ", status='" + status + '\'' +
                ", createdAt=" + createdAt +
                ", revokedAt=" + revokedAt +
                '}';
    }
}
