package com.programroast.firstapi.controller;

import com.programroast.firstapi.entity.UserEntity;
import com.programroast.firstapi.service.UserEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/Admin")
public class AdminEntityController {

    @Autowired
    private UserEntityService userEntityService;

    @GetMapping("/all-user")
    public ResponseEntity<List<UserEntity>> getAllUser(){
        List<UserEntity> users = userEntityService.getall();
        if (!users.isEmpty()) return ResponseEntity.ok(users);
        return new ResponseEntity<>(users, HttpStatus.NO_CONTENT);
    }

}
