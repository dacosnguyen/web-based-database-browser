package com.dacosoft.controller;

import com.dacosoft.entity.ConnectionDetail;
import com.dacosoft.service.IConnectionDetailService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class ConnectionDetailController {

    public static final String CONNECTION_DETAILS_PREFIX = "/connectiondetails";
    private final IConnectionDetailService service;

    @Autowired
    public ConnectionDetailController(IConnectionDetailService service) {
        this.service = service;
    }

    @GetMapping(CONNECTION_DETAILS_PREFIX)
    public List<ConnectionDetail> getAllConnectionDetails() {
        return service.getConnectionDetails();
    }

    @GetMapping(CONNECTION_DETAILS_PREFIX + "/{id}")
    public ConnectionDetail getConnectionDetail(@PathVariable int id) {
        try {
            return service.getConnectionDetail(id);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @PostMapping(CONNECTION_DETAILS_PREFIX)
    public void saveConnectionDetail(@RequestBody ConnectionDetail connectionDetail) {
        service.saveConnectionDetail(connectionDetail);
    }

    @PutMapping(CONNECTION_DETAILS_PREFIX)
    public void updateConnectionDetail(@RequestBody ConnectionDetail connectionDetail) {
        service.updateConnectionDetail(connectionDetail);
    }

    @DeleteMapping(CONNECTION_DETAILS_PREFIX + "/{id}")
    public void deleteConnectionDetail(@PathVariable int id) {
        service.deleteConnectionDetail(id);
    }

}
