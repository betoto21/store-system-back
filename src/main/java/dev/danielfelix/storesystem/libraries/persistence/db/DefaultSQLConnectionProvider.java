package dev.danielfelix.storesystem.libraries.persistence.db;

import dev.danielfelix.storesystem.libraries.exceptions.DatabaseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DefaultSQLConnectionProvider {
    private static final Logger LOGGER = LogManager.getLogger(DefaultSQLConnectionProvider.class);
    private DefaultSQLConnectionProvider() {
    }

    static Connection getConnection(){
       try{
           LOGGER.info("Starting connection request");
           final String URL = PostgreSQLConnection.URL;
           final String USERNAME = PostgreSQLConnection.USERNAME;
           final String PASSWORD = PostgreSQLConnection.PASSWORD;
           final Connection con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
           LOGGER.info("Connection established");
           return con;
       } catch (SQLException e){
           throw new DatabaseException("Error on retrieving connection to database: ", e);
       }
    }

}
