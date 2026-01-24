package vn.edu.hcmuaf.fit.pkcn.service.reset_password;

import vn.edu.hcmuaf.fit.pkcn.config.JDBI;
import vn.edu.hcmuaf.fit.pkcn.dao.reset_password.ResetPasswordDao;

import java.time.LocalDateTime;

public class ResetPasswordService {
    private ResetPasswordDao resetPasswordDao;

    public ResetPasswordService(ResetPasswordDao resetPasswordDao) {
        this.resetPasswordDao = resetPasswordDao;
    }

    public boolean insertEmailToken(String email, String token, LocalDateTime expiry) {
        return JDBI.getJdbi().inTransaction(handle -> {
            boolean isExist = resetPasswordDao.checkEmailHasTokenWithTransaction(handle, email);
            if (isExist) resetPasswordDao.removeEmailTokenWithTransaction(handle, email);

            return resetPasswordDao.insertPasswordWithTransaction(handle, email, token, expiry) > 0;
        });
    }
}
