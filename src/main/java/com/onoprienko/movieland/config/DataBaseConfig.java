package com.onoprienko.movieland.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DataBaseConfig {
    @Bean
    public DataSource dataSource(@Value("${jdbc.url}") String databaseUrl,
                                 @Value("${jdbc.user}") String databaseUser,
                                 @Value("${jdbc.pass}") String databasePass) {
        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setPassword(databasePass);
        hikariDataSource.setUsername(databaseUser);
        hikariDataSource.setJdbcUrl(databaseUrl);
        return hikariDataSource;
    }
}
