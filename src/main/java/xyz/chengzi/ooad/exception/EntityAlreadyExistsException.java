package xyz.chengzi.ooad.exception;

public class EntityAlreadyExistsException extends RuntimeException {
    public EntityAlreadyExistsException(String message) {
        super("Entity already exists: " + message);
    }

    public EntityAlreadyExistsException(Throwable cause) {
        super(cause);
    }
}
