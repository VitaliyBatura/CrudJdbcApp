package org.example.servlet;

import jakarta.servlet.annotation.WebListener;
import org.example.dao.ConnectionPool;
import org.example.dao.PersonDao;
import org.example.dao.TyreDao;
import org.example.dao.VehicleDao;
import org.example.dao.impl.ConnectionPoolImpl;
import org.example.dao.impl.PersonDaoImpl;
import org.example.dao.impl.TyreDaoImpl;
import org.example.dao.impl.VehicleDaoImpl;
import org.example.dao.mapper.PersonMapper;
import org.example.dao.mapper.TyreMapper;
import org.example.dao.mapper.VehicleMapper;
import org.example.service.PersonService;
import org.example.service.TyreService;
import org.example.service.VehicleService;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;

@WebListener
public class ContextListener implements ServletContextListener {

    private ConnectionPool connectionPool;
    private PersonDao personDao;
    private VehicleDao vehicleDao;
    private TyreDao tyreDao;
    private PersonMapper personMapper;
    private VehicleMapper vehicleMapper;
    private TyreMapper tyreMapper;
    private PersonService personService;
    private VehicleService vehicleService;
    private TyreService tyreService;

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        final ServletContext servletContext = sce.getServletContext();
        connectionPool = ConnectionPoolImpl.getConnectionPool();
        personDao = new PersonDaoImpl(connectionPool);
        vehicleDao = new VehicleDaoImpl(connectionPool);
        tyreDao = new TyreDaoImpl(connectionPool);
        personMapper = new PersonMapper(vehicleDao);
        vehicleMapper = new VehicleMapper(personDao, tyreDao);
        tyreMapper = new TyreMapper(vehicleDao);
        personService = new PersonService(personDao, personMapper);
        vehicleService = new VehicleService(vehicleDao, vehicleMapper);
        tyreService = new TyreService(tyreDao, tyreMapper);

        servletContext.setAttribute("personService", personService);
        servletContext.setAttribute("vehicleService", vehicleService);
        servletContext.setAttribute("tyreService", tyreService);
    }
}