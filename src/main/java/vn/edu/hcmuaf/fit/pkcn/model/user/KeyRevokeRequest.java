package vn.edu.hcmuaf.fit.pkcn.model.user;

import org.jdbi.v3.core.mapper.reflect.ColumnName;

import java.time.LocalDateTime;

public class KeyRevokeRequest {
    private Integer id;
    private Integer userKeyId;
    private String token;
    private LocalDateTime expiredAt;
    private Boolean isCompleted;

    public KeyRevokeRequest() {
    }

    public KeyRevokeRequest(Integer userKeyId, String token, LocalDateTime expiredAt, Boolean isCompleted) {
        this.userKeyId = userKeyId;
        this.token = token;
        this.expiredAt = expiredAt;
        this.isCompleted = isCompleted;
    }

    @ColumnName("id")
    public Integer getId() {
        return id;
    }

    @ColumnName("id")
    public void setId(Integer id) {
        this.id = id;
    }

    @ColumnName("user_key_id")
    public Integer getUserKeyId() {
        return userKeyId;
    }

    @ColumnName("user_key_id")
    public void setUserKeyId(Integer userKeyId) {
        this.userKeyId = userKeyId;
    }

    @ColumnName("token")
    public String getToken() {
        return token;
    }

    @ColumnName("token")
    public void setToken(String token) {
        this.token = token;
    }

    @ColumnName("expired_at")
    public LocalDateTime getExpiredAt() {
        return expiredAt;
    }

    @ColumnName("expired_at")
    public void setExpiredAt(LocalDateTime expiredAt) {
        this.expiredAt = expiredAt;
    }

    @ColumnName("is_completed")
    public Boolean getCompleted() {
        return isCompleted;
    }

    @ColumnName("is_completed")
    public void setCompleted(Boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

}
