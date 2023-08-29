package org.example.servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.service.VehicleService;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet(urlPatterns = "/vehicle/*")
public class VehicleServlet extends HttpServlet {

    private VehicleService vehicleService;

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
            vehicleService.handlePostRequest(req);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) {

        try {
            vehicleService.handlePutRequest(req);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {

        try {
            vehicleService.handleDeleteRequest(req);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }
}