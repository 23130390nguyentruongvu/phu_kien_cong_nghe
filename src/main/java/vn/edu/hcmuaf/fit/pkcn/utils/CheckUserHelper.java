package vn.edu.hcmuaf.fit.pkcn.utils;

import vn.edu.hcmuaf.fit.pkcn.config.JDBI;
import vn.edu.hcmuaf.fit.pkcn.service.user.UserService;

public abstract class CheckUserHelper {
    private static UserService userService = new UserService(JDBI.getJdbi());
    public static boolean checkUserInValid(int userId) {
        return !userService.isUserExist(userId) || userService.isUserLocked(userId);
    }
    public static boolean checkUserLocked(int userId) {
        return userService.isUserLocked(userId);
    }
}
