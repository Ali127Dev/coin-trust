package io.github.ali127dev.cointrust.modules.account.domain.exceptions;

import io.github.ali127dev.cointrust.shared.domain.exceptions.InvalidDataException;

public class InvalidAccountIdException extends InvalidDataException {
    public InvalidAccountIdException() {
        super("Invalid account ID");
    }
}
