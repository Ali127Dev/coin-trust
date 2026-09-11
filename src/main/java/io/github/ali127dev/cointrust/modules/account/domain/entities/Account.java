package io.github.ali127dev.cointrust.modules.account.domain.entities;

import io.github.ali127dev.cointrust.modules.account.domain.valueobjects.AccountId;
import io.github.ali127dev.cointrust.modules.account.domain.valueobjects.Address;
import io.github.ali127dev.cointrust.modules.account.domain.valueobjects.Balance;
import io.github.ali127dev.cointrust.modules.account.domain.valueobjects.OwnerId;
import lombok.Getter;

import java.time.Instant;

@Getter
public class Account {
    private final AccountId id;
    private final OwnerId ownerId;
    private Address address;
    private Balance balance;
    private final Instant createdAt;
    private Instant updatedAt;

    public Account(AccountId id, OwnerId ownerId, Address address, Balance balance) {
        var now = Instant.now();

        this.id = id;
        this.ownerId = ownerId;
        this.address = address;
        this.balance = balance;
        this.createdAt = now;
        this.updatedAt = now;
    }

    public Account(AccountId id, OwnerId ownerId) {
        this(id, ownerId, Address.empty(), Balance.zero());
    }

    /**
     * Persistence-only constructor.
     *
     * <p>This constructor is intended exclusively for database loading
     * by Spring Data. It must not be used for creating new Account instances.</p>
     */
    public Account(
            AccountId id,
            OwnerId ownerId,
            Address address,
            Balance balance,
            Instant createdAt,
            Instant updatedAt
    ) {
        this.id = id;
        this.ownerId = ownerId;
        this.address = address;
        this.balance = balance;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public void changeAddress(Address value) {
        address = value;
        touch();
    }

    public void deposit(long amount) {
        balance = balance.add(amount);
        touch();
    }

    public void withdraw(long amount) {
        balance = balance.subtract(amount);
        touch();
    }

    private void touch() {
        updatedAt = Instant.now();
    }
}
