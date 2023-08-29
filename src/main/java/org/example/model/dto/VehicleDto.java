package org.example.model.dto;

import org.example.model.entity.Person;
import org.example.model.entity.Tyre;

import java.util.Set;

public class VehicleDto {

    private int id;
    private String type;
    private String model;
    private Person person;
    private Set<Tyre> tyres;

    public VehicleDto() {
    }

    public VehicleDto(int id, String type, String model, Person person, Set<Tyre> tyres) {
        this.id = id;
        this.type = type;
        this.model = model;
        this.person = person;
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

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Set<Tyre> getTyres() {
        return tyres;
    }

    public void setTyres(Set<Tyre> tyres) {
        this.tyres = tyres;
    }
}