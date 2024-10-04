package com.example.AMS.model;

import jakarta.persistence.Entity;

import java.util.List;
import java.util.Objects;

@Entity
public class Course {
    private String courseId;
    private String courseName;
    private List<Task> tasks;

    public Course(String courseId, String courseName, List<Task> tasks) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.tasks = tasks;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Course course)) return false;
        return Objects.equals(courseId, course.courseId) && Objects.equals(courseName, course.courseName) && Objects.equals(tasks, course.tasks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseId, courseName, tasks);
    }
}
