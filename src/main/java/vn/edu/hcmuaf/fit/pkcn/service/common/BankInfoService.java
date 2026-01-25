package vn.edu.hcmuaf.fit.pkcn.service.common;

import vn.edu.hcmuaf.fit.pkcn.dao.common.BankInfoDao;
import vn.edu.hcmuaf.fit.pkcn.model.common.BankInfo;

import java.util.List;

public class BankInfoService {
    private BankInfoDao bankInfoDao;

    public BankInfoService(BankInfoDao bankInfoDao) {
        this.bankInfoDao = bankInfoDao;
    }

    public List<BankInfo> getBankInfos() {
        return bankInfoDao.getBankInfos();
    }
}
