package xyz.chengzi.ooad.exception;

import xyz.chengzi.ooad.repository.Specification;

public class EntityNotFoundException extends Exception {
    public EntityNotFoundException(int id) {
        super("Could not find entity with id: " + id);
    }

    public EntityNotFoundException(Specification<?> specification) {
        super("Could not find entity with specification: " + specification);
    }
}
