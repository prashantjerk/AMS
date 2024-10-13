package com.example.AMS.model;

import com.example.AMS.Priority;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

@Entity
public class Task {

    @Id
    private String taskId;
    private String courseId;
    private String taskTitle;
    private String description;
    private LocalDate dueDate;
    private Priority priority;
    private boolean completed;

    public Task() {

    }

    public Task(String taskId, String taskTitle, String description, LocalDate dueDate, Priority priority, boolean completed) {
        this.taskId = taskId;
        this.taskTitle = taskTitle;
        this.description = description;
        this.dueDate = dueDate;
        this.priority = priority;
        this.completed = completed;
    }

    public String getTaskId() {
        return taskId;
    }

    @PrePersist
    public void generateTaskId() {
        if(this.taskId == null)
            this.taskId = "task" + UUID.randomUUID().toString().substring(0, 4);
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Task task)) return false;
        return Objects.equals(courseId, task.courseId) && Objects.equals(taskTitle, task.taskTitle) && Objects.equals(description, task.description) && Objects.equals(dueDate, task.dueDate) && priority == task.priority && Objects.equals(completed, task.completed);
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseId, taskTitle, description, dueDate, priority, completed);
    }
}
