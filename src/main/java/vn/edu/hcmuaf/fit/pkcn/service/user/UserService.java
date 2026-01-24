package vn.edu.hcmuaf.fit.pkcn.service.user;

import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import vn.edu.hcmuaf.fit.pkcn.model.admin.add.JsonAddUser;
import vn.edu.hcmuaf.fit.pkcn.model.admin.edit.JsonUpdateUser;
import vn.edu.hcmuaf.fit.pkcn.model.user.User;
import vn.edu.hcmuaf.fit.pkcn.utils.HashMD5;
import vn.edu.hcmuaf.fit.pkcn.dao.user.UserDao;

import java.util.List;

public class UserService {
    UserDao userDao;

    public UserService(Jdbi userDao) {
        this.userDao = new UserDao(userDao);
    }

    public String getFolderId(int userId) {
        return userDao.getFolderId(userId);
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

    public int insertUser(JsonAddUser user) {
        user.setPassword(HashMD5.MD5(user.getPassword()));
        return userDao.insertUser(user);
    }

    public User loginUser(String usernameoremail, String password) throws Exception {
        User user = userDao.login(usernameoremail);

        if (user != null) {
            String md5Pass = HashMD5.MD5(password);
            if (user.getPassword().equalsIgnoreCase(md5Pass)) {
                if ("active".equalsIgnoreCase(user.getStatus())) {
                    return user;
                } else {
                    throw new Exception("Tài khoản đã bị khóa");
                }
            }
        }
        return null;
    }

    public int updateUserWithTransaction(Handle handle, JsonUpdateUser user) {
        return userDao.updateUserWithTransaction(handle, user);
    }

    public void updateUserInfo(int id, String fullName, String plainPassword) {
        String hashedPassword = HashMD5.MD5(plainPassword);
        userDao.updateProfile(id, fullName, hashedPassword);
    }

    public boolean checkUsername(String username) {
        return userDao.checkUsername(username);
    }

    public boolean checkEmail(String email) {
        return userDao.checkEmail(email);
    }

    // load user len wweb
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    public boolean updateStatusSv(int id, String status) {
        return userDao.updateStatus(id, status);
    }

    public boolean deleteUser(int id) {
        return userDao.deleteUser(id);
    }

    public boolean updateUserAdmin(User user) {
        return userDao.updateUser(user);
    }

    public User getUserById(int id) {
        return userDao.getUserById(id);
    }

    public List<User> getUserByName(String fullName) {
        return userDao.getUserByName(fullName);
    }

    public String getUid(int id) {
        return userDao.getUid(id);
    }
    public boolean changePassword(int id, String newPassword) {
        String md5Pass = HashMD5.MD5(newPassword);
        return userDao.updatePassword(id, md5Pass);
    }
}