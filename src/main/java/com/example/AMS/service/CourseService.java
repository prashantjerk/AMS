package com.example.AMS.service;

import com.example.AMS.exception.DataDuplicateException;
import com.example.AMS.exception.IncompleteBodyException;
import com.example.AMS.exception.NoSuchDataException;
import com.example.AMS.model.Course;  // Import the Course model
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisPooled;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CourseService {
    private final JedisPooled jedisPooled;

    @Autowired
    public CourseService(JedisPooled jedisPooled) {
        this.jedisPooled = jedisPooled;
    }

    public void addCourse(String courseId, String courseName) {
        if (jedisPooled.exists("course:"+courseId)) {
            throw new DataDuplicateException(courseId);
        } else {
            Map<String, String> courseDetail = new HashMap<>();
            String courseKey = "course:" + courseId;
            if (courseId != null && !courseId.isEmpty() && courseName != null && !courseName.isEmpty()) {
                courseDetail.put("courseId", courseId);
                courseDetail.put("courseName", courseName);
                jedisPooled.hset(courseKey, courseDetail);
            } else {
                throw new IncompleteBodyException("Incomplete Request Body.");
            }
        }
    }

    // Get all the courses
    public List<Course> getAllCourses() {
        List<Course> allCourses = new ArrayList<>();

        for(String prefixedCourseId: jedisPooled.keys("course:*")) {
            Map<String, String> courseDetail = jedisPooled.hgetAll(prefixedCourseId);

            Course course = new Course(courseDetail.get("courseId"), courseDetail.get("courseName"));
            allCourses.add(course);
        }
        return allCourses;
    }

    // Get all the course details associated with the courseId
    public Map<String, String> getCourse(String courseId) {
        return jedisPooled.hgetAll(courseId);
    }

    // Delete the value associated with courseId
    public void deleteCourse(String courseId) {
        if(!jedisPooled.exists("course:"+courseId)) {
            throw new NoSuchDataException(courseId);
        }
        jedisPooled.del("course:" + courseId);
    }

    // Update course information
    public void updateCourse(String courseId, String newCourseId, String newCourseName) {
        String oldCourseKey = "course:" + courseId;
        String newCourseKey = "course:" + newCourseId;

        if (!jedisPooled.exists("course:" + courseId)) {
            throw new NoSuchDataException(courseId);
        }

        Map<String, String> courseDetail = new HashMap<>();

        if(!newCourseId.equals(courseId)) {
            if(!jedisPooled.exists(newCourseKey)) {
                courseDetail.put("courseId", newCourseId);
                courseDetail.put("courseName", newCourseName);

                jedisPooled.hset(newCourseKey, courseDetail);
                jedisPooled.del(oldCourseKey);
            } else {
                throw new DataDuplicateException(newCourseId);
            }
        } else {
            courseDetail.put("courseName", newCourseName);
            jedisPooled.hset(oldCourseKey, courseDetail);
        }
    }
}
