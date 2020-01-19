package com.dacosoft.controller;

import com.dacosoft.entity.ConnectionDetail;
import com.dacosoft.service.ConnectionDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ConnectionDetailController {

    public static final String PREFIX = "/connectiondetails";
    private final ConnectionDetailService service;

    @Autowired
    public ConnectionDetailController(ConnectionDetailService service) {
        this.service = service;
    }

    @GetMapping(PREFIX)
    public List<ConnectionDetail> getAllConnectionDetails() {
         return service.getConnectionDetails();
    }

    @GetMapping(PREFIX + "/{id}")
    public Optional<ConnectionDetail> getConnectionDetail(@PathVariable Integer id) {
        return service.getConnectionDetail(id);
    }

    @PostMapping(PREFIX)
    public void saveConnectionDetail(@RequestBody ConnectionDetail connectionDetail) {
        service.saveConnectionDetail(connectionDetail);
    }

    @PutMapping(PREFIX)
    public void updateConnectionDetail(@RequestBody ConnectionDetail connectionDetail) {
        service.updateConnectionDetail(connectionDetail);
    }

    @DeleteMapping(PREFIX + "/{id}")
    public void deleteConnectionDetail(@PathVariable Integer id) {
        service.deleteConnectionDetail(id);
    }

}
