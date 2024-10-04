package com.example.AMS.model;

import com.example.AMS.Priority;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDate;
import java.util.Objects;

@Entity
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long courseId;
    private String courseName;
    private String taskTitle;
    private String description;
    private LocalDate dueDate;
    private Priority priority;
    private boolean completed;

    public Task() {

    }

    public Task(String courseName, String taskTitle, String description, LocalDate dueDate, Priority priority, boolean completed) {
        this.courseName = courseName;
        this.taskTitle = taskTitle;
        this.description = description;
        this.dueDate = dueDate;
        this.priority = priority;
        this.completed = completed;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
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
        return Objects.equals(courseName, task.courseName) && Objects.equals(taskTitle, task.taskTitle) && Objects.equals(description, task.description) && Objects.equals(dueDate, task.dueDate) && priority == task.priority && Objects.equals(completed, task.completed);
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseName, taskTitle, description, dueDate, priority, completed);
    }
}
