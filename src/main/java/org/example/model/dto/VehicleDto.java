package org.example.model.dto;

import org.example.model.entity.Tyre;

import java.util.Set;

public class VehicleDto {

    private int id;
    private String type;
    private String model;
    private int personId;
    private Set<Tyre> tyres;

    public VehicleDto() {
    }

    public VehicleDto(int id, String type, String model, int personId, Set<Tyre> tyres) {
        this.id = id;
        this.type = type;
        this.model = model;
        this.personId = personId;
        this.tyres = tyres;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getPerson() {
        return personId;
    }

    public void setPerson(int personId) {
        this.personId = personId;
    }

    public Set<Tyre> getTyres() {
        return tyres;
    }

    public void setTyres(Set<Tyre> tyres) {
        this.tyres = tyres;
    }

}