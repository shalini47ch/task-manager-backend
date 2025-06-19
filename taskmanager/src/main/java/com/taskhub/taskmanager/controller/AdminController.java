package com.taskhub.taskmanager.controller;

import com.taskhub.taskmanager.model.Task;
import com.taskhub.taskmanager.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin")

public class AdminController {
    //to get all the roles this permission is only provided to the admin by the security config
    @Autowired
    TaskRepository taskRepository;

    //here we will get all the tasks of the admin
    @GetMapping("/tasks")
    public List<Task> getAllTasks(){
        return taskRepository.findAll();
    }


}
