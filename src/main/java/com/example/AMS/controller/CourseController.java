package com.example.AMS.controller;

import com.example.AMS.service.CourseService;
import com.example.AMS.exception.DataDuplicateException;
import com.example.AMS.exception.NoSuchDataException;
import com.example.AMS.model.Course;  // Import the Course model
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
    public ResponseEntity<String> addCourses(@RequestBody Course course) {
        try {
            courseService.addCourse(course.getCourseId(), course.getCourseName());
            return ResponseEntity.status(HttpStatus.CREATED).body("Course added successfully");
        } catch (DataDuplicateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @GetMapping("/ams/courses")
    public ResponseEntity<List<Course>> getAllCourses() {
        List<Course> courses = courseService.getAllCourses();
        return ResponseEntity.ok(courses);
    }

    @DeleteMapping("/ams/courses/{courseId}")
    public ResponseEntity<String> deleteCourse(@PathVariable String courseId) {
        try {
            courseService.deleteCourse(courseId);
            return ResponseEntity.ok("Course Deleted Successfully");
        } catch (NoSuchDataException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("ams/courses/{courseId}")
    public ResponseEntity<String> updateCourse(@PathVariable String courseId, @RequestBody Course course) {
        try {
            courseService.updateCourse(courseId, course.getCourseId(), course.getCourseName());
            return ResponseEntity.ok("Course Updated Successfully!");
        } catch (NoSuchDataException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
