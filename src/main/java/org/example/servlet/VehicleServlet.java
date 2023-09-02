package org.example.servlet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.dao.mapper.VehicleMapper;
import org.example.model.dto.VehicleDto;
import org.example.model.entity.Vehicle;
import org.example.service.VehicleService;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.stream.Collectors;

@WebServlet(urlPatterns = "/vehicle/*")
public class VehicleServlet extends HttpServlet {

    private VehicleService vehicleService;

    private ObjectMapper objectMapper = new ObjectMapper();

    private VehicleMapper vehicleMapper;

    @Override
    public void init() {
        vehicleService = (VehicleService) getServletContext().getAttribute("vehicleService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String vehicleId = req.getParameter("id");
        resp.setContentType("application/json; charset=UTF-8");
        resp.setStatus(200);
        try {
            String responseBody = vehicleService.handleGetRequest(vehicleId).get();
            PrintWriter printWriter = resp.getWriter();
            printWriter.write(responseBody);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {

        try {
            vehicleService.handlePostRequest(getVehicleDtoFromReqBody(req));
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) {

        try {
            vehicleService.handlePutRequest(getVehicleDtoFromReqBody(req));
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {

        try {
            vehicleService.handleDeleteRequest(Integer.parseInt(req.getParameter("id")));
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    private Vehicle getVehicleDtoFromReqBody(HttpServletRequest req) {

        String requestBody = null;
        try {
            requestBody = req.getReader().lines().collect(Collectors.joining());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            VehicleDto vehicleDto = objectMapper.readValue(requestBody, VehicleDto.class);
            return vehicleMapper.convertToVehicle(vehicleDto);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
}