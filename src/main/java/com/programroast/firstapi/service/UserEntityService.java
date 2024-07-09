package com.programroast.firstapi.service;

import com.programroast.firstapi.entity.UserEntity;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import com.programroast.firstapi.repository.UserEntityRepo;

@Component
public class UserEntityService {

    @Autowired
    private UserEntityRepo userentityrepo;

    @Autowired
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public void saveEntry(UserEntity userentity){
        userentityrepo.save(userentity);
    }

    public List<UserEntity> getall(){
        return userentityrepo.findAll();
    }


    public Optional<UserEntity> getbyusername(String username){
        return userentityrepo.findByUsername(username);

    }

    public void deleteEntry(String username ){
        Optional<UserEntity> userentity = userentityrepo.findByUsername(username);
        userentityrepo.deleteById(userentity.get().getId());
    }
}
