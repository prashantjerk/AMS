package com.example.AMS.controller;

import com.example.AMS.service.CourseService;
import com.example.AMS.exception.DataDuplicateException;
import com.example.AMS.exception.NoSuchDataException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CourseController {
    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping("/ams/courses")
    public ResponseEntity<String> addCourses(@RequestParam String courseId, @RequestParam String courseName) {
        try {
            courseService.addCourse(courseId, courseName);
            return ResponseEntity.status(HttpStatus.CREATED).body("Course added successfully");
        } catch (DataDuplicateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @GetMapping("/ams/courses")
    public ResponseEntity<List<String>> getAllCourses() {
        List<String> courses = courseService.getAllCourses();
        return ResponseEntity.ok(courses);  // here the courses are returned along with the status
    }

    @PutMapping("ams/courses/{courseId}")
    public ResponseEntity<String> updateCourse(@PathVariable String courseId, @RequestParam String courseName) {
        try {
            courseService.updateCourse(courseId, courseName);
            return ResponseEntity.ok("Course updated successfully!");
        } catch (NoSuchDataException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/ams/courses/{coursesId}")
    public ResponseEntity<String> deleteCourse(@PathVariable String courseId) {
        try {
            courseService.deleteCourse(courseId);
            return ResponseEntity.ok("Course Deleted");
        } catch (NoSuchDataException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
