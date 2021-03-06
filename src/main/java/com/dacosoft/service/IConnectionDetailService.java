package com.dacosoft.service;

import com.dacosoft.entity.ConnectionDetail;
import javassist.NotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * This service offers all operations with {@link ConnectionDetail}.
 */
public interface IConnectionDetailService {

    @Transactional
    List<ConnectionDetail> getConnectionDetails();

    @Transactional
    ConnectionDetail getConnectionDetail(int id) throws NotFoundException;

    @Transactional
    void saveConnectionDetail(ConnectionDetail connectionDetail);

    @Transactional
    void updateConnectionDetail(ConnectionDetail connectionDetail);

    @Transactional
    void deleteConnectionDetail(int id);

}
