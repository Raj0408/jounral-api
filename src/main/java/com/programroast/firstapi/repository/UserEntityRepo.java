package com.programroast.firstapi.repository;

import com.programroast.firstapi.entity.UserEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserEntityRepo extends MongoRepository<UserEntity, ObjectId> {
    Optional<UserEntity> findByUsername(String username);
}
