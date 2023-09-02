package org.example.servlet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.dao.mapper.TyreMapper;
import org.example.model.dto.TyreDto;
import org.example.model.entity.Tyre;
import org.example.service.TyreService;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.stream.Collectors;

@WebServlet(urlPatterns = "/tyre/*")
public class TyreServlet extends HttpServlet {

    private TyreService tyreService;

    private ObjectMapper objectMapper = new ObjectMapper();

    private TyreMapper tyreMapper;

    @Override
    public void init() {
        tyreService = (TyreService) getServletContext().getAttribute("tyreService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String parameter = req.getParameter("id");
        resp.setContentType("application/json; charset=UTF-8");
        resp.setStatus(200);
        String getResponse = null;
        try {
            getResponse = tyreService.handleGetRequest(parameter).get();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        PrintWriter printWriter = resp.getWriter();
        if (getResponse != null) {
            printWriter.write(getResponse);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {

        try {
            tyreService.handlePostRequest(getTyreDtoFromRequestBody(req));
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) {

        try {
            tyreService.handlePutRequest(getTyreDtoFromRequestBody(req));
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {

        try {
            tyreService.handleDeleteRequest(Integer.parseInt(req.getParameter("id")));
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    private Tyre getTyreDtoFromRequestBody(HttpServletRequest req) {

        String requestBody = null;
        try {
            requestBody = req.getReader().lines().collect(Collectors.joining());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            TyreDto tyreDto = objectMapper.readValue(requestBody, TyreDto.class);
            return tyreMapper.convertToTyre(tyreDto);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
