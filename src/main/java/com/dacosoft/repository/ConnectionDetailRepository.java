package com.dacosoft.repository;

import com.dacosoft.entity.ConnectionDetail;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConnectionDetailRepository extends CrudRepository<ConnectionDetail, Integer> {
}
