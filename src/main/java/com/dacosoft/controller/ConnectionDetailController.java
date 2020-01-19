package com.dacosoft.controller;

import com.dacosoft.entity.ConnectionDetail;
import com.dacosoft.service.ConnectionDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class ConnectionDetailController {

    private final ConnectionDetailService service;

    @Autowired
    public ConnectionDetailController(ConnectionDetailService service) {
        this.service = service;
    }

    @GetMapping("/connectiondetails")
    public List<ConnectionDetail> getAllConnectionDetails() {
         return service.getConnectionDetails();
    }

    @GetMapping("/connectiondetails/{id}")
    public Optional<ConnectionDetail> getConnectionDetail(@PathVariable Integer id) {
        return service.getConnectionDetail(id);
    }

}
