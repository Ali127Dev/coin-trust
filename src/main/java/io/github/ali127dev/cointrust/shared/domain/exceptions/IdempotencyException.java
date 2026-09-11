package io.github.ali127dev.cointrust.shared.domain.exceptions;

public class IdempotencyException extends RuntimeException {
    public IdempotencyException(String message) {
        super(message);
    }
}