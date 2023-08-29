package org.example.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.example.dao.VehicleDao;
import org.example.dao.mapper.VehicleMapper;
import org.example.model.dto.VehicleDto;
import org.example.model.entity.Vehicle;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class VehicleService {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private VehicleDao vehicleDao;
    private VehicleMapper vehicleMapper;

    public VehicleService(VehicleDao vehicleDao, VehicleMapper vehicleMapper) {
        this.vehicleDao = vehicleDao;
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

    public void handlePostRequest(HttpServletRequest req) throws SQLException {

        VehicleDto vehicleDto = getVehicleDtoFromReqBody(req);
        if (vehicleDto != null) {
            vehicleDao.create(vehicleMapper.convertToVehicle(vehicleDto));
        }
    }

    public void handlePutRequest(HttpServletRequest req) throws SQLException {

        VehicleDto vehicleDto = getVehicleDtoFromReqBody(req);
        if (vehicleDto != null) {
            vehicleDao.update(vehicleMapper.convertToVehicle(vehicleDto));
        }
    }

    public void handleDeleteRequest(HttpServletRequest req) throws SQLException {

        int id = Integer.parseInt(req.getParameter("id"));
        vehicleDao.deleteById(id);
    }

    private VehicleDto getVehicleDtoFromReqBody(HttpServletRequest req) {

        String requestBody = null;
        try {
            requestBody = req.getReader().lines().collect(Collectors.joining());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            return objectMapper.readValue(requestBody, VehicleDto.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
}