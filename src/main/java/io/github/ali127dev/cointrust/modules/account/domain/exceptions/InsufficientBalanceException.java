package io.github.ali127dev.cointrust.modules.account.domain.exceptions;

import io.github.ali127dev.cointrust.shared.domain.exceptions.BusinessRuleViolationException;

public class InsufficientBalanceException extends BusinessRuleViolationException {
    public InsufficientBalanceException() {
        super("Insufficient balance to complete this operation");
    }
}