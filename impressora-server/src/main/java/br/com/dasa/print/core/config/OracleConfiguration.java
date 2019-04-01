package br.com.dasa.print.core.config;

import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class OracleConfiguration {
    @Autowired
    private Environment env;

    @Bean
    public DataSource oracleDataSource() {

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(Preconditions.checkNotNull(env.getProperty("spring.datasource.oracle.driver-class-name")));
        dataSource.setUrl(Preconditions.checkNotNull(env.getProperty("spring.datasource.oracle.url")));
        dataSource.setUsername(Preconditions.checkNotNull(env.getProperty("spring.datasource.oracle.username")));
        dataSource.setPassword(Preconditions.checkNotNull(env.getProperty("spring.datasource.oracle.password")));

        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource oracleDataSource) {
        return new JdbcTemplate(oracleDataSource);
    }
}

