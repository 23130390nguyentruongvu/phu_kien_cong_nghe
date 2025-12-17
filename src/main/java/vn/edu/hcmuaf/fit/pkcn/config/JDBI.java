package vn.edu.hcmuaf.fit.pkcn.config;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.mapper.reflect.ReflectionMappers;

public abstract class JDBI {
    private volatile static Jdbi jdbi;

    public static Jdbi getJdbi() {
        if (jdbi == null) {
            synchronized (JDBI.class) {
                if (jdbi == null) {
                    MysqlDataSource mysqlDataSource = new MysqlDataSource();
                    mysqlDataSource.setUrl(
                            "jdbc:mysql://" + DBProperties.getHost() + ":"
                                    + DBProperties.getPort() + "/" + DBProperties.getDbname()
                                    + "?useSSL=false" +
                                    "&characterEncoding=UTF-8" +
                                    "&useUnicode=true" +
                                    "&connectionCollation=utf8mb4_unicode_ci" +
                                    "&serverTimezone=Asia/Ho_Chi_Minh"
                    );
                    mysqlDataSource.setUser(DBProperties.getUsername());
                    mysqlDataSource.setPassword(DBProperties.getPassword());
                    try {
                        mysqlDataSource.setUseCompression(true);
                        mysqlDataSource.setAutoReconnect(true);
                    } catch (Exception e) {
                    }
                    jdbi = Jdbi.create(mysqlDataSource);
                }
            }
        }
        return jdbi;
    }
}
