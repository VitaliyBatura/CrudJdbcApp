package servicestests;

import org.example.dao.impl.TyreDaoImpl;
import org.example.dao.impl.VehicleDaoImpl;
import org.example.dao.mapper.TyreMapper;
import org.example.model.entity.Tyre;
import org.example.service.TyreService;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.SQLException;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class TyreServiceTest {

    @Mock
    private TyreDaoImpl tyreDao;
    @Mock
    private TyreMapper tyreMapper;
    @Mock
    private VehicleDaoImpl vehicleDao;
    @InjectMocks
    private TyreService tyreService;

    public TyreServiceTest() {
        MockitoAnnotations.initMocks(this);
        this.tyreMapper = new TyreMapper(vehicleDao);
        MockitoAnnotations.initMocks(this);
        this.tyreService = new TyreService(tyreDao);
    }

    @Test
    public void testHandleGetRequest() throws SQLException {

        Mockito.when(tyreDao.readById(1)).thenReturn(new Tyre("Michelin", "Winter"));
        Mockito.when(tyreDao.readAll()).thenReturn(List.of(new Tyre(), new Tyre()));
        tyreService.handleGetRequest(String.valueOf(1));
        tyreService.handleGetRequest(null);
        Mockito.verify(tyreDao).readById(1);
        Mockito.verify(tyreDao).readAll();
    }

    @Test
    public void testHandlePostRequest() throws SQLException {

        Tyre tyre = new Tyre("Michelin", "Winter");
        Mockito.when(tyreDao.create(tyre)).thenReturn(new Tyre(1, "Michelin", "Winter"));
        tyreService.handlePostRequest(tyre);
        Mockito.verify(tyreDao).create(tyre);
    }

    @Test
    public void tessHandlePutRequest() throws SQLException {

        Tyre tyre = new Tyre("Michelin", "Winter");
        Mockito.when(tyreDao.update(tyre)).thenReturn(tyre);
        tyreService.handlePutRequest(tyre);
        Mockito.verify(tyreDao).update(tyre);
    }

    @Test
    public void testHandleDeleteRequest() throws SQLException {

        Mockito.doNothing().when(tyreDao).deleteById(1);
        tyreService.handleDeleteRequest(1);
        Mockito.verify(tyreDao).deleteById(1);
    }
}
