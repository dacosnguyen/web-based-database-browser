package com.dacosoft.service;

import com.dacosoft.repository.ConnectionDetailRepository;
import com.dacosoft.entity.ConnectionDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public Optional<ConnectionDetail> getConnectionDetail(Integer id) {
        return connectionDetailRepository.findById(id);
    }

    public void saveConnectionDetail(ConnectionDetail connectionDetail) {
        connectionDetailRepository.save(connectionDetail);
    }

    public void updateConnectionDetail(ConnectionDetail connectionDetail) {
        connectionDetailRepository.save(connectionDetail);
    }

    public void deleteConnectionDetail(Integer id) {
        connectionDetailRepository.deleteById(id);
    }

}
