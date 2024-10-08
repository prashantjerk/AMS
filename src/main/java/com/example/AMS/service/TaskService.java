package com.example.AMS.service;

import com.example.AMS.Priority;
import com.example.AMS.model.Task;
import com.example.AMS.exception.NoSuchDataException;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisPooled;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TaskService {
    private final JedisPooled jedisPooled;

    public TaskService(JedisPooled jedisPooled) {
        this.jedisPooled = jedisPooled;
    }

    // this will add task - CREATE
    public void addTask(Long taskId, String courseId, String taskTitle, String description, LocalDate dueDate, Priority priority, boolean completed) {
        Map<String, String> taskDetail = new HashMap<>();

        taskDetail.put("courseId", courseId);
        taskDetail.put("taskTitle", taskTitle);
        taskDetail.put("description", description);
        taskDetail.put("dueDate", String.valueOf(dueDate));
        taskDetail.put("priority", String.valueOf(priority));
        taskDetail.put("completed", String.valueOf(completed));

        jedisPooled.hset(String.valueOf(taskId), taskDetail);
    }

    public List<Task> getAllTasks(String courseId) {
        List<Task> allTasks = new ArrayList<>();

        for(String taskId: jedisPooled.keys("*")) {
            Map<String, String> task = jedisPooled.hgetAll(taskId); // this will fetch the task based on the taskId

            if(task.get("courseId").equals(courseId)) {
                Task eachTask = new Task(
                        task.get("taskTitle"),
                        task.get("description"),
                        LocalDate.parse(task.get("dueDate")),
                        Priority.valueOf(task.get("priority")),
                        Boolean.parseBoolean(task.get("completed")));
                eachTask.setCourseId(task.get("courseId"));
                allTasks.add(eachTask);
            }
        }
        return allTasks;
    }

    // write the READ code here
    public Task getTask(Long taskId) {
        if(!jedisPooled.exists(String.valueOf(taskId))) {
            throw new NoSuchDataException(String.valueOf(taskId));
        }
        Map<String, String> task = jedisPooled.hgetAll(String.valueOf(taskId));

        Task fetchedTask = new Task(
                task.get("taskTitle"),
                task.get("description"),
                LocalDate.parse(task.get("dueDate")),
                Priority.valueOf(task.get("priority")),
                Boolean.parseBoolean(task.get("completed")));
        fetchedTask.setCourseId(task.get("courseId"));

        return fetchedTask;
    }

    // this will DELETE the task with the given taskId
    public void deleteTask(Long taskId) {
        jedisPooled.del(String.valueOf(taskId));
    }

    // UPDATE the task associated with given taskId
    public void updateTask(Long taskId, String courseId, String taskTitle, String description, LocalDate dueDate, Priority priority, boolean completed) {
        if(!jedisPooled.exists(String.valueOf(taskId))) {
            throw new NoSuchDataException(String.valueOf(taskId));
        }

        Map<String, String> updatedTaskDetail = new HashMap<>();
        updatedTaskDetail.put("courseId", courseId);
        updatedTaskDetail.put("taskTitle", taskTitle);
        updatedTaskDetail.put("description", description);
        updatedTaskDetail.put("dueDate", String.valueOf(dueDate));
        updatedTaskDetail.put("priority", String.valueOf(priority));
        updatedTaskDetail.put("completed", String.valueOf(completed));

        jedisPooled.hset(String.valueOf(taskId), updatedTaskDetail);
    }
}
