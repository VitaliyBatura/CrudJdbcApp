package servicestests;

import org.example.dao.impl.PersonDaoImpl;
import org.example.dao.impl.VehicleDaoImpl;
import org.example.dao.mapper.PersonMapper;
import org.example.model.entity.Person;
import org.example.model.entity.Vehicle;
import org.example.service.PersonService;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.SQLException;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {

    @Mock
    private PersonDaoImpl personDao;
    @Mock
    private PersonMapper personMapper;
    @Mock
    private VehicleDaoImpl vehicleDao;
    @InjectMocks
    private PersonService personService;

    public PersonServiceTest() {
        MockitoAnnotations.initMocks(this);
        this.personService = new PersonService(personDao);
        MockitoAnnotations.initMocks(this);
        this.personMapper = new PersonMapper(vehicleDao);
    }

    @Test
    public void testHandleGetRequest() throws SQLException {

        Mockito.when(personDao.readById(1)).thenReturn(new Person
                (1, "Stepan", "Sokolov", List.of(new Vehicle(), new Vehicle())));
        Mockito.when(personDao.readAll()).thenReturn(List.of(new Person(), new Person()));
        personService.handleGetRequest(String.valueOf(1));
        personService.handleGetRequest(null);
        Mockito.verify(personDao).readById(1);
        Mockito.verify(personDao).readAll();
    }

    @Test
    public void testHandlePostRequest() throws SQLException {

        Person person = new Person("Stepan", "Sokolov");
        Mockito.when(personDao.create(person)).thenReturn(new Person(1, "Stepan", "Sokolov"));
        personService.handlePostRequest(person);
        Mockito.verify(personDao).create(person);
    }

    @Test
    public void tessHandlePutRequest() throws SQLException {

        Person person = new Person("Stepan", "Sokolov");
        Mockito.when(personDao.update(person)).thenReturn(new Person("Stepan", "Sokolov"));
        personService.handlePutRequest(person);
        Mockito.verify(personDao).update(person);
    }

    @Test
    public void testHandleDeleteRequest() throws SQLException {

        Mockito.doNothing().when(personDao).deleteById(1);
        personService.handleDeleteRequest(1);
        Mockito.verify(personDao).deleteById(1);
    }
}
