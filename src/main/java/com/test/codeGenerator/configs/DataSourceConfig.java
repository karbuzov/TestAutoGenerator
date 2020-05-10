package com.test.codeGenerator.configs;

import oracle.jdbc.pool.OracleDataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author Vitalii
 * on 08.10.2018.
 */
@Configuration
public class DataSourceConfig {

    private static final Log log = LogFactory.getLog(DataSourceConfig.class);

    @Value("${oracle.datasource.URL}")
    private String URL;

    @Value("${oracle.datasource.user}")
    private String user;

    @Value("${oracle.datasource.password}")
    private String password;

    @Bean
    public DataSource dataSource() throws SQLException {

        OracleDataSource dataSource = new OracleDataSource();

        dataSource.setURL(URL);
        dataSource.setUser(user);
        dataSource.setPassword(password);

        if(log.isInfoEnabled()) {
            log.info("OracleDataSource was created successfully.");
        }

        return dataSource;
    }
}
