package org.example.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dao.mapper.TyreMapper;
import org.example.model.entity.Tyre;
import org.example.service.TyreService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.stream.Collectors;

@WebServlet(value = "/tyre/*")
public class TyreServlet extends HttpServlet {

    private TyreService tyreService = TyreService.getInstance();
    private ObjectMapper objectMapper = new ObjectMapper();

    private TyreMapper tyreMapper = new TyreMapper();

    @Override
    public void init() {
        tyreService = (TyreService) getServletContext().getAttribute("tyreService");
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String tyreId = req.getParameter("id");
        resp.setContentType("application/json; charset=UTF-8");
        resp.setStatus(200);
        try {
            String responseBody = tyreService.handleGetRequest(tyreId).toString();
            PrintWriter printWriter = resp.getWriter();
            printWriter.write(responseBody);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) {

        String responseJson;
        try {
            String requestBody = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
            Tyre tyre = objectMapper.readValue(requestBody, Tyre.class);
            tyreService.handlePostRequest(tyre);
            responseJson = objectMapper.writeValueAsString(tyre);
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
            Tyre tyre = objectMapper.readValue(requestBody, Tyre.class);
            tyreService.handlePutRequest(tyre);
            responseJson = objectMapper.writeValueAsString(tyre);
            resp.getWriter().append(responseJson);
        } catch (SQLException | IOException throwable) {
            throwable.printStackTrace();
        }
    }

    @Override
    public void doDelete(HttpServletRequest req, HttpServletResponse resp) {

        try {
            int id = Integer.parseInt(req.getParameter("id"));
            tyreService.handleDeleteRequest(id);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }
}
