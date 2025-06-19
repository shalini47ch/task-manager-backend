package com.taskhub.taskmanager.controller;

import com.taskhub.taskmanager.model.Task;
import com.taskhub.taskmanager.model.User;
import com.taskhub.taskmanager.repository.TaskRepository;
import com.taskhub.taskmanager.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.nio.file.AccessDeniedException;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/tasks")

public class TaskController {
    //here we will perform all the crud operations for the tasks and will use a Principal interface(the user that is authenticated using spring security with jwt token is here)
    @Autowired
    TaskRepository taskRepository;

    @Autowired
    UserRepository userRepository;

    //now the next step is to get all the users for the tasks
    @GetMapping
    public List<Task> getTasks(Principal principal) {
        //lets find the user
        User user = userRepository.findByUserName(principal.getName()).get();
        //we found the user that is authenticated now we will add it in the taskrepo
        return taskRepository.findByUser(user);
    }

    //now the next step is to create a task
    @PostMapping
    public Task createTask(Principal principal, @RequestBody Task task) {
        User user = userRepository.findByUserName(principal.getName()).get();
        task.setUser(user);
        task.setStatus("TODO");
        return taskRepository.save(task);


    }


    @PutMapping("/{id}")
    public Task updateTask(@PathVariable String id, @RequestBody Task updatedTask, Principal principal) {

        if (id == null || id.length() != 24) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid ObjectId: must be 24-char hex string");
        }
        ObjectId objectId = new ObjectId(id);

        Task task = taskRepository.findById(objectId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found with id " + id));


        ObjectId userId = task.getUser().getId(); // This should already be ObjectId
        User taskUser = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "User not found for task"));

        if (!taskUser.getUserName().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You cannot edit this task");
        }

        // ✅ Update fields
        task.setTitle(updatedTask.getTitle());
        task.setDescription(updatedTask.getDescription());
        task.setStatus(updatedTask.getStatus());

        return taskRepository.save(task);
    }


    //similarly do for deleteMapping
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable String id, Principal principal) {

        ObjectId objectId = new ObjectId(id);

        Task task = taskRepository.findById(objectId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found with id " + id));


        ObjectId userId = task.getUser().getId();
        User taskUser = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "User not found for task"));


        if (!taskUser.getUserName().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You cannot delete this task");
        }


        taskRepository.delete(task);


        return ResponseEntity.ok().body("Task deleted successfully");


    }
}
