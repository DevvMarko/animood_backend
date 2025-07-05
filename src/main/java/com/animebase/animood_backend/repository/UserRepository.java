package com.animebase.animood_backend.repository;

import com.animebase.animood_backend.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    // Domyślne zapytania CRUD MongoDB
}