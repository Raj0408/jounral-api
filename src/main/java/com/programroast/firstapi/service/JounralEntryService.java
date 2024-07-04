package com.programroast.firstapi.service;

import com.programroast.firstapi.MyClass;
import com.programroast.firstapi.entity.JounralEntity;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.programroast.firstapi.repository.JounralEntryRepo;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class JounralEntryService{

    @Autowired
    private JounralEntryRepo jounralEntryRepo;

    public void saveEntry(JounralEntity jounralentity){
        jounralentity.setCreatedAt(LocalDateTime.now());
        jounralEntryRepo.save(jounralentity);
    }

    public List<JounralEntity> getall(){
         return jounralEntryRepo.findAll();
    }

    public Optional<JounralEntity> getbyid(ObjectId id){
        return jounralEntryRepo.findById(id.toString());

    }


    public boolean deleteEntry(ObjectId MyId ){
        jounralEntryRepo.deleteById(MyId.toString());
        Optional<JounralEntity> objectid = getbyid(MyId);
        return !objectid.isPresent();
    }


}
