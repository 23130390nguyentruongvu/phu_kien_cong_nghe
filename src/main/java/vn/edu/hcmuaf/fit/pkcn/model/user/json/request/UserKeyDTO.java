package vn.edu.hcmuaf.fit.pkcn.model.user.json.request;

import org.jdbi.v3.core.mapper.reflect.ColumnName;

import java.time.LocalDateTime;

public class UserKeyDTO {
    private Integer id;
    private Integer userId;
    private String keyName;
    private String nameAlgorithm;
    private String publicKey;
    private String status;
    private LocalDateTime createAt;
    private LocalDateTime revokedAt;

    public UserKeyDTO() {}

    public UserKeyDTO(Integer userId, String keyName, String nameAlgorithm,
                      String publicKey, String status, LocalDateTime createAt,
                      LocalDateTime revokedAt) {
        this.userId = userId;
        this.keyName = keyName;
        this.nameAlgorithm = nameAlgorithm;
        this.publicKey = publicKey;
        this.status = status;
        this.createAt = createAt;
        this.revokedAt = revokedAt;
    }

    @ColumnName("id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @ColumnName("user_id")
    public Integer getUserId() {
        return userId;
    }

    @ColumnName("key_name")
    public String getKeyName() {
        return keyName;
    }

    @ColumnName("name_algorithm")
    public String getNameAlgorithm() {
        return nameAlgorithm;
    }

    @ColumnName("public_key")
    public String getPublicKey() {
        return publicKey;
    }

    @ColumnName("status")
    public String getStatus() {
        return status;
    }

    @ColumnName("created_at")
    public LocalDateTime getCreatedAt() {
        return createAt;
    }

    @ColumnName("revoked_at")
    public LocalDateTime getRevokedAt() {
        return revokedAt;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }

    public void setNameAlgorithm(String nameAlgorithm) {
        this.nameAlgorithm = nameAlgorithm;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCreatedAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public void setRevokedAt(LocalDateTime revokedAt) {
        this.revokedAt = revokedAt;
    }
}
