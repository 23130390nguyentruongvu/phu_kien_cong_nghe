package vn.edu.hcmuaf.fit.pkcn.service.user;

import vn.edu.hcmuaf.fit.pkcn.dao.user.KeyRevokeRequestDao;
import vn.edu.hcmuaf.fit.pkcn.dao.user.UserKeyDao;
import vn.edu.hcmuaf.fit.pkcn.model.user.KeyRevokeRequest;

import java.time.LocalDateTime;

public class KeyRevokeRequestService {
    private UserKeyDao userKeyDao;
    private KeyRevokeRequestDao keyRevokeRequestDao;

    public KeyRevokeRequestService(UserKeyDao userKeyDao, KeyRevokeRequestDao keyRevokeRequestDao) {
        this.userKeyDao = userKeyDao;
        this.keyRevokeRequestDao = keyRevokeRequestDao;
    }

    public boolean updateIsCompleted(boolean isComplete, String token) {
        KeyRevokeRequest tmp = keyRevokeRequestDao.getRevokeKeyRequestByToken(token);
        if(tmp == null || tmp.getExpiredAt().isBefore(LocalDateTime.now()))
            return false;

        return keyRevokeRequestDao.updateIsCompleted(isComplete, token);
    }
    public KeyRevokeRequest getKeyRevokeRequestByToken(String token) {
        return keyRevokeRequestDao.getRevokeKeyRequestByToken(token);
    }

    public boolean insertKeyRevokeRequest(KeyRevokeRequest keyRevokeRequest) {
        if(userKeyDao.getUserKeyById(keyRevokeRequest.getUserKeyId()) == null)
            return false;

        return keyRevokeRequestDao.insertKeyRevokeRequest(keyRevokeRequest);
    }
}
