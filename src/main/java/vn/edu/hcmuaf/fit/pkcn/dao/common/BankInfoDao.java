package vn.edu.hcmuaf.fit.pkcn.dao.common;

import org.jdbi.v3.core.Jdbi;
import vn.edu.hcmuaf.fit.pkcn.model.common.BankInfo;

import java.util.List;

public class BankInfoDao {
    private Jdbi jdbi;

    public BankInfoDao(Jdbi jdbi) {
        this.jdbi = jdbi;
    }

    public List<BankInfo> getBankInfos() {
        String sql = """
                SELECT *
                FROM info_banks
                """;
        return jdbi.withHandle(handle -> handle.createQuery(sql)
                .mapToBean(BankInfo.class)
                .list()
        );
    }
}
