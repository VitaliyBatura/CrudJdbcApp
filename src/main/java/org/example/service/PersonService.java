package org.example.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.example.dao.PersonDao;
import org.example.dao.mapper.PersonMapper;
import org.example.model.dto.PersonDto;
import org.example.model.entity.Person;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PersonService {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private PersonDao personDao;
    private PersonMapper personMapper;

    public PersonService() {
    }

    public PersonService(PersonDao personDao, PersonMapper personMapper) {
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

    public void handlePostRequest(HttpServletRequest req) throws SQLException {

        PersonDto personDto = getPersonDtoFromRequestBody(req);
        if (personDto != null) {
            personDao.create(personMapper.convertToPerson(personDto));
        }
    }

    public void handlePutRequest(HttpServletRequest req) throws SQLException {

        PersonDto personDto = getPersonDtoFromRequestBody(req);
        if (personDto != null) {
            personDao.update(personMapper.convertToPerson(personDto));
        }
    }

    public void handleDeleteRequest(HttpServletRequest req) throws SQLException {

        int personId = Integer.parseInt(req.getParameter("id"));
        personDao.deleteById(personId);
    }

    private PersonDto getPersonDtoFromRequestBody(HttpServletRequest req) {

        String requestBody = null;
        try {
            requestBody = req.getReader().lines().collect(Collectors.joining());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            return objectMapper.readValue(requestBody, PersonDto.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
}