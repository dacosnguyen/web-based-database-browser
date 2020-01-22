package com.dacosoft.service;

import com.dacosoft.entity.DBColumn;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * This service connects to any database (locally or remotely)
 * where the connection is defined by connection details stored in our embedded database.
 * This service provides operations like listing schemas, tables etc. on the connected database.
 */
public interface IDatabaseBrowserService {

    @Transactional
    List<String> getAllSchemas(int connectionDetailId) throws Exception;

    @Transactional
    List<String> getAllTables(int connectionDetailId) throws Exception;

    @Transactional
    List<DBColumn> getAllColumns(int connectionDetailId, String tableName) throws Exception;

    /**
     * Returns a list of maps where the key is a column name and the value is a database row field's value on the column.
     */
    @Transactional
    List<Map<String, Object>> getTableRows(int connectionDetailId, String tableName, int maxRows) throws Exception;

}
