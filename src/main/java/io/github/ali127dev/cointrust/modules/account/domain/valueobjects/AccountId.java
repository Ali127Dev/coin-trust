package io.github.ali127dev.cointrust.modules.account.domain.valueobjects;

import io.github.ali127dev.cointrust.shared.domain.valueobjects.Identifier;

import java.util.UUID;

public final class AccountId extends Identifier {
    private AccountId(UUID value) {
        super(value);
    }

    public static AccountId generate() {
        return new AccountId(UUID.randomUUID());
    }

    public static AccountId fromUuid(UUID uuid) {
        return new AccountId(uuid);
    }
}