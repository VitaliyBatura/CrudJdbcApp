package org.example.servlet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dao.mapper.PersonMapper;
import org.example.model.dto.PersonDto;
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

@WebServlet(urlPatterns = "/person/*")
public class PersonServlet extends HttpServlet {

    private PersonService personService;

    private ObjectMapper objectMapper = new ObjectMapper();

    private PersonMapper personMapper;

    @Override
    public void init() {
        personService = (PersonService) getServletContext().getAttribute("personService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String id = req.getParameter("id");
        resp.setContentType("application/json; charset=UTF-8");
        resp.setStatus(200);
        try {
            String getResponse = personService.handleGetRequest(id).get();
            PrintWriter printWriter = resp.getWriter();
            printWriter.write(getResponse);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {

        try {
            personService.handlePostRequest(getPersonDtoFromRequestBody(req));
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) {

        try {
            personService.handlePutRequest(getPersonDtoFromRequestBody(req));
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {

        try {
            personService.handleDeleteRequest(Integer.parseInt(req.getParameter("id")));
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    private Person getPersonDtoFromRequestBody(HttpServletRequest req) throws IOException {

        String requestBody = null;
        try {
            requestBody = req.getReader().lines().collect(Collectors.joining());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            PersonDto persondto = objectMapper.readValue(requestBody, PersonDto.class);
            return personMapper.convertToPerson(persondto);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}