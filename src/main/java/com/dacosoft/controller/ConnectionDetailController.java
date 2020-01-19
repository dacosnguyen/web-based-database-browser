package com.dacosoft.controller;

import com.dacosoft.entity.ConnectionDetail;
import com.dacosoft.service.ConnectionDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ConnectionDetailController {

    public static final String CONNECTION_DETAILS_PREFIX = "/connectiondetails";
    private final ConnectionDetailService service;

    @Autowired
    public ConnectionDetailController(ConnectionDetailService service) {
        this.service = service;
    }

    @GetMapping(CONNECTION_DETAILS_PREFIX)
    public List<ConnectionDetail> getAllConnectionDetails() {
         return service.getConnectionDetails();
    }

    @GetMapping(CONNECTION_DETAILS_PREFIX + "/{id}")
    public Optional<ConnectionDetail> getConnectionDetail(@PathVariable int id) {
        return service.getConnectionDetail(id);
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
