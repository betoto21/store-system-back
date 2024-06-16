package dev.danielfelix.storesystem.libraries.exceptions;

public class TechnicalErrorException extends RuntimeException {
    public TechnicalErrorException(String message) {
        super(message);
    }

    public TechnicalErrorException(String message, Throwable cause) {
        super(message, cause);
    }
}
