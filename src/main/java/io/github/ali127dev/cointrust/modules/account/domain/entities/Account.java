package io.github.ali127dev.cointrust.modules.account.domain.entities;

import io.github.ali127dev.cointrust.modules.account.domain.valueobjects.AccountId;
import io.github.ali127dev.cointrust.modules.account.domain.valueobjects.OwnerId;

import java.time.Instant;

public class Account {
    private final AccountId id;
    private final OwnerId ownerId;
    private String address;
    private long balance;
    private final Instant createdAt;
    private Instant updatedAt;

    public Account(AccountId id, OwnerId owner_id) {
        var now = Instant.now();

        this.id = id;
        this.ownerId = owner_id;
        this.address = null;
        this.balance = 0;
        this.createdAt = now;
        this.updatedAt = now;
    }
}
