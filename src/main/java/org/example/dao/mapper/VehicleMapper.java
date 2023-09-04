package org.example.dao.mapper;

import org.example.dao.impl.PersonDaoImpl;
import org.example.dao.impl.TyreDaoImpl;
import org.example.model.dto.VehicleDto;
import org.example.model.entity.Vehicle;

public class VehicleMapper {

    private PersonDaoImpl personDao;
    private TyreDaoImpl tyreDao;

    public VehicleMapper() {
    }

    public VehicleMapper(PersonDaoImpl personDao) {
        this.personDao = personDao;
    }

    public VehicleMapper(TyreDaoImpl tyreDao) {
        this.tyreDao = tyreDao;
    }

    public VehicleMapper(PersonDaoImpl personDao, TyreDaoImpl tyreDao) {
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
}