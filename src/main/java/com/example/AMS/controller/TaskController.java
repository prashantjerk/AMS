package com.example.AMS.controller;

import com.example.AMS.model.Task;
import com.example.AMS.service.TaskService;
import com.example.AMS.exception.NoSuchDataException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    // Create a task
    @PostMapping("/ams/courses/{courseId}/tasks")
    public ResponseEntity<String> addTask(@RequestBody Task task, @PathVariable String courseId) {
        try {
            taskService.addTask(courseId, task);
            return ResponseEntity.status(HttpStatus.CREATED).body("Task added successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(e.getMessage());
        }
    }

    // Get all tasks for a specific course
    @GetMapping("/ams/courses/{courseId}/tasks")
    public ResponseEntity<List<Task>> getAllTasks(@PathVariable String courseId) {
        try {
            List<Task> allTasks = taskService.getAllTasks(courseId);
            return ResponseEntity.ok(allTasks);
        } catch (NoSuchDataException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    // Get a task by its ID
    @GetMapping("/ams/courses/{courseId}/tasks/{taskId}")
    public ResponseEntity<Task> getTask(@PathVariable String taskId) {
        try {
            Task task = taskService.getTask(taskId);
            return ResponseEntity.ok(task);
        } catch (NoSuchDataException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Delete a task
    @DeleteMapping("/ams/courses/{courseId}/tasks/{taskId}")
    public ResponseEntity<String> deleteTask(@PathVariable String taskId) {
        try {
            taskService.deleteTask(taskId);
            return ResponseEntity.status(HttpStatus.OK).body("Task deleted successfully");
        } catch (NoSuchDataException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Update a task
    @PutMapping("/ams/courses/{courseId}/tasks/{taskId}")
    public ResponseEntity<String> updateTask(@RequestBody Task updatedTask, @PathVariable String courseId, @PathVariable String taskId) {
        try {
            taskService.updateTask(taskId, courseId, updatedTask);
            return ResponseEntity.status(HttpStatus.OK).body("Task updated successfully.");
        } catch (NoSuchDataException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
