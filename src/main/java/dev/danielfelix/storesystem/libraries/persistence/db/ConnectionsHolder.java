package dev.danielfelix.storesystem.libraries.persistence.db;

import dev.danielfelix.storesystem.libraries.exceptions.DatabaseException;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class ConnectionsHolder {

    static Map<String, Connection> connections = new HashMap<>();

    private ConnectionsHolder() {
    }

    public static Connection getConnection(String environment, Function<String, Connection> connectionFactory) {
        return getOpenConnection(connections.get(environment), environment, connectionFactory);
    }

    private static Connection getOpenConnection(
            final Connection existingConnection,
            final String enviromment,
            final Function<String, Connection> connectionFactory){
        try{
            if (existingConnection == null || existingConnection.isClosed()) {
                Connection con = connectionFactory.apply(enviromment);
                connections.put(enviromment, con);
                return con;
            } else {
                return existingConnection;
            }
        } catch (SQLException e){
            throw new DatabaseException("Error on closing connection: ",e);
        }


    }

}
