package org.example.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dao.impl.TyreDaoImpl;
import org.example.dao.impl.VehicleDaoImpl;
import org.example.dao.mapper.VehicleMapper;
import org.example.model.dto.VehicleDto;
import org.example.model.entity.Vehicle;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class VehicleService {

    private ObjectMapper objectMapper = new ObjectMapper();
    private VehicleDaoImpl vehicleDao;
    private TyreDaoImpl tyreDao;

    VehicleMapper vehicleMapper = new VehicleMapper();

    public VehicleService(VehicleDaoImpl vehicleDao, TyreDaoImpl tyreDao) {

        this.vehicleDao = vehicleDao;
        this.tyreDao = tyreDao;
    }

    public VehicleService(VehicleDaoImpl vehicleDao, TyreDaoImpl tyreDao, VehicleMapper vehicleMapper) {
        this.vehicleDao = vehicleDao;
        this.tyreDao = tyreDao;
        this.vehicleMapper = vehicleMapper;
    }

    public Optional<String> handleGetRequest(String parameter) throws SQLException {

        if (parameter == null) {
            List<VehicleDto> vehicles = vehicleDao.readAll().stream().map(vehicle ->
                    vehicleMapper.convertToVehicleDto(vehicle)).collect(Collectors.toList());
            try {
                return Optional.ofNullable(objectMapper.writeValueAsString(vehicles));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            return Optional.empty();
        } else {
            int id = Integer.parseInt(parameter);
            Vehicle vehicle = vehicleDao.readById(id);
            VehicleDto vehicleDto = vehicleMapper.convertToVehicleDto(vehicle);
            try {
                return Optional.ofNullable(objectMapper.writeValueAsString(vehicleDto));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        return Optional.empty();
    }

    public void handlePostRequest(Vehicle vehicle) throws SQLException {

        if (vehicle != null) {
            vehicleDao.create(vehicle);
        }
    }

    public void handlePutRequest(Vehicle vehicle) throws SQLException {

        if (vehicle != null) {
            vehicleDao.update(vehicle);
        }
    }

    public void handleDeleteRequest(int vehicleId) throws SQLException {

        vehicleDao.deleteById(vehicleId);
    }
}