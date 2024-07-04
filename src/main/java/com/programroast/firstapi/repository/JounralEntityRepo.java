package com.programroast.firstapi.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import com.programroast.firstapi.entity.JounralEntity;

public interface JounralEntityRepo extends  MongoRepository<JounralEntity, ObjectId>{

}
