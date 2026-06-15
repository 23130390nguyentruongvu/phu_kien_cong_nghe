package vn.edu.hcmuaf.fit.pkcn.service.user;

import vn.edu.hcmuaf.fit.pkcn.dao.user.UserDao;
import vn.edu.hcmuaf.fit.pkcn.dao.user.UserKeyDao;
import vn.edu.hcmuaf.fit.pkcn.model.user.json.request.UserKeyDTO;

public class UserKeyService {
    private UserKeyDao userKeyDao;
    private UserDao userDao;

    public UserKeyService(UserKeyDao userKeyDao, UserDao userDao) {
        this.userKeyDao = userKeyDao;
        this.userDao = userDao;
    }

    public boolean addUserKey(UserKeyDTO userKeyDTO) {
        if(!userDao.isUserExist(userKeyDTO.getUserId()))
            return false;
        userKeyDao.revokedAllUserKey(userKeyDTO);
        return userKeyDao.addUserKey(userKeyDTO);
    }

    public boolean revokeUserKeyById(Integer userId, Integer id) {
        if(!userDao.isUserExist(userId))
            return false;

        return userKeyDao.revokedUserKeyById(userId, id);
    }
}
