package org.example.dao.mapper;

import org.example.dao.VehicleDao;
import org.example.model.dto.TyreDto;
import org.example.model.entity.Tyre;

public class TyreMapper {

    private VehicleDao vehicleDao;

    public TyreMapper(VehicleDao vehicleDao) {
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

    public Tyre convertToTyre(TyreDto tyreDto) {
        Tyre tyre = new Tyre();
        tyre.setId(tyreDto.getId());
        tyre.setName(tyreDto.getName());
        tyre.setSeason(tyreDto.getSeason());
        tyre.setVehicles(tyreDto.getVehicles());
        return tyre;
    }
}