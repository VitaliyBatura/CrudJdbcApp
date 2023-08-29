import org.example.dao.PersonDao;
import org.example.dao.impl.PersonDaoImpl;
import org.example.dao.mapper.PersonMapper;
import org.example.model.entity.Person;
import org.example.service.PersonService;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.SQLException;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PersonDaoImplTest {

    @Mock
    private PersonDao personDao;


    public PersonDaoImplTest() {
    }

//    public PersonServiceTest(PersonDao personDao) {
//        MockitoAnnotations.initMocks(this);
//        this.personService = new PersonService(personDao, personMapper);
//    }

    @Test
    public void testReadById() throws SQLException {
        Person person = new Person("Stepan", "Sokolov");
//        person.setId(1);
//        person.setFirstName("Stepan");
//        person.setLastName("Sokolov");
        when(personDao.readById(1)).thenReturn(person);
//        when(personDao.readAll()).thenReturn(List.of(new Person()));

//        when(personDao.create(person)).thenReturn(new Person("Stepan", "Sokolov"));

    }
}
