package com.workerp.company_app_service.repository;

import com.workerp.company_app_service.model.Company;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CompanyRepository extends MongoRepository<Company, String> {
}