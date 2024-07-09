package com.programroast.firstapi.controller;

import com.programroast.firstapi.entity.UserEntity;
import com.programroast.firstapi.service.UserEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public")
public class PublicEntityController {
    @Autowired
    private UserEntityService userEntityService;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PostMapping("create-user")
    public ResponseEntity<UserEntity> addUserEntity(@RequestBody UserEntity userEntity) {
        try{
            userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
            userEntityService.saveEntry(userEntity);
            return new ResponseEntity<>(userEntity, HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
