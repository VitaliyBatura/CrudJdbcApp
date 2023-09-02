package org.example.dao.impl;

import org.example.dao.ConnectionPool;
import org.example.dao.VehicleDao;
import org.example.model.entity.Person;
import org.example.model.entity.Tyre;
import org.example.model.entity.Vehicle;
import org.jetbrains.annotations.NotNull;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class VehicleDaoImpl implements VehicleDao {

    private ConnectionPool connectionPool;
    private ConnectionImpl connectionImpl;

    public VehicleDaoImpl(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public Vehicle create(@NotNull Vehicle vehicle) throws SQLException {

        Connection connection = connectionImpl.getConnections();
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO vehicle " +
                "(type, model, person_id) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, vehicle.getType());
        preparedStatement.setString(2, vehicle.getModel());
        //int personId = (int) Math.round(Math.random() * 4);
        preparedStatement.setInt(3, vehicle.getPerson());
        preparedStatement.executeUpdate();
        ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
        if(generatedKeys.next()) {
            vehicle.setId(generatedKeys.getInt("id"));
        }
        //connectionPool.releaseConnection(connection);
        return vehicle;
    }

    @Override
    public Vehicle readById(int id) throws SQLException {

        Vehicle vehicle = new Vehicle();
        Connection connection = connectionImpl.getConnections();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM vehicle WHERE id = ?");
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        Integer personId = null;
        Person person = new Person();
        Set<Tyre> tyres = new LinkedHashSet<>();
        while (resultSet.next()) {
            vehicle.setId(resultSet.getInt("id"));
            vehicle.setType(resultSet.getString("type"));
            vehicle.setModel(resultSet.getString("model"));
            personId = resultSet.getInt("person_id");
            vehicle.setPerson(person.getId());
            vehicle.setTyres(tyres);
        }

        preparedStatement = connection.prepareStatement("SELECT * FROM person WHERE id = ?");
        preparedStatement.setInt(1, personId);
        resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            person.setId(resultSet.getInt("id"));
            person.setFirstName(resultSet.getString("first_name"));
            person.setLastName(resultSet.getString("last_name"));
        }

        preparedStatement = connection.prepareStatement("SELECT * FROM tyre JOIN vehicle_tyre vt on " +
                "tyre.id = vt.tyre_id WHERE vehicle_id = ?");
        preparedStatement.setInt(1, vehicle.getId());
        resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            Tyre tyre = new Tyre();
            tyre.setId(resultSet.getInt("id"));
            tyre.setName(resultSet.getString("name"));
            tyre.setSeason(resultSet.getString("season"));
            tyres.add(tyre);
        }

        //connectionPool.releaseConnection(connection);
        return vehicle;
    }

    @Override
    public List<Vehicle> readAll() throws SQLException {

        List<Vehicle> vehicles = new ArrayList<>();
        Connection connection = connectionImpl.getConnections();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM vehicle");
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            Vehicle vehicle = readById(id);
            vehicles.add(vehicle);
        }
        //connectionPool.releaseConnection(connection);
        return vehicles;
    }

    @Override
    public Vehicle update(Vehicle vehicle) throws SQLException {

        Connection connection = connectionImpl.getConnections();
        int id = vehicle.getId();
        String type = vehicle.getType();
        String model = vehicle.getModel();
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE vehicle SET " +
                "type = ?, model = ? WHERE id = ?");
        preparedStatement.setString(1, type);
        preparedStatement.setString(2, model);
        preparedStatement.setInt(3, id);
        preparedStatement.execute();
        //connectionPool.releaseConnection(connection);
        return vehicle;
    }

    @Override
    public void deleteById(int id) throws SQLException {

        Connection connection = connectionImpl.getConnections();
        PreparedStatement deleteStatement = connection.prepareStatement("DELETE FROM vehicle WHERE id = ?");
        deleteStatement.setInt(1, id);
        deleteStatement.executeUpdate();
        //connectionPool.releaseConnection(connection);
    }

    private VehicleDaoImpl() {
    }

    private static class VehicleDoaImplHolder {
        private final static VehicleDaoImpl instance = new VehicleDaoImpl();
    }

    public static VehicleDaoImpl getInstance() {
        return VehicleDaoImpl.VehicleDoaImplHolder.instance;
    }
}