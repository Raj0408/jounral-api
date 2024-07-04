package com.programroast.firstapi.entity;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import java.lang.annotation.Documented;
import java.time.LocalDateTime;

@Document
@Data
@NoArgsConstructor
public class JounralEntity {

    @Id
    private ObjectId id;
    private String name;
    private String content;
    private LocalDateTime createdAt;
}
