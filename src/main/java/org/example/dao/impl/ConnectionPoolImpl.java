package org.example.dao.impl;

import org.example.dao.ConnectionPool;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ConnectionPoolImpl implements ConnectionPool {

    private static String url;
    private static String user;
    private static String password;

    private static final String DRIVER = "org.postgresql.Driver";
    private static final int INITIAL_POOL_SIZE = 10;

    private static final List<Connection> connectionPoolList = new ArrayList<>(INITIAL_POOL_SIZE);

    private static final List<Connection> stoppedConnectionList = new ArrayList<>(INITIAL_POOL_SIZE);

    private static final ConnectionPool connectionPool = new ConnectionPoolImpl();

    static {
        getAppProperties();
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            System.out.println("Не найден драйвер подключения к базе данных.");
            e.printStackTrace();
        }
        for (int i = 0; i < INITIAL_POOL_SIZE; i++) {
            try {
                connectionPoolList.add(DriverManager.getConnection(url, user, password));
            } catch (SQLException e) {
                System.out.println("Ошибка при получении соединения.");
                e.printStackTrace();
            }
        }
    }

    private static void getAppProperties() {

        Properties properties = new Properties();
        if (System.getProperty("url") == null) {
            try {
                properties.load(new FileInputStream("src/main/resources/datasource.properties"));
//                properties.load(Thread.currentThread().getContextClassLoader()
//                        .getResourceAsStream("src/main/resources/datasource.properties"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            url = properties.getProperty("url");
            user = properties.getProperty("user");
            password = properties.getProperty("password");
        } else {
            url = System.getProperty("url");
            user = System.getProperty("user");
            password = System.getProperty("password");
        }
    }

    public static ConnectionPool getConnectionPool() {
        return connectionPool;
    }

    @Override
    public Connection getConnection() {

        Connection connection = connectionPoolList.remove(connectionPoolList.size() - 1);
        stoppedConnectionList.add(connection);
        return connection;
    }

    @Override
    public void releaseConnection(Connection connection) {
        connectionPoolList.add(connection);
        stoppedConnectionList.remove(connection);
    }
}