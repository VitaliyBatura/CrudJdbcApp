package org.example.model.entity;

import java.util.Objects;
import java.util.Set;

public class Vehicle {

    private int id;
    private String type;
    private String model;
    private int personId;
    private Set<Tyre> tyres;

    public Vehicle() {
    }

    public Vehicle(int id, String type, String model, int personId, Set<Tyre> tyres) {
        this.id = id;
        this.type = type;
        this.model = model;
        this.personId = personId;
        this.tyres = tyres;
    }

    public Vehicle(String type, String model) {
        this.type = type;
        this.model = model;
    }

    public Vehicle(int id, String type, String model, int personId) {
        this.id = id;
        this.type = type;
        this.model = model;
        this.personId = personId;
    }

    public Vehicle(String type, String model, int personId) {
        this.type = type;
        this.model = model;
        this.personId = personId;
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

    public void setPerson(int person) {
        this.personId = personId;
    }

    public Set<Tyre> getTyres() {
        return tyres;
    }

    public void setTyres(Set<Tyre> tyres) {
        this.tyres = tyres;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vehicle vehicle = (Vehicle) o;
        return id == vehicle.id && Objects.equals(type, vehicle.type) && Objects.equals(model, vehicle.model)
                && Objects.equals(personId, vehicle.personId) && Objects.equals(tyres, vehicle.tyres);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, type, model, personId, tyres);
    }
}