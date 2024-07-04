package com.programroast.firstapi.controller;

import com.programroast.firstapi.entity.UserEntity;
import com.programroast.firstapi.service.UserEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping
    public ResponseEntity<List<UserEntity>> getAll() {
        ArrayList<UserEntity> entries = new ArrayList<>(userEntityService.getall());
        if (!entries.isEmpty()) return ResponseEntity.ok(entries);
        return new ResponseEntity<>(entries, HttpStatus.NO_CONTENT);
    }

    @GetMapping("/id/{MyUseName}")
    public ResponseEntity<UserEntity> getById(@PathVariable String MyUseName){
        Optional<UserEntity> object  = userEntityService.getbyusername(MyUseName);
        return object.map(ResponseEntity::ok).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    @PostMapping
    public ResponseEntity<UserEntity> addUserEntity(@RequestBody UserEntity userEntity) {
        try{
            userEntityService.saveEntry(userEntity);
            return new ResponseEntity<>(userEntity, HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("id/{MyUserName}")
    public ResponseEntity<?> deleteUserEntity(@PathVariable String MyUserName) {

        userEntityService.deleteEntry(MyUserName);
        return new ResponseEntity<>(true,HttpStatus.NO_CONTENT);

    }

    @PutMapping("id/{MyUserName}")
    public ResponseEntity<UserEntity> EditUserEntity(@PathVariable String MyUserName , @RequestBody UserEntity userEntity) {

        String username = userEntity.getUsername();
        String password = userEntity.getPassword();

        Optional<UserEntity> old = userEntityService.getbyusername(MyUserName);
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