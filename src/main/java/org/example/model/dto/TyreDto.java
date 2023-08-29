package org.example.model.dto;

import org.example.model.entity.Vehicle;

import java.util.Set;

public class TyreDto {

    int id;
    private String name;
    private String season;
    private Set<Vehicle> vehicles;

    public TyreDto() {
    }

    public TyreDto(int id, String name, String season, Set<Vehicle> vehicles) {
        this.id = id;
        this.name = name;
        this.season = season;
        this.vehicles = vehicles;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public Set<Vehicle> getVehicles() {
        return vehicles;
    }

    public void setVehicles(Set<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }
}