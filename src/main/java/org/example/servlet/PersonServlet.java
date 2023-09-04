package org.example.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dao.mapper.PersonMapper;
import org.example.model.entity.Person;
import org.example.service.PersonService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.stream.Collectors;

@WebServlet(value = "/person/*")
public class PersonServlet extends HttpServlet {

    private PersonService personService = PersonService.getInstance();
    private ObjectMapper objectMapper = new ObjectMapper();

    private PersonMapper personMapper = new PersonMapper();

    @Override
    public void init() {
        personService = (PersonService) getServletContext().getAttribute("personService");
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String id = req.getParameter("id");
        resp.setContentType("application/json; charset=UTF-8");
        resp.setStatus(200);
        try {
            String getResponse = personService.handleGetRequest(id).toString();
            PrintWriter printWriter = resp.getWriter();
            printWriter.write(getResponse);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) {

        String responseJson;
        try {
            String requestBody = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
            Person person = objectMapper.readValue(requestBody, Person.class);
            personService.handlePostRequest(person);
            responseJson = objectMapper.writeValueAsString(person);
            resp.getWriter().append(responseJson);
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void doPut(HttpServletRequest req, HttpServletResponse resp) {

        String responseJson;
        try {
            String requestBody = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
            Person person = objectMapper.readValue(requestBody, Person.class);
            personService.handlePutRequest(person);
            responseJson = objectMapper.writeValueAsString(person);
            resp.getWriter().append(responseJson);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void doDelete(HttpServletRequest req, HttpServletResponse resp) {

        try {
            int id = Integer.parseInt(req.getParameter("id"));
            personService.handleDeleteRequest(id);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }
}
