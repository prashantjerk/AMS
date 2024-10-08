package com.example.AMS.service;

import com.example.AMS.exception.DataDuplicateException;
import com.example.AMS.exception.NoSuchDataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisPooled;

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

    // add new course to the redis
    public void addCourse(String courseId, String courseName) {
        if (jedisPooled.exists(courseId)) {
            throw new DataDuplicateException(courseId);
        } else {
            Map<String, String> courseDetail = new HashMap<>();
            // example: courseName: "Distributed Computing"
            courseDetail.put("courseName", courseName);

            jedisPooled.hset(courseId, courseDetail);
        }
    }

    // get all the courses
    public List<String> getAllCourses() {
        return jedisPooled.keys("*").stream().toList();
    }

    // get all the courseDetails associated with the courseId
    public Map<String, String> getCourse(String courseId) {
        return jedisPooled.hgetAll(courseId);
    }

    // delete the value associated with courseId
    public void deleteCourse(String courseId) {
        jedisPooled.del(courseId);
    }

    public void updateCourse(String courseId, String courseName) {
        if(!jedisPooled.exists(courseId)) {
            throw new NoSuchDataException(courseId);
        } else {
            Map<String, String> courseDetail = new HashMap<>();
            courseDetail.put("courseName", courseName);

            jedisPooled.hset(courseId, courseDetail);
        }
    }
}
