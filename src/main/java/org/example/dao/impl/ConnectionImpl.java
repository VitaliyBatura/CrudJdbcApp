package org.example.dao.impl;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionImpl {

    public static Connection getConnections() throws SQLException {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("src/main/resources/datasource.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Connection connection = DriverManager.getConnection(properties.getProperty("url"),
                properties.getProperty("user"), properties.getProperty("password"));
        return connection;
    }
}