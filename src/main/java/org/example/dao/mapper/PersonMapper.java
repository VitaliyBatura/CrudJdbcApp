package org.example.dao.mapper;

import org.example.dao.impl.VehicleDaoImpl;
import org.example.model.dto.PersonDto;
import org.example.model.entity.Person;

public class PersonMapper {

    private VehicleDaoImpl vehicleDao;

    public PersonMapper() {
    }

    public PersonMapper(VehicleDaoImpl vehicleDao) {
        this.vehicleDao = vehicleDao;
    }

    public PersonDto convertToPersonDto(Person person) {

        PersonDto personDto = new PersonDto();
        personDto.setId(person.getId());
        personDto.setFirstName(person.getFirstName());
        personDto.setLastName(person.getLastName());
        personDto.setVehicles(person.getVehicles());
        return personDto;
    }

    public Person convertToPerson(PersonDto personDto) {

        Person person = new Person();
        person.setId(personDto.getId());
        person.setFirstName(personDto.getFirstName());
        person.setLastName(personDto.getLastName());
        person.setVehicles(personDto.getVehicles());
        return person;
    }
}