package org.example.dao.mapper;

import org.example.dao.impl.VehicleDaoImpl;
import org.example.model.dto.TyreDto;
import org.example.model.entity.Tyre;

public class TyreMapper {

    private VehicleDaoImpl vehicleDao;

    public TyreMapper() {
    }

    public TyreMapper(VehicleDaoImpl vehicleDao) {
        this.vehicleDao = vehicleDao;
    }

    public TyreDto convertToTyreDto(Tyre tyre) {
        TyreDto tyreDto = new TyreDto();
        tyreDto.setId(tyre.getId());
        tyreDto.setName(tyre.getName());
        tyreDto.setSeason(tyre.getSeason());
        tyreDto.setVehicles(tyre.getVehicles());
        return tyreDto;
    }
}