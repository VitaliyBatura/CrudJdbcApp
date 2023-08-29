package org.example.servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.service.TyreService;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet(urlPatterns = "/tyre/*")
public class TyreServlet extends HttpServlet {

    TyreService tyreService;

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
            tyreService.handlePostRequest(req);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) {

        try {
            tyreService.handlePutRequest(req);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {

        try {
            tyreService.handleDeleteRequest(req);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }
}
