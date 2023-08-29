package org.example.model.entity;

import java.util.Objects;
import java.util.Set;

public class Tyre {

    private int id;
    private String name;
    private String season;
    private Set<Vehicle> vehicles;

    public Tyre() {
    }

    public Tyre(int id, String name, String season, Set<Vehicle> vehicles) {
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

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tyre tyre = (Tyre) o;
        return id == tyre.id && Objects.equals(name, tyre.name) && Objects.equals(season, tyre.season)
                && Objects.equals(vehicles, tyre.vehicles);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, season, vehicles);
    }
}