package vn.edu.hcmuaf.fit.pkcn.service.user;
import org.jdbi.v3.core.Jdbi;
import  vn.edu.hcmuaf.fit.pkcn.model.user.User;
import vn.edu.hcmuaf.fit.pkcn.utils.HashMD5;
import  vn.edu.hcmuaf.fit.pkcn.dao.user.UserDao;
public class UserService {
    UserDao userDao;

    public UserService(Jdbi userDao) {
        this.userDao = new UserDao(userDao);
    }
    public boolean checkExist(String username, String email) {
        return userDao.checkExist(username, email);
    }
    public boolean registerUser(User user) {
        String getPass = user.getPassword();
        String md5Pass = HashMD5.MD5(getPass);
        user.setPassword(md5Pass);
        return userDao.register(user);
    }
}
