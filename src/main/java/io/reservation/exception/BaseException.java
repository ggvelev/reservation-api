package io.reservation.exception;

/**
 * Intended to be subclassed by application specific exceptions.
 */
public class BaseException extends RuntimeException {
    public BaseException(String message) {
        super(message);
    }
}

