package com.taskhub.taskmanager.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "task")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Task {
    @Id
    private ObjectId id;
    //it will have parameters like title,description,status,User(using dbref)
    private String title;
    private String description;
    private String status;

    @DBRef(lazy=false)
    private User user;
    public String getId() {
        return id != null ? id.toHexString() : null;
    }

}
