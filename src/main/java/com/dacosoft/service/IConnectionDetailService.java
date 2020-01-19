package com.dacosoft.service;

import com.dacosoft.entity.ConnectionDetail;
import javassist.NotFoundException;

import java.util.List;

public interface IConnectionDetailService {

    List<ConnectionDetail> getConnectionDetails();

    ConnectionDetail getConnectionDetail(int id) throws NotFoundException;

    void saveConnectionDetail(ConnectionDetail connectionDetail);

    void updateConnectionDetail(ConnectionDetail connectionDetail);

    void deleteConnectionDetail(int id);

}
