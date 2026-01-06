package vn.edu.hcmuaf.fit.pkcn.service.user;
import org.jdbi.v3.core.Jdbi;
import  vn.edu.hcmuaf.fit.pkcn.model.user.User;
import vn.edu.hcmuaf.fit.pkcn.utils.HashMD5;
import  vn.edu.hcmuaf.fit.pkcn.dao.user.UserDao;

import java.util.List;

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

    public User loginUser(String usernameoremail, String password) {
        User user = userDao.login(usernameoremail);

        if (user != null) {
            String md5Pass = HashMD5.MD5(password);
            if (user.getPassword().equalsIgnoreCase(md5Pass)) {
                return user;
            }
        }
        return null;
    }
    public void updateUserInfo(int id, String fullName, String plainPassword) {
        String hashedPassword = HashMD5.MD5(plainPassword);
        userDao.updateProfile(id, fullName, hashedPassword);
    }
    // load user len wweb
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }
}