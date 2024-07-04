package com.programroast.firstapi.controller;

import com.programroast.firstapi.entity.JounralEntity;
import com.programroast.firstapi.service.JounralEntryService;
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
    private JounralEntryService jounralEntryService;

    @GetMapping
    public ResponseEntity<List<JounralEntity>> getAll() {
        ArrayList<JounralEntity> entries = new ArrayList<>(jounralEntryService.getall());
        if (!entries.isEmpty()) return ResponseEntity.ok(entries);
        return new ResponseEntity<>(entries, HttpStatus.NO_CONTENT);
    }

    @GetMapping("/id/{MyId}")
    public ResponseEntity<JounralEntity> getById(@PathVariable ObjectId MyId){
         Optional<JounralEntity> object  = jounralEntryService.getbyid(MyId);
        return object.map(ResponseEntity::ok).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    @PostMapping
    public ResponseEntity<JounralEntity> addJounralEntity(@RequestBody JounralEntity jounralEntity) {
        try{
            jounralEntity.setCreatedAt(LocalDateTime.now());
            jounralEntryService.saveEntry(jounralEntity);
            return new ResponseEntity<>(jounralEntity, HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("id/{MyId}")
    public ResponseEntity<?> deleteJounralEntity(@PathVariable ObjectId MyId) {

            jounralEntryService.deleteEntry(MyId);
            return new ResponseEntity<>(true,HttpStatus.NO_CONTENT);

    }

    @PutMapping("id/{MyId}")
    public ResponseEntity<JounralEntity> EditJounralEntity(@PathVariable ObjectId MyId ,@RequestBody JounralEntity jounralEntity) {

        String title = jounralEntity.getName();
        String content = jounralEntity.getContent();

        Optional<JounralEntity> old = jounralEntryService.getbyid(MyId);
        if (old.isPresent()) {
            title = title == null || title.isEmpty() ? old.get().getName() : title;
            content = content == null || content.isEmpty() ? old.get().getContent() : content;
            jounralEntity.setName(title);
            jounralEntity.setContent(content);
            jounralEntryService.saveEntry(jounralEntity);
            return new ResponseEntity<>(jounralEntity, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
