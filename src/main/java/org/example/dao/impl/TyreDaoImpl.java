package org.example.dao.impl;

import org.example.dao.ConnectionPool;
import org.example.dao.TyreDao;
import org.example.model.entity.Tyre;
import org.example.model.entity.Vehicle;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class TyreDaoImpl implements TyreDao {

    private final ConnectionPool connectionPool;

    public TyreDaoImpl(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public void create(Tyre tyre) throws SQLException {

        Connection connection = connectionPool.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO tyre " +
                "(name, season) VALUES (?, ?)");
        preparedStatement.setString(1, tyre.getName());
        preparedStatement.setString(2, tyre.getSeason());
        preparedStatement.execute();
        connectionPool.releaseConnection(connection);
    }

    @Override
    public Tyre readById(int id) throws SQLException {

        Tyre tyre = new Tyre();
        Set<Vehicle> vehicles = new LinkedHashSet<>();
        Connection connection = connectionPool.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM tyre WHERE id = ?");
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            tyre.setId(resultSet.getInt("id"));
            tyre.setName(resultSet.getString("name"));
            tyre.setSeason(resultSet.getString("season"));
            tyre.setVehicles(vehicles);
        }

        preparedStatement = connection.prepareStatement("SELECT * FROM vehicle JOIN vehicle_tyre vt on " +
                "vehicle.id = vt.vehicle_id WHERE tyre_id = ?");
        preparedStatement.setInt(1, tyre.getId());
        resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            Vehicle vehicle = new Vehicle();
            vehicle.setId(resultSet.getInt("id"));
            vehicle.setType(resultSet.getString("type"));
            vehicle.setModel(resultSet.getString("model"));
            vehicles.add(vehicle);
        }

        connectionPool.releaseConnection(connection);
        return tyre;
    }

    @Override
    public List<Tyre> readAll() throws SQLException {

        Connection connection = connectionPool.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM tyre");
        List<Tyre> tyres = new ArrayList<>();
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            Tyre tyre = readById(id);
            tyres.add(tyre);
        }

        connectionPool.releaseConnection(connection);
        return tyres;
    }

    @Override
    public void update(Tyre tyre) throws SQLException {

        Connection connection = connectionPool.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE tyre SET name = ?, " +
                "season = ? WHERE id = ?");
        preparedStatement.setString(1, tyre.getName());
        preparedStatement.setString(2, tyre.getSeason());
        preparedStatement.setInt(3, tyre.getId());
        preparedStatement.execute();
        connectionPool.releaseConnection(connection);
    }

    @Override
    public void deleteById(int id) throws SQLException {

        Connection connection = connectionPool.getConnection();
        PreparedStatement deleteStatement = connection.prepareStatement("DELETE FROM tyre WHERE id = ?");
        deleteStatement.setInt(1, id);
        deleteStatement.execute();
        connectionPool.releaseConnection(connection);
    }
}