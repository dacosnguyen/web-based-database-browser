package com.dacosoft.controller;

import com.dacosoft.entity.DBColumn;
import com.dacosoft.service.DatabaseBrowserService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@RestController
public class DatabaseBrowserController {

    private final DatabaseBrowserService databaseBrowserService;

    @Autowired
    public DatabaseBrowserController(DatabaseBrowserService databaseBrowserService) {
        this.databaseBrowserService = databaseBrowserService;
    }

    @GetMapping(ConnectionDetailController.CONNECTION_DETAILS_PREFIX + "/{connectionDetailId}/schemas")
    public List<String> getAllSchemas(@PathVariable int connectionDetailId) {
        try {
            return databaseBrowserService.getAllSchemas(connectionDetailId);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @GetMapping(ConnectionDetailController.CONNECTION_DETAILS_PREFIX + "/{connectionDetailId}/tables")
    public List<String> getAllTables(@PathVariable int connectionDetailId) {
        try {
            return databaseBrowserService.getAllTables(connectionDetailId);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @GetMapping(ConnectionDetailController.CONNECTION_DETAILS_PREFIX + "/{connectionDetailId}/tables/{tableName}/columns")
    public List<DBColumn> getAllColumns(@PathVariable int connectionDetailId, @PathVariable String tableName) {
        try {
            return databaseBrowserService.getAllColumns(connectionDetailId, tableName);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    /**
     * Max limit for rows fetching is 20.
     */
    @GetMapping(ConnectionDetailController.CONNECTION_DETAILS_PREFIX + "/{connectionDetailId}/tables/{tableName}/rows")
    public List<Map<String, Object>> getTableRows(@PathVariable int connectionDetailId, @PathVariable String tableName, @RequestParam("limit") int limit) {
        try {
            return databaseBrowserService.getTableRows(connectionDetailId, tableName, limit);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }
}
