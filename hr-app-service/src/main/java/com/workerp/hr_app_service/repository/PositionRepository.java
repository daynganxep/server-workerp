package com.workerp.hr_app_service.repository;

import com.workerp.hr_app_service.model.Position;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PositionRepository extends MongoRepository<Position, String> {
    List<Position> findByCompanyId(String companyId);
}
