package com.taskhub.taskmanager.repository;

import com.taskhub.taskmanager.model.Task;
import com.taskhub.taskmanager.model.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends MongoRepository<Task, ObjectId> {
    List<Task> findByUser(User user);

}
