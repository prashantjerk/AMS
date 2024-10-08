package com.example.AMS.controller;

import com.example.AMS.Priority;
import com.example.AMS.model.Task;
import com.example.AMS.service.TaskService;
import com.example.AMS.exception.NoSuchDataException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/ams/courses/{courseId}/tasks")
    public ResponseEntity<String> addTask(
            @RequestParam(required = false) Long taskId,
            @PathVariable Long courseId,
            @RequestParam String taskTitle,
            @RequestParam String description,
            @RequestParam LocalDate dueDate,
            @RequestParam Priority priority,
            @RequestParam boolean completed) {
        try {
            taskService.addTask(taskId, String.valueOf(courseId), taskTitle, description, dueDate, priority, completed);
            return ResponseEntity.status(HttpStatus.CREATED).body("Task added successfully");
        }  catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(e.getMessage());
        }
    }

    @GetMapping("/ams/courses/{courseId}/tasks")
    public ResponseEntity<List<Task>> getAllTasks(@PathVariable Long courseId) {
        try {
            List<Task> allTasks = taskService.getAllTasks(String.valueOf(courseId));
            return ResponseEntity.ok(allTasks);
        } catch (NoSuchDataException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("ams/courses/{courseId}/tasks/{taskId}")
    public ResponseEntity<Task> getTask(@PathVariable String courseId, @PathVariable String taskId) {
        try {
            Task task = taskService.getTask(Long.valueOf(taskId));
            return ResponseEntity.ok(task);
        } catch (NoSuchDataException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/ams/courses/{courseId}/tasks/{taskId}")
    public ResponseEntity<String> deleteTask(@PathVariable String courseId, @PathVariable Long taskId) {
        try {
            taskService.deleteTask(taskId);
            return ResponseEntity.status(HttpStatus.OK).body("Task deleted successfully");
        } catch (NoSuchDataException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/ams/courses/{courseId}/tasks/{taskId}")
    public ResponseEntity<String> updateTask(
            @PathVariable Long taskId,
            @PathVariable String courseId,
            @RequestParam String taskTitle,
            @RequestParam String description,
            @RequestParam LocalDate dueDate,
            @RequestParam Priority priority,
            @RequestParam boolean completed) {

        try {
            // Call the service layer to update the task
            taskService.updateTask(taskId, courseId, taskTitle, description, dueDate, priority, completed);
            return ResponseEntity.status(HttpStatus.OK).body("Task updated successfully.");
        } catch (NoSuchDataException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}
