package org.example.dao;

import org.example.model.entity.Vehicle;

import java.sql.SQLException;
import java.util.List;

public interface VehicleDao {

    void create(Vehicle vehicle) throws SQLException;

    Vehicle readById(int id) throws SQLException;

    List<Vehicle> readAll() throws SQLException;

    void update(Vehicle vehicle) throws SQLException;

    void deleteById(int id) throws SQLException;
}
