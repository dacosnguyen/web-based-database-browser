package com.dacosoft.service;

import javassist.NotFoundException;
import org.jooq.example.db.h2.tables.pojos.ConnectionDetail;
import org.jooq.example.db.h2.tables.records.ConnectionDetailRecord;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * This service offers all operations with {@link ConnectionDetail} via JOOQ.
 */
public interface IConnectionDetailServiceJooq {

    @Transactional
    List<ConnectionDetailRecord> getConnectionDetails();

    @Transactional
    ConnectionDetailRecord getConnectionDetail(int id) throws NotFoundException;

    @Transactional
    void saveConnectionDetail(ConnectionDetail connectionDetail);

    @Transactional
    void updateConnectionDetail(ConnectionDetail connectionDetail);

    @Transactional
    void deleteConnectionDetail(int id);
}
