package testcontainers;

import org.example.dao.PersonDao;
import org.example.dao.impl.PersonDaoImpl;
import org.example.model.entity.Person;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class PersonDaoImplTest extends AbstractPostgresContainer {

    private static final PersonDao personDao = PersonDaoImpl.getInstance();

    @Test
    void testCreate() throws SQLException {

        Person person = new Person("Stepan", "Sokolov");
        Person createdPerson = personDao.create(person);
        assertEquals(1, personDao.readAll().size(),
                "The readAll method must return a list with size = 1");
        personDao.deleteById(createdPerson.getId());
    }

    @Test
    void testReadById() throws SQLException {

        Person person = new Person("Stepan", "Sokolov");
        Person createdPerson = personDao.create(person);
        int id = createdPerson.getId();
        assertEquals(createdPerson.getFirstName(), personDao.readById(id).getFirstName(),
                "The readById method must return personName which equals to createdPersonName");
        personDao.deleteById(createdPerson.getId());
    }

    @Test
    void testReadAll() throws SQLException {

        Person person = new Person("Stepan", "Sokolov");
        Person person2 = new Person("Petr", "Orlov");
        Person createdPerson = personDao.create(person);
        Person createdPerson2 = personDao.create(person2);
        assertEquals(2, personDao.readAll().size(),
                "The readAll method must return a list with size = 2");
        personDao.deleteById(createdPerson.getId());
        personDao.deleteById(createdPerson2.getId());
    }

    @Test
    void testUpdate() throws SQLException {

        Person person = new Person("Stepan", "Sokolov");
        Person createdPerson = personDao.create(person);
        Person updatedPerson = personDao.update(createdPerson);
        int id = updatedPerson.getId();
        assertEquals("Stepan", personDao.readById(id).getFirstName(),
                "Person.getFirstName must return \"Stepan\"");
        personDao.deleteById(id);
    }

    @Test
    void testDeleteById() throws SQLException {

        Person person = new Person("Stepan", "Sokolov");
        Person createdPerson = personDao.create(person);
        int id = createdPerson.getId();
        personDao.deleteById(id);
        assertEquals(0, personDao.readAll().size(),
                "The readAll method must return a list with size = 0");
    }
}