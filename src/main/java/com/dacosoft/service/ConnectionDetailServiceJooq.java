package com.dacosoft.service;

import com.dacosoft.repository.ConnectionDetailRepositoryJooq;
import org.jooq.example.db.h2.tables.pojos.ConnectionDetail;
import org.jooq.example.db.h2.tables.records.ConnectionDetailRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConnectionDetailServiceJooq implements IConnectionDetailServiceJooq {

    private final ConnectionDetailRepositoryJooq repositoryJooq;

    @Autowired
    public ConnectionDetailServiceJooq(ConnectionDetailRepositoryJooq repositoryJooq) {
        this.repositoryJooq = repositoryJooq;
    }

    @Override
    public List<ConnectionDetailRecord> getConnectionDetails() {
        return repositoryJooq.findAll();
    }

    @Override
    public ConnectionDetailRecord getConnectionDetail(int id) {
        return repositoryJooq.findById(id);
    }

    @Override
    public void saveConnectionDetail(ConnectionDetail connectionDetail) {
        repositoryJooq.save(connectionDetail);
    }

    @Override
    public void updateConnectionDetail(ConnectionDetail connectionDetail) {
        repositoryJooq.update(connectionDetail);
    }

    @Override
    public void deleteConnectionDetail(int id) {
        repositoryJooq.deleteById(id);
    }

}
