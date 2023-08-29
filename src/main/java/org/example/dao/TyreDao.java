package org.example.dao;

import org.example.model.entity.Tyre;

import java.sql.SQLException;
import java.util.List;

public interface TyreDao {

    void create(Tyre tyre) throws SQLException;

    Tyre readById(int id) throws SQLException;

    List<Tyre> readAll() throws SQLException;

    void update(Tyre tyre) throws SQLException;

    void deleteById(int id) throws SQLException;
}
