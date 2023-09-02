package testcontainers;

import org.example.dao.PersonDao;
import org.example.dao.VehicleDao;
import org.example.dao.impl.PersonDaoImpl;
import org.example.dao.impl.VehicleDaoImpl;
import org.example.model.entity.Person;
import org.example.model.entity.Vehicle;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class VehicleDaoImplTest extends AbstractPostgresContainer {

    private static final VehicleDao vehicleDao = VehicleDaoImpl.getInstance();
    private static final PersonDao personDao = PersonDaoImpl.getInstance();

    @Test
    void testCreate() throws SQLException {

        Person person = new Person("Stepan", "Sokolov");
        Person createdPerson = personDao.create(person);
        Vehicle vehicle = new Vehicle("Car", "Lada", createdPerson.getId());
        Vehicle createdVehicle = vehicleDao.create(vehicle);
        assertEquals(1, vehicleDao.readAll().size(),
                "The readAll method must return a list with size = 1");
        vehicleDao.deleteById(createdVehicle.getId());
        personDao.deleteById(createdPerson.getId());
    }

    @Test
    void testReadById() throws SQLException {

        Person person = new Person("Stepan", "Sokolov");
        Person createdPerson = personDao.create(person);
        Vehicle vehicle = new Vehicle("Car", "Lada", createdPerson.getId());
        Vehicle createdVehicle = vehicleDao.create(vehicle);
        int id = createdVehicle.getId();
        int personId = createdPerson.getId();
        assertEquals(createdVehicle.getType(), vehicleDao.readById(id).getType(),
                "The readById method must return vehicleType which equals to createdVehicleType");
        vehicleDao.deleteById(id);
        personDao.deleteById(personId);
    }

    @Test
    void testReadAll() throws SQLException {

        Person person = new Person("Stepan", "Sokolov");
        Person person2 = new Person("Petr", "Orlov");
        Person createdPerson = personDao.create(person);
        Person createdPerson2 = personDao.create(person2);
        Vehicle vehicle = new Vehicle("Car", "Lada", createdPerson.getId());
        Vehicle createdVehicle = vehicleDao.create(vehicle);
        Vehicle vehicle2 = new Vehicle("Truck", "Volvo", createdPerson2.getId());
        Vehicle createdVehicle2 = vehicleDao.create(vehicle2);
        int id = createdVehicle.getId();
        int id2 = createdVehicle2.getId();
        int personId = createdPerson.getId();
        int personId2 = createdPerson2.getId();
        assertEquals(2, vehicleDao.readAll().size(),
                "The readAll method must return a list with size = 2");
        personDao.deleteById(personId);
        personDao.deleteById(personId2);
        vehicleDao.deleteById(id);
        vehicleDao.deleteById(id2);
    }

    @Test
    void testUpdate() throws SQLException {

        Person person = new Person("Stepan", "Sokolov");
        Person createdPerson = personDao.create(person);
        Vehicle vehicle = new Vehicle("Car", "Lada", createdPerson.getId());
        Vehicle createdVehicle = vehicleDao.create(vehicle);
        Vehicle updatedVehicle = vehicleDao.update(createdVehicle);
        int id = updatedVehicle.getId();
        int personId = createdPerson.getId();
        assertEquals("Car", vehicleDao.readById(id).getType(),
                "Vehicle.getType must return \"Car\"");
        vehicleDao.deleteById(id);
        personDao.deleteById(personId);
    }

    @Test
    void testDeleteById() throws SQLException {

        Person person = new Person("Stepan", "Sokolov");
        Person createdPerson = personDao.create(person);
        int personId = createdPerson.getId();
        Vehicle vehicle = new Vehicle("Car", "Lada", createdPerson.getId());
        Vehicle createdVehicle = vehicleDao.create(vehicle);
        int id = createdVehicle.getId();
        vehicleDao.deleteById(id);
        personDao.deleteById(personId);
        assertEquals(0, vehicleDao.readAll().size(),
                "The readAll method must return a list with size = 0");
    }
}
