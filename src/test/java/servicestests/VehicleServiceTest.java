package servicestests;

import org.example.dao.impl.PersonDaoImpl;
import org.example.dao.impl.TyreDaoImpl;
import org.example.dao.impl.VehicleDaoImpl;
import org.example.dao.mapper.VehicleMapper;
import org.example.model.entity.Person;
import org.example.model.entity.Vehicle;
import org.example.service.VehicleService;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.SQLException;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class VehicleServiceTest {

    @Mock
    private VehicleDaoImpl vehicleDao;
    @Mock
    private VehicleMapper vehicleMapper;
    @Mock
    private PersonDaoImpl personDao;
    @Mock
    private TyreDaoImpl tyreDao;
    @InjectMocks
    private VehicleService vehicleService;

    public VehicleServiceTest() {
        MockitoAnnotations.initMocks(this);
        this.vehicleMapper = new VehicleMapper(personDao, tyreDao);
        MockitoAnnotations.initMocks(this);
        this.vehicleService = new VehicleService(vehicleDao, tyreDao);
    }

    @Test
    public void testHandleGetRequest() throws SQLException {

        Person person = new Person("Stepan", "Sokolov");
        Mockito.when(vehicleDao.readById(1)).thenReturn(new Vehicle(1, "Car", "Lada", person.getId()));
        Mockito.when(vehicleDao.readAll()).thenReturn(List.of(new Vehicle(), new Vehicle(), new Vehicle()));
        vehicleService.handleGetRequest(String.valueOf(1));
        vehicleService.handleGetRequest(null);
        Mockito.verify(vehicleDao).readById(1);
        Mockito.verify(vehicleDao).readAll();
    }

    @Test
    public void testHandlePostRequest() throws SQLException {

        Person person = new Person("Stepan", "Sokolov");
        Vehicle vehicle = new Vehicle("Car", "Lada", person.getId());
        Mockito.when(vehicleDao.create(vehicle)).thenReturn(new Vehicle(1, "Car", "Lada", person.getId()));
        vehicleService.handlePostRequest(vehicle);
        Mockito.verify(vehicleDao).create(vehicle);
    }

    @Test
    public void tessHandlePutRequest() throws SQLException {

        Person person = new Person("Stepan", "Sokolov");
        Vehicle vehicle = new Vehicle("Car", "Lada", person.getId());
        Mockito.when(vehicleDao.update(vehicle)).thenReturn(new Vehicle("Car", "Lada", person.getId()));
        vehicleService.handlePutRequest(vehicle);
        Mockito.verify(vehicleDao).update(vehicle);
    }

    @Test
    public void testHandleDeleteRequest() throws SQLException {

        Mockito.doNothing().when(vehicleDao).deleteById(1);
        vehicleService.handleDeleteRequest(1);
        Mockito.verify(vehicleDao).deleteById(1);
    }
}
