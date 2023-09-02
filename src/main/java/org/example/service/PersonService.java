package org.example.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dao.impl.PersonDaoImpl;
import org.example.dao.mapper.PersonMapper;
import org.example.model.dto.PersonDto;
import org.example.model.entity.Person;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PersonService {

    private ObjectMapper objectMapper = new ObjectMapper();
    private PersonDaoImpl personDao;
    private PersonMapper personMapper;

    public PersonService() {
    }

    public PersonService(PersonDaoImpl personDao) {
        this.personDao = personDao;
    }

    public PersonService(PersonDaoImpl personDao, PersonMapper personMapper) {
        this.personDao = personDao;
        this.personMapper = personMapper;
    }

    public Optional<String> handleGetRequest(String parameter) throws SQLException {
        if (parameter == null) {
            List<PersonDto> persons = personDao.readAll().stream().map(person ->
                    personMapper.convertToPersonDto(person)).collect(Collectors.toList());
            try {
                return Optional.ofNullable(objectMapper.writeValueAsString(persons));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        } else {
            int id = Integer.parseInt(parameter);
            Person person = personDao.readById(id);
            PersonDto personDto = personMapper.convertToPersonDto(person);
            try {
                return Optional.ofNullable(objectMapper.writeValueAsString(personDto));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        return Optional.empty();
    }

    public void handlePostRequest(Person person) throws SQLException {

        if (person != null) {
            personDao.create(person);
        }
    }

    public void handlePutRequest(Person person) throws SQLException {
        personDao.update(person);
    }

    public void handleDeleteRequest(int personId) throws SQLException {

        personDao.deleteById(personId);
    }
}