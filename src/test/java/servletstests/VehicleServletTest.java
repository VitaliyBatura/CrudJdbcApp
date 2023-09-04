package servletstests;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dao.mapper.PersonMapper;
import org.example.model.entity.Vehicle;
import org.example.service.VehicleService;
import org.example.servlet.VehicleServlet;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.SQLException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class VehicleServletTest {

    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private VehicleService vehicleService;
    @Mock
    private PersonMapper personMapper;
    @Spy
    private ObjectMapper objectMapper;
    @InjectMocks
    private VehicleServlet vehicleServlet;

    @Test
    void testDoGet() throws IOException, SQLException {

        when(request.getParameter("id")).thenReturn(String.valueOf(1));
        when(response.getWriter()).thenReturn(new PrintWriter(new StringWriter()));
        vehicleServlet.doGet(request, response);
        verify(vehicleService).handleGetRequest(String.valueOf(1));
    }

    @Test
    void testDoPost() throws SQLException, IOException {

        String json = "{\"type\":\"Car\",\"model\":\"Lada\"}";
        when(request.getReader()).thenReturn(new BufferedReader(new StringReader(json)));
        when(response.getWriter()).thenReturn(new PrintWriter(new StringWriter()));
        vehicleServlet.doPost(request, response);
        verify(vehicleService).handlePostRequest(any(Vehicle.class));
    }

    @Test
    void testDoPut() throws IOException, SQLException {

        String json = "{\"type\":\"Car\",\"model\":\"Lada\"}";
        when(request.getReader()).thenReturn((new BufferedReader(new StringReader(json))));
        when(response.getWriter()).thenReturn(new PrintWriter(new StringWriter()));
        vehicleServlet.doPut(request, response);
        verify(vehicleService).handlePutRequest(any(Vehicle.class));
    }

    @Test
    void testDoDelete() throws SQLException {

        when(request.getParameter("id")).thenReturn(String.valueOf(1));
        doNothing().when(vehicleService).handleDeleteRequest(anyInt());
        vehicleServlet.doDelete(request, response);
        verify(vehicleService).handleDeleteRequest(anyInt());
    }
}
