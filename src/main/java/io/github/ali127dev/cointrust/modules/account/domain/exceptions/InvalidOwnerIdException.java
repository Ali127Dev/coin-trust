package io.github.ali127dev.cointrust.modules.account.domain.exceptions;


public class InvalidOwnerIdException extends RuntimeException {
    public InvalidOwnerIdException(String uuid) {
        super("invalid owner id: " + uuid);
    }
}
