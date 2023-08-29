package org.example;

import org.example.model.entity.Person;

import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class Application {
    public static void main(String[] args) throws SQLException, IOException {

        Person person = new Person("Oleg", "Zverev");


//        Properties properties = new Properties();
//        properties.load(new FileInputStream("application.properties"));
//        try (Connection connection = DriverManager.getConnection(properties.getProperty("url"),
//                properties.getProperty("user"), properties.getProperty("password"));
//             PreparedStatement statement = connection.prepareStatement("SELECT * FROM public.tyre WHERE id = ?")) {
//            statement.setInt(1, 5);
//            final ResultSet resultSet = statement.executeQuery();
//            if (resultSet.next()) {
//                String byName = "name: " + resultSet.getString("name");
//                String byIndex = "season: " + resultSet.getString(3);
//                //int personId = resultSet.getInt(4);
//                System.out.println(byName);
//                System.out.println(byIndex);
//                //System.out.println("personId: " + personId);
//            }
//        }
    }
}