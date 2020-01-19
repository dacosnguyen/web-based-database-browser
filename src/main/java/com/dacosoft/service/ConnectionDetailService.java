package com.dacosoft.service;

import com.dacosoft.entity.ConnectionDetail;
import com.dacosoft.repository.ConnectionDetailRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ConnectionDetailService {

    private final ConnectionDetailRepository connectionDetailRepository;

    @Autowired
    public ConnectionDetailService(ConnectionDetailRepository connectionDetailRepository) {
        this.connectionDetailRepository = connectionDetailRepository;
    }

    public List<ConnectionDetail> getConnectionDetails() {
        List<ConnectionDetail> connectionDetails = new ArrayList<>();
        connectionDetailRepository.findAll().forEach(connectionDetails::add);
        return connectionDetails;
    }

    public ConnectionDetail getConnectionDetail(int id) throws NotFoundException {
        return connectionDetailRepository.findById(id)
                .orElseThrow(() ->  new NotFoundException(String.format("Connection detail with ID %d was not found!", id)));
    }

    public void saveConnectionDetail(ConnectionDetail connectionDetail) {
        connectionDetailRepository.save(connectionDetail);
    }

    public void updateConnectionDetail(ConnectionDetail connectionDetail) {
        connectionDetailRepository.save(connectionDetail);
    }

    public void deleteConnectionDetail(int id) {
        connectionDetailRepository.deleteById(id);
    }

}
