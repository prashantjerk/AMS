package exception;

public class DataDuplicateException extends RuntimeException{
    public DataDuplicateException(String courseId) {
        super(courseId + "already in existence.");
    }
}
