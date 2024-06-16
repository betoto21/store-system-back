package dev.danielfelix.storesystem.libraries.persistence.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.sql.Connection;

@Configuration
public class PostgreSQLConnection implements ApplicationListener<ApplicationEvent> {

    @Autowired
    private Environment env;
    public static String URL;
    public static String USERNAME;
    public static String PASSWORD;
    public static String ENVARPREFIX;

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        this.URL = env.getProperty("spring.datasource.url");
        this.USERNAME = env.getProperty("spring.datasource.username");
        this.PASSWORD = env.getProperty("spring.datasource.password");
        this.ENVARPREFIX = env.getProperty("postgresql.pool.queue");
    }

    public static Connection getConnection(){
        return DefaultSQLConnectionProvider.getConnection();
    }

    public static Connection getConnection(String s) {
        return DefaultSQLConnectionProvider.getConnection();
    }
}
