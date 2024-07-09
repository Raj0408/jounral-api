package com.programroast.firstapi.controller;

import com.programroast.firstapi.entity.JounralEntity;
import com.programroast.firstapi.entity.UserEntity;
import com.programroast.firstapi.service.JounralEntityService;
import com.programroast.firstapi.service.UserEntityService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/Jounral")
public class JounralEntityController {

//    private Map<Long, JounralEntity> jounralEntityMap = new HashMap<>();

    @Autowired
    private JounralEntityService jounralEntityService;

    @Autowired
    private UserEntityService userEntityService;

    @GetMapping()
    public ResponseEntity<List<JounralEntity>> getAllofuser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        ArrayList<JounralEntity> entries = new ArrayList<>(jounralEntityService.getallofuser(authentication.getName()));
        if (!entries.isEmpty()) return ResponseEntity.ok(entries);
        return new ResponseEntity<>(entries, HttpStatus.NO_CONTENT);
    }

    @GetMapping("/id/{MyId}")
    public ResponseEntity<JounralEntity> getById(@PathVariable ObjectId MyId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity loginuser = userEntityService.getbyusername(authentication.getName()).get();

        
         Optional<JounralEntity> object  = jounralEntityService.getbyid(MyId);

         if(object.isPresent() && object.get().getOwner().equals(loginuser.getUsername())){
             return object.map(ResponseEntity::ok).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
         }
         else{
             return new ResponseEntity<>(HttpStatus.NOT_FOUND);
         }

    }

    @PostMapping()
    public ResponseEntity<JounralEntity> addJounralEntity(@RequestBody JounralEntity jounralEntity) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        try{
            jounralEntityService.saveEntry(jounralEntity,authentication.getName());
            return new ResponseEntity<>(jounralEntity, HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("id/{MyId}")
    public ResponseEntity<?> deleteJounralEntity(@PathVariable ObjectId MyId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<JounralEntity> joun = jounralEntityService.getbyid(MyId);
        if(authentication.getName().equals(joun.get().getOwner())){
            jounralEntityService.deleteEntry(MyId,authentication.getName());
            return new ResponseEntity<>(true,HttpStatus.NO_CONTENT);
        }
        return  new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

    }

    @PutMapping("update/{MyId}")
    public ResponseEntity<JounralEntity> EditJounralEntity(@PathVariable ObjectId MyId ,@RequestBody JounralEntity jounralEntity) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String title = jounralEntity.getTitle();
        String content = jounralEntity.getContent();

        Optional<JounralEntity> old = jounralEntityService.getbyid(MyId);
        if (old.isPresent() && old.get().getOwner().equals(authentication.getName())) {
            title = title.isEmpty() ? old.get().getTitle() : title;
            content = content == null || content.isEmpty() ? old.get().getContent() : content;
            jounralEntity.setTitle(title);
            jounralEntity.setContent(content);
            jounralEntityService.saveEntry(jounralEntity,authentication.getName());
            return new ResponseEntity<>(jounralEntity, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
