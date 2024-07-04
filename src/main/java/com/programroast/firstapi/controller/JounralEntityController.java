package com.programroast.firstapi.controller;

import com.programroast.firstapi.entity.JounralEntity;
import com.programroast.firstapi.entity.UserEntity;
import com.programroast.firstapi.service.JounralEntityService;
import com.programroast.firstapi.service.UserEntityService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/Jounral")
public class JounralEntityController {

//    private Map<Long, JounralEntity> jounralEntityMap = new HashMap<>();

    @Autowired
    private JounralEntityService jounralEntityService;

    @Autowired
    private UserEntityService userEntityService;

    @GetMapping("{Username}")
    public ResponseEntity<List<JounralEntity>> getAllofuser(@PathVariable String Username){
        ArrayList<JounralEntity> entries = new ArrayList<>(jounralEntityService.getallofuser(Username));
        if (!entries.isEmpty()) return ResponseEntity.ok(entries);
        return new ResponseEntity<>(entries, HttpStatus.NO_CONTENT);
    }

    @GetMapping("/id/{MyId}")
    public ResponseEntity<JounralEntity> getById(@PathVariable ObjectId MyId){
         Optional<JounralEntity> object  = jounralEntityService.getbyid(MyId);
        return object.map(ResponseEntity::ok).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    @PostMapping("/{username}")
    public ResponseEntity<JounralEntity> addJounralEntity(@PathVariable String username,@RequestBody JounralEntity jounralEntity) {
        try{
            jounralEntityService.saveEntry(jounralEntity,username);
            return new ResponseEntity<>(jounralEntity, HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("id/{MyId}/{username}")
    public ResponseEntity<?> deleteJounralEntity(@PathVariable ObjectId MyId ,@PathVariable String username) {

            jounralEntityService.deleteEntry(MyId,username);
            return new ResponseEntity<>(true,HttpStatus.NO_CONTENT);

    }

    @PutMapping("update/{Username}/{MyId}")
    public ResponseEntity<JounralEntity> EditJounralEntity(@PathVariable ObjectId MyId ,@RequestBody JounralEntity jounralEntity,@PathVariable String Username) {

        String title = jounralEntity.getName();
        String content = jounralEntity.getContent();

        Optional<JounralEntity> old = jounralEntityService.getbyid(MyId);
        if (old.isPresent()) {
            title = title.isEmpty() ? old.get().getName() : title;
            content = content == null || content.isEmpty() ? old.get().getContent() : content;
            jounralEntity.setName(title);
            jounralEntity.setContent(content);
            jounralEntityService.saveEntry(jounralEntity,Username);
            return new ResponseEntity<>(jounralEntity, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
