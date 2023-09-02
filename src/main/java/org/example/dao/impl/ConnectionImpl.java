package org.example.dao.impl;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionImpl {

    public static Connection getConnections() throws SQLException {
        Properties properties = new Properties();
        try {
            properties.load(ConnectionImpl.class.getResourceAsStream("/datasource.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String host = properties.getProperty("host");
        String port = properties.getProperty("port");
        String databaseName = properties.getProperty("databaseName");
        String url = String.format("jdbc:postgresql://%s:%s/%s", host, port, databaseName);
        return DriverManager.getConnection(url,
                properties.getProperty("user"), properties.getProperty("password"));
    }
}