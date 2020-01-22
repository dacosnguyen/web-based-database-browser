package com.dacosoft.controller;

import com.dacosoft.service.IConnectionDetailServiceJooq;
import javassist.NotFoundException;
import org.jooq.example.db.h2.tables.pojos.ConnectionDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ConnectionDetailControllerJooq {

    public static final String CONNECTION_DETAILS_PREFIX = "/jooq/connectiondetails";
    private final IConnectionDetailServiceJooq service;

    @Autowired
    public ConnectionDetailControllerJooq(IConnectionDetailServiceJooq service) {
        this.service = service;
    }

    @GetMapping(CONNECTION_DETAILS_PREFIX)
    public List<String> getAllConnectionDetails() {
        try {
            return service.getConnectionDetails().stream()
                    .map(r -> r.formatJSON())
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @GetMapping(CONNECTION_DETAILS_PREFIX + "/{id}")
    public String getConnectionDetail(@PathVariable int id) {
        try {
            return service.getConnectionDetail(id).formatJSON();
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @PostMapping(CONNECTION_DETAILS_PREFIX)
    public void saveConnectionDetail(@RequestBody ConnectionDetail connectionDetail) {
        try {
            service.saveConnectionDetail(connectionDetail);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @PutMapping(CONNECTION_DETAILS_PREFIX)
    public void updateConnectionDetail(@RequestBody ConnectionDetail connectionDetail) {
        try {
            service.updateConnectionDetail(connectionDetail);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @DeleteMapping(CONNECTION_DETAILS_PREFIX + "/{id}")
    public void deleteConnectionDetail(@PathVariable int id) {
        try {
            service.deleteConnectionDetail(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

}
