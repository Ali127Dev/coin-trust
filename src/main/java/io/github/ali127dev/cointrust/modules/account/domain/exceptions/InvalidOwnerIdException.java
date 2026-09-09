package io.github.ali127dev.cointrust.modules.account.domain.exceptions;

import io.github.ali127dev.cointrust.shared.domain.exceptions.InvalidDataException;

public class InvalidOwnerIdException extends InvalidDataException {
    public InvalidOwnerIdException() {
        super("Invalid owner ID");
    }
}
