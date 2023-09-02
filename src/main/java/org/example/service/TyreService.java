package org.example.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dao.impl.TyreDaoImpl;
import org.example.dao.mapper.TyreMapper;
import org.example.model.dto.TyreDto;
import org.example.model.entity.Tyre;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class TyreService {

    private ObjectMapper objectMapper = new ObjectMapper();
    private TyreDaoImpl tyreDao;
    TyreMapper tyreMapper = new TyreMapper();

    public TyreService(TyreDaoImpl tyreDao) {
        this.tyreDao = tyreDao;
    }

    public TyreService(TyreDaoImpl tyreDao, TyreMapper tyreMapper) {
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
            TyreDto tyreDto = new TyreDto();
            if (tyre != null) {
                tyreDto = tyreMapper.convertToTyreDto(tyre);
            }
            try {
                return Optional.ofNullable(objectMapper.writeValueAsString(tyreDto));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        return Optional.empty();
    }

    public void handlePostRequest(Tyre tyre) throws SQLException {

        if (tyre != null) {
            tyreDao.create(tyre);
        }
    }

    public void handlePutRequest(Tyre tyre) throws SQLException {

        if (tyre != null) {
            tyreDao.update(tyre);
        }
    }

    public void handleDeleteRequest(int tyreId) throws SQLException {

        tyreDao.deleteById(tyreId);
    }
}