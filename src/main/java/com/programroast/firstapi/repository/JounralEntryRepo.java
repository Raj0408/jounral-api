package com.programroast.firstapi.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.programroast.firstapi.entity.JounralEntity;

public interface JounralEntryRepo extends  MongoRepository<JounralEntity,String>{

}
