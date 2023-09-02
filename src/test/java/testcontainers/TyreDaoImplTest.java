package testcontainers;

import org.example.dao.TyreDao;
import org.example.dao.impl.TyreDaoImpl;
import org.example.model.entity.Tyre;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TyreDaoImplTest extends AbstractPostgresContainer {

    private static final TyreDao tyreDao = TyreDaoImpl.getInstance();

    @Test
    void testCreate() throws SQLException {

        Tyre tyre = new Tyre("Michelin", "Winter");
        Tyre createdTyre = tyreDao.create(tyre);
        assertEquals(1, tyreDao.readAll().size(),
                "The readAll method must return a list with size = 1");
        tyreDao.deleteById(createdTyre.getId());
    }

    @Test
    void testReadById() throws SQLException {

        Tyre tyre = new Tyre("Michelin", "Winter");
        Tyre createdTyre = tyreDao.create(tyre);
        int id = createdTyre.getId();
        assertEquals(createdTyre.getName(), tyreDao.readById(id).getName(),
                "The readById method must return tyreName which equals to createdTyreName");
        tyreDao.deleteById(createdTyre.getId());
    }

    @Test
    void testReadAll() throws SQLException {

        Tyre tyre = new Tyre("Michelin", "Winter");
        Tyre tyre2 = new Tyre("Bridgestone", "Summer");
        Tyre createdTyre = tyreDao.create(tyre);
        Tyre createdTyre2 = tyreDao.create(tyre2);
        int id = createdTyre.getId();
        int id2 = createdTyre2.getId();
        assertEquals(2, tyreDao.readAll().size(),
                "The readAll method must return a list with size = 2");
        tyreDao.deleteById(id);
        tyreDao.deleteById(id2);
    }

    @Test
    void testUpdate() throws SQLException {

        Tyre tyre = new Tyre("Michelin", "Winter");
        Tyre createdTyre = tyreDao.create(tyre);
        Tyre updatedTyre = tyreDao.update(createdTyre);
        int id = updatedTyre.getId();
        assertEquals("Michelin", tyreDao.readById(id).getName(),
                "Tyre.getName must return \"Michelin\"");
        tyreDao.deleteById(id);
    }

    @Test
    void testDeleteById() throws SQLException {

        Tyre tyre = new Tyre("Michelin", "Winter");
        Tyre createdTyre = tyreDao.create(tyre);
        int id = createdTyre.getId();
        tyreDao.deleteById(id);
        assertEquals(0, tyreDao.readAll().size(),
                "The readAll method must return a list with size = 0");
    }
}
