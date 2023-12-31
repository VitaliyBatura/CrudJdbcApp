package org.example.dao;

import org.example.model.entity.Person;

import java.sql.SQLException;
import java.util.List;

public interface PersonDao {

    Person create(Person person) throws SQLException;

    Person readById(int id) throws SQLException;

    List<Person> readAll() throws SQLException;

    Person update(Person person) throws SQLException;

    void deleteById(int id) throws SQLException;
}
