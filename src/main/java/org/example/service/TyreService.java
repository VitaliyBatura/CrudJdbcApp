package org.example.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.example.dao.TyreDao;
import org.example.dao.mapper.TyreMapper;
import org.example.model.dto.TyreDto;
import org.example.model.entity.Tyre;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class TyreService {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private TyreDao tyreDao;
    private TyreMapper tyreMapper;

    public TyreService(TyreDao tyreDao, TyreMapper tyreMapper) {
        this.tyreDao = tyreDao;
        this.tyreMapper = tyreMapper;
    }


    public Optional<String> handleGetRequest(String parameter) throws SQLException {

        if (parameter == null) {
            List<TyreDto> tyres = tyreDao.readAll().stream().map(tyre ->
                    tyreMapper.convertToTyreDto(tyre)).collect(Collectors.toList());
            try {
                return Optional.ofNullable(objectMapper.writeValueAsString(tyres));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        } else {
            int id = Integer.parseInt(parameter);
            Tyre tyre = tyreDao.readById(id);
            TyreDto tyreDto = tyreMapper.convertToTyreDto(tyre);
            try {
                return Optional.ofNullable(objectMapper.writeValueAsString(tyreDto));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        return Optional.empty();
    }

    public void handlePostRequest(HttpServletRequest req) throws SQLException {

        TyreDto tyreDto = getTyreDtoFromRequestBody(req);
        if (tyreDto != null) {
            tyreDao.create(tyreMapper.convertToTyre(tyreDto));
        }
    }

    public void handlePutRequest(HttpServletRequest req) throws SQLException {

        TyreDto tyreDto = getTyreDtoFromRequestBody(req);
        if (tyreDto != null) {
            tyreDao.update(tyreMapper.convertToTyre(tyreDto));
        }
    }

    public void handleDeleteRequest(HttpServletRequest req) throws SQLException {

        int id = Integer.parseInt(req.getParameter("id"));
        tyreDao.deleteById(id);
    }

    private TyreDto getTyreDtoFromRequestBody(HttpServletRequest req) {

        String requestBody = null;
        try {
            requestBody = req.getReader().lines().collect(Collectors.joining());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            return objectMapper.readValue(requestBody, TyreDto.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
}