package org.example.model.entity;

import java.util.List;
import java.util.Objects;

public class Person {

    private int id;
    private String firstName;
    private String lastName;
    private List<Vehicle> vehicles;

    public Person() {
    }

    public Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Person(int id, String firstName, String lastName, List<Vehicle> vehicles) {

        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.vehicles = vehicles;
    }


    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    public void setVehicles(List<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return id == person.id && Objects.equals(firstName, person.firstName)
                && Objects.equals(lastName, person.lastName) && Objects.equals(vehicles, person.vehicles);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, firstName, lastName, vehicles);
    }
}