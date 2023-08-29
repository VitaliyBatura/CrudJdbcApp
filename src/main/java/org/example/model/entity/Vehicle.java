package org.example.model.entity;

import java.util.Objects;
import java.util.Set;

public class Vehicle {

    private int id;
    private String type;
    private String model;
    private Person person;
    private Set<Tyre> tyres;

    public Vehicle() {
    }

    public Vehicle(int id, String type, String model, Person person, Set<Tyre> tyres) {
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

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vehicle vehicle = (Vehicle) o;
        return id == vehicle.id && Objects.equals(type, vehicle.type) && Objects.equals(model, vehicle.model)
                && Objects.equals(person, vehicle.person) && Objects.equals(tyres, vehicle.tyres);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, type, model, person, tyres);
    }
}