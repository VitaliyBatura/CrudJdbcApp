package org.example.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dao.mapper.VehicleMapper;
import org.example.model.entity.Vehicle;
import org.example.service.VehicleService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.stream.Collectors;

@WebServlet(value = "/vehicle/*")
public class VehicleServlet extends HttpServlet {

    private VehicleService vehicleService = VehicleService.getInstance();

    private ObjectMapper objectMapper = new ObjectMapper();

    private VehicleMapper vehicleMapper = new VehicleMapper();

    @Override
    public void init() {
        vehicleService = (VehicleService) getServletContext().getAttribute("vehicleService");
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String vehicleId = req.getParameter("id");
        resp.setContentType("application/json; charset=UTF-8");
        resp.setStatus(200);
        try {
            String responseBody = vehicleService.handleGetRequest(vehicleId).toString();
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
            Vehicle vehicle = objectMapper.readValue(requestBody, Vehicle.class);
            vehicleService.handlePostRequest(vehicle);
            responseJson = objectMapper.writeValueAsString(vehicle);
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
            Vehicle vehicle = objectMapper.readValue(requestBody, Vehicle.class);
            vehicleService.handlePutRequest(vehicle);
            responseJson = objectMapper.writeValueAsString(vehicle);
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
            vehicleService.handleDeleteRequest(id);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }
}