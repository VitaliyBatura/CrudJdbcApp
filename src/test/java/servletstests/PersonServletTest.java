package servletstests;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dao.mapper.PersonMapper;
import org.example.model.entity.Person;
import org.example.service.PersonService;
import org.example.servlet.PersonServlet;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.*;
import java.sql.SQLException;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class PersonServletTest {

    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private PersonService personService;
    @Mock
    private PersonMapper personMapper;
    @Spy
    private ObjectMapper objectMapper;
    @InjectMocks
    private PersonServlet personServlet;

    @Test
    void testDoGet() throws IOException, SQLException {

        when(request.getParameter("id")).thenReturn(String.valueOf(1));
        when(response.getWriter()).thenReturn(new PrintWriter(new StringWriter()));
        personServlet.doGet(request, response);
        verify(personService).handleGetRequest(String.valueOf(1));
    }

    @Test
    void testDoPost() throws SQLException, IOException {

        String json = "{\"firstName\":\"Stepan\",\"lastName\":\"Sokolov\"}";
        when(request.getReader()).thenReturn(new BufferedReader(new StringReader(json)));
        when(response.getWriter()).thenReturn(new PrintWriter(new StringWriter()));
        personServlet.doPost(request, response);
        verify(personService).handlePostRequest(any(Person.class));
    }

    @Test
    void testDoPut() throws IOException, SQLException {

        String json = "{\"firstName\":\"Stepan\",\"lastName\":\"Sokolov\"}";
        when(request.getReader()).thenReturn((new BufferedReader(new StringReader(json))));
        when(response.getWriter()).thenReturn(new PrintWriter(new StringWriter()));
        personServlet.doPut(request, response);
        verify(personService).handlePutRequest(any(Person.class));
    }

    @Test
    void testDoDelete() throws SQLException {

        when(request.getParameter("id")).thenReturn(String.valueOf(1));
        doNothing().when(personService).handleDeleteRequest(anyInt());
        personServlet.doDelete(request, response);
        verify(personService).handleDeleteRequest(anyInt());
    }
}
