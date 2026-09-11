package io.github.ali127dev.cointrust.modules.account.domain.exceptions;

import io.github.ali127dev.cointrust.shared.domain.exceptions.InvalidDataException;

public class InvalidBalanceException extends InvalidDataException {
    public InvalidBalanceException() {
        super("Balance must be greater than or equal to 0");
    }
}