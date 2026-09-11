package io.github.ali127dev.cointrust.modules.account.domain.exceptions;

import io.github.ali127dev.cointrust.shared.domain.exceptions.NotFoundException;

public class AccountNotFoundException extends NotFoundException {
    public AccountNotFoundException() {
        super("Account not found");
    }
}
