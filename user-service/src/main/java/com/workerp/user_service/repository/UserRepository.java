package com.workerp.user_service.repository;

import com.workerp.user_service.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    boolean existsByEmailAndLocalTrue(String email);
    Optional<User> findByEmailAndLocalTrue(String email);
    Optional<User> findByIdAndLocalTrue(String id);
    Optional<User> findByProviderAndProviderId(String provider, String providerId);
}