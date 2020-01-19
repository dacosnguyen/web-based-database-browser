package com.dacosoft.service;

import com.dacosoft.entity.ConnectionDetail;
import com.dacosoft.entity.DBColumn;
import javassist.NotFoundException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public interface IDatabaseBrowserService {

    List<String> getAllSchemas(int connectionDetailId) throws NotFoundException, SQLException, ClassNotFoundException;

    List<String> getAllTables(int connectionDetailId) throws SQLException, NotFoundException, ClassNotFoundException;

    List<DBColumn> getAllColumns(int connectionDetailId, String tableName) throws SQLException, NotFoundException, ClassNotFoundException;

    DBColumn newColumn(ResultSet rs) throws SQLException;

    List<Map<String, Object>> getTableRows(int connectionDetailId, String tableName, int maxRows) throws SQLException, NotFoundException, ClassNotFoundException;

}
