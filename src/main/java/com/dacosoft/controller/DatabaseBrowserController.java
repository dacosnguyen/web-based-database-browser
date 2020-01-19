package com.dacosoft.controller;

import com.dacosoft.service.DatabaseBrowserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DatabaseBrowserController {

    private final DatabaseBrowserService databaseBrowserService;

    @Autowired
    public DatabaseBrowserController(DatabaseBrowserService databaseBrowserService) {
        this.databaseBrowserService = databaseBrowserService;
    }

    @GetMapping(ConnectionDetailController.CONNECTION_DETAILS_PREFIX + "/{id}" + "/schemas")
    public List<String> getAllSchemas(@PathVariable int id) {
        return databaseBrowserService.getAllSchemas(id);
    }

}
