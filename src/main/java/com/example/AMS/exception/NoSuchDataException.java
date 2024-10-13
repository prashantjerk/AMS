package com.example.AMS.exception;

public class NoSuchDataException extends RuntimeException{
    public NoSuchDataException(String courseId) {
        super(courseId + " not found.");
    }
}
