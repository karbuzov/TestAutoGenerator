package com.test.codeGenerator.configs;

import com.mysql.jdbc.jdbc2.optional.MysqlConnectionPoolDataSource;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @author Vitalii
 * on 08.10.2018.
 */
@Configuration
public class DataSourceConfig {

    private static final Log log = LogFactory.getLog(DataSourceConfig.class);

//    @Value("${oracle.datasource.URL}")
    private String URL = "jdbc:mysql://172.16.9.213:3306/quartz?useUnicode=true&characterEncoding=UTF-8";

//    @Value("${oracle.datasource.user}")
    private String user = "quartz";

//    @Value("${oracle.datasource.password}")
    private String password = "quartz";

    @Bean
    public DataSource dataSource()  {

        if(log.isInfoEnabled()) {
            log.info(String.format("MySQL connect to URL[%s], User[%s]", URL, user));
        }
        MysqlDataSource dataSource = new MysqlConnectionPoolDataSource();

        dataSource.setURL(URL);
        dataSource.setUser(user);
        dataSource.setPassword(password);

        if(log.isInfoEnabled()) {
            log.info("MySQLDataSource was created successfully.");
        }

        return dataSource;
    }
}
