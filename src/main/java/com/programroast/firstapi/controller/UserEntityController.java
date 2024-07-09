package com.programroast.firstapi.controller;

import com.programroast.firstapi.entity.UserEntity;
import com.programroast.firstapi.service.UserEntityService;
import org.apache.catalina.security.SecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/User")
public class UserEntityController {

//    private Map<Long, JounralEntity> jounralEntityMap = new HashMap<>();

    @Autowired
    private UserEntityService userEntityService;

//    @GetMapping
//    public ResponseEntity<List<UserEntity>> getAll() {
//        ArrayList<UserEntity> entries = new ArrayList<>(userEntityService.getall());
//        if (!entries.isEmpty()) return ResponseEntity.ok(entries);
//        return new ResponseEntity<>(entries, HttpStatus.NO_CONTENT);
//    }

    @GetMapping()
    public ResponseEntity<UserEntity> getById(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<UserEntity> object  = userEntityService.getbyusername(authentication.getName());
        System.out.println(authentication.getName());

        return object.map(ResponseEntity::ok).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    @DeleteMapping()
    public ResponseEntity<?> deleteUserEntity() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userEntityService.deleteEntry(authentication.getName());
        return new ResponseEntity<>(true,HttpStatus.NO_CONTENT);

    }

    @Transactional
    @PutMapping()
    public ResponseEntity<UserEntity> EditUserEntity(@RequestBody UserEntity userEntity) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = userEntity.getUsername();
        String password = userEntity.getPassword();



        Optional<UserEntity> old = userEntityService.getbyusername(authentication.getName());
        if (old.isPresent()) {
            username = username.isEmpty() ? old.get().getUsername() : username;
            password = password.isEmpty() ? old.get().getPassword() : password;
            old.get().setUsername(username);
            old.get().setPassword(password);
            userEntityService.saveEntry(old.get());
            return new ResponseEntity<>(old.get(), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}