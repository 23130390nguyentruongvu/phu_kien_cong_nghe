package vn.edu.hcmuaf.fit.pkcn.service.user;

import vn.edu.hcmuaf.fit.pkcn.dao.user.UserDao;
import vn.edu.hcmuaf.fit.pkcn.dao.user.UserKeyDao;
import vn.edu.hcmuaf.fit.pkcn.model.user.json.request.UserKeyDTO;

import java.util.List;

public class UserKeyService {
    private UserKeyDao userKeyDao;
    private UserDao userDao;

    public UserKeyService(UserKeyDao userKeyDao, UserDao userDao) {
        this.userKeyDao = userKeyDao;
        this.userDao = userDao;
    }

    public boolean isNotFoundUserKeyACTIVE(Integer userId) {
        return !userKeyDao.isAnyUserKeyActive(userId);
    }

    public boolean addUserKey(UserKeyDTO userKeyDTO) throws Exception {
        if(!userDao.isUserExist(userKeyDTO.getUserId()))
            return false;
        if(userKeyDao.isAnyUserKeyActive(userKeyDTO.getUserId()))
            throw new Exception("Bạn đang có 1 khóa đang hoạt động, vui lòng thu hồi khóa đó nếu muốn thêm khóa mới");

        if(userKeyDao.isDbHasPublicKey(userKeyDTO.getPublicKey()))
            throw new Exception("Khóa này đã có trong hệ thống, vui lòng thử lại khóa khác");

        userKeyDao.revokedAllUserKey(userKeyDTO);
        return userKeyDao.addUserKey(userKeyDTO);
    }

    public List<UserKeyDTO> getAllUserKeyByUserId(Integer userId) {
        return userKeyDao.getAllUserKeyByUserId(userId);
    }

    public boolean revokeUserKeyById(Integer userId, Integer id) {
        if(!userDao.isUserExist(userId))
            return false;

        return userKeyDao.revokedUserKeyById(userId, id);
    }
    public UserKeyDTO getActiveUserKeyByIdUser(int userId) {
        return userKeyDao.getActiveUserKey(userId);
    }
}
