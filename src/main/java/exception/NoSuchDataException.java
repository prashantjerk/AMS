package exception;

public class NoSuchDataException extends RuntimeException{
    public NoSuchDataException(String courseId) {
        super(courseId + "not found.");
    }
}
