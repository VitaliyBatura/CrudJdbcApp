package org.example.servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.service.PersonService;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet(urlPatterns = "/person/*")
public class PersonServlet extends HttpServlet {

    private PersonService personService;

    @Override
    public void init() {
        personService = (PersonService) getServletContext().getAttribute("personService");
        ;
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
            personService.handlePostRequest(req);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) {

        try {
            personService.handlePutRequest(req);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {

        try {
            personService.handleDeleteRequest(req);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }
}