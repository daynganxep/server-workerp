package com.workerp.hr_app_service.repository;

import com.workerp.hr_app_service.model.Position;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PositionRepository extends MongoRepository<Position, String> {
}
