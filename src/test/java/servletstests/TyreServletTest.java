package servletstests;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dao.mapper.TyreMapper;
import org.example.model.entity.Tyre;
import org.example.service.TyreService;
import org.example.servlet.TyreServlet;
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
class TyreServletTest {

    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private TyreService tyreService;
    @Mock
    private TyreMapper tyreMapper;
    @Spy
    private ObjectMapper objectMapper;
    @InjectMocks
    private TyreServlet tyreServlet;

    @Test
    void testDoGet() throws IOException, SQLException {

        when(request.getParameter("id")).thenReturn(String.valueOf(1));
        when(response.getWriter()).thenReturn(new PrintWriter(new StringWriter()));
        tyreServlet.doGet(request, response);
        verify(tyreService).handleGetRequest(String.valueOf(1));
    }

    @Test
    void testDoPost() throws SQLException, IOException {

        String json = "{\"name\":\"Michelin\",\"season\":\"Winter\"}";
        when(request.getReader()).thenReturn(new BufferedReader(new StringReader(json)));
        when(response.getWriter()).thenReturn(new PrintWriter(new StringWriter()));
        tyreServlet.doPost(request, response);
        verify(tyreService).handlePostRequest(any(Tyre.class));
    }

    @Test
    void testDoPut() throws IOException, SQLException {

        String json = "{\"name\":\"Michelin\",\"season\":\"Winter\"}";
        when(request.getReader()).thenReturn((new BufferedReader(new StringReader(json))));
        when(response.getWriter()).thenReturn(new PrintWriter(new StringWriter()));
        tyreServlet.doPut(request, response);
        verify(tyreService).handlePutRequest(any(Tyre.class));
    }

    @Test
    void testDoDelete() throws SQLException {

        when(request.getParameter("id")).thenReturn(String.valueOf(1));
        doNothing().when(tyreService).handleDeleteRequest(anyInt());
        tyreServlet.doDelete(request, response);
        verify(tyreService).handleDeleteRequest(anyInt());
    }
}
