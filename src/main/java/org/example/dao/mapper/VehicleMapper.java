package org.example.dao.mapper;

import org.example.dao.PersonDao;
import org.example.dao.TyreDao;
import org.example.model.dto.VehicleDto;
import org.example.model.entity.Vehicle;

public class VehicleMapper {

    private PersonDao personDao;
    private TyreDao tyreDao;

    public VehicleMapper(PersonDao personDao, TyreDao tyreDao) {
        this.personDao = personDao;
        this.tyreDao = tyreDao;
    }

    public VehicleDto convertToVehicleDto(Vehicle vehicle) {
        VehicleDto vehicleDto = new VehicleDto();
        vehicleDto.setId(vehicle.getId());
        vehicleDto.setType(vehicle.getType());
        vehicleDto.setModel(vehicle.getModel());
        vehicleDto.setPerson(vehicle.getPerson());
        vehicleDto.setTyres(vehicle.getTyres());
        return vehicleDto;
    }

    public Vehicle convertToVehicle(VehicleDto vehicleDto) {
        Vehicle vehicle = new Vehicle();
        vehicle.setId(vehicleDto.getId());
        vehicle.setType(vehicleDto.getType());
        vehicle.setModel(vehicleDto.getModel());
        vehicle.setPerson(vehicleDto.getPerson());
        vehicle.setTyres(vehicleDto.getTyres());
        return vehicle;
    }
}