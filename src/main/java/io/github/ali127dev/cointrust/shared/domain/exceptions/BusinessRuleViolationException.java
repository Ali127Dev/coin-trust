package io.github.ali127dev.cointrust.shared.domain.exceptions;

public abstract class BusinessRuleViolationException extends RuntimeException {
    public BusinessRuleViolationException(String message) {
        super(message);
    }
}