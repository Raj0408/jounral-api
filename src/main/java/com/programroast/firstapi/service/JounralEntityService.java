package com.programroast.firstapi.service;

import com.programroast.firstapi.entity.JounralEntity;
import com.programroast.firstapi.entity.UserEntity;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.programroast.firstapi.repository.JounralEntityRepo;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class JounralEntityService {

    @Autowired
    private JounralEntityRepo jounralEntityRepo;

    @Autowired
    private UserEntityService userEntityService;

    @Transactional
    public void saveEntry(JounralEntity jounralentity,String Username){
        jounralentity.setCreatedAt(LocalDateTime.now());
        Optional<UserEntity> user = userEntityService.getbyusername(Username);
        if(user.isPresent()){
            JounralEntity j = jounralEntityRepo.save(jounralentity);
            user.get().getJournrals().add(j);
            userEntityService.saveEntry(user.get());
        }
    }

    public List<JounralEntity> getallofuser(String Username){
         Optional<UserEntity> user = userEntityService.getbyusername(Username);
         return user.get().getJournrals();
    }

    public Optional<JounralEntity> getbyid(ObjectId id){
        return jounralEntityRepo.findById(id);

    }


    public void deleteEntry(ObjectId MyId,String username){
        Optional<UserEntity> user = userEntityService.getbyusername(username);
        if(user.isPresent()){
            JounralEntity j = jounralEntityRepo.findById(MyId).get();
            user.get().getJournrals().remove(j);
            userEntityService.saveEntry(user.get());
        }
        jounralEntityRepo.deleteById(MyId);
    }


}
