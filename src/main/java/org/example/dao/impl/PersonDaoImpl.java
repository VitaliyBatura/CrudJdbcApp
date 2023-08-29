package org.example.dao.impl;

import org.example.dao.ConnectionPool;
import org.example.dao.PersonDao;
import org.example.model.entity.Person;
import org.example.model.entity.Vehicle;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonDaoImpl implements PersonDao {

    private final ConnectionPool connectionPool;

    public PersonDaoImpl(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public void create(Person person) throws SQLException {

        Connection connection = connectionPool.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO person " +
                "(first_name, last_name) VALUES (?, ?)");
        preparedStatement.setString(1, person.getFirstName());
        preparedStatement.setString(2, person.getLastName());
        preparedStatement.execute();
        connectionPool.releaseConnection(connection);
    }

    @Override
    public Person readById(int id) throws SQLException {

        Person person = new Person();
        Connection connection = connectionPool.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM person WHERE id = ?");
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<Vehicle> vehicles = new ArrayList<>();
        while (resultSet.next()) {
            person.setId(id);
            person.setFirstName(resultSet.getString("first_name"));
            person.setLastName(resultSet.getString("last_name"));
            person.setVehicles(vehicles);
        }

        preparedStatement = connection.prepareStatement("SELECT * FROM vehicle WHERE person_id = ?");
        preparedStatement.setInt(1, person.getId());
        resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            Vehicle vehicle = new Vehicle();
            vehicle.setId(resultSet.getInt("id"));
            vehicle.setType(resultSet.getString("type"));
            vehicle.setModel(resultSet.getString("model"));
            vehicles.add(vehicle);
        }
        connectionPool.releaseConnection(connection);
        return person;
    }

    @Override
    public List<Person> readAll() throws SQLException {

        List<Person> persons = new ArrayList<>();
        Connection connection = connectionPool.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM person");
        while (resultSet.next()) {
            Person person = new Person();
            List<Vehicle> vehicles = new ArrayList<>();
            person.setId(resultSet.getInt("id"));
            person.setFirstName(resultSet.getString("first_name"));
            person.setLastName(resultSet.getString("last_name"));
            person.setVehicles(vehicles);

            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM vehicle " +
                    "WHERE person_id = ?");
            preparedStatement.setInt(1, person.getId());
            ResultSet vehiclesResultSet = preparedStatement.executeQuery();
            while (vehiclesResultSet.next()) {
                Vehicle vehicle = new Vehicle();
                vehicle.setId(vehiclesResultSet.getInt("id"));
                vehicle.setType(vehiclesResultSet.getString("type"));
                vehicle.setModel(vehiclesResultSet.getString("model"));
                vehicles.add(vehicle);
            }
            persons.add(person);
        }
        connectionPool.releaseConnection(connection);
        return persons;
    }

    @Override
    public void update(Person person) throws SQLException {

        Connection connection = connectionPool.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE person " +
                "SET first_name = ?, last_name = ? WHERE id = ?");
        preparedStatement.setString(1, person.getFirstName());
        preparedStatement.setString(2, person.getLastName());
        preparedStatement.setInt(3, person.getId());
        preparedStatement.execute();
        connectionPool.releaseConnection(connection);
    }

    @Override
    public void deleteById(int id) throws SQLException {

        Connection connection = connectionPool.getConnection();
        PreparedStatement deleteStatement = connection.prepareStatement("DELETE FROM vehicle " +
                "WHERE person_id = ?");
        deleteStatement.setInt(1, id);
        deleteStatement.execute();

        deleteStatement = connection.prepareStatement("DELETE FROM person WHERE id = ?");
        deleteStatement.setInt(1, id);
        deleteStatement.execute();
        connectionPool.releaseConnection(connection);
    }
}