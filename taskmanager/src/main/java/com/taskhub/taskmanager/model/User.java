package com.taskhub.taskmanager.model;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "user")
public class User {
    @Id
    private ObjectId id;
    private String userName;
    private String password;
    private String role;

}
