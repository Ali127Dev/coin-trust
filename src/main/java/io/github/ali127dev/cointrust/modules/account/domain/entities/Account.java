package io.github.ali127dev.cointrust.modules.account.domain.entities;

import io.github.ali127dev.cointrust.modules.account.domain.exceptions.InvalidAddressException;
import io.github.ali127dev.cointrust.modules.account.domain.exceptions.InvalidBalanceException;
import io.github.ali127dev.cointrust.modules.account.domain.valueobjects.AccountId;
import io.github.ali127dev.cointrust.modules.account.domain.valueobjects.OwnerId;
import lombok.Getter;

import java.time.Instant;
import java.util.Objects;

@Getter
public class Account {
    private static final int MIN_ADDRESS_LENGTH = 30;
    private static final int MAX_ADDRESS_LENGTH = 200;

    private final AccountId id;
    private final OwnerId ownerId;
    private String address;
    private long balance;
    private final Instant createdAt;
    private Instant updatedAt;

    public Account(AccountId id, OwnerId ownerId, String address, long balance) {
        validateAddress(address);
        validateNonNegative(balance);

        var now = Instant.now();

        this.id = id;
        this.ownerId = ownerId;
        this.address = address;
        this.balance = balance;
        this.createdAt = now;
        this.updatedAt = now;
    }

    public Account(AccountId id, OwnerId ownerId) {
        this(id, ownerId, "", 0);
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
            String address,
            long balance,
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

    public void changeAddress(String value) {
        validateAddress(value);
        address = value;
        touch();
    }

    public void deposit(long value) {
        validateNonNegative(value);
        balance += value;
        touch();
    }

    public void withdraw(long value) {
        validateNonNegative(value);
        long newBalance = balance - value;
        validateNonNegative(newBalance);
        balance = newBalance;
        touch();
    }

    private void touch() {
        updatedAt = Instant.now();
    }

    private void validateAddress(String value) {
        Objects.requireNonNull(value);

        if (value.isEmpty()) return;
        if (value.length() < MIN_ADDRESS_LENGTH)
            throw new InvalidAddressException(MIN_ADDRESS_LENGTH, null);
        if (value.length() > MAX_ADDRESS_LENGTH)
            throw new InvalidAddressException(null, MAX_ADDRESS_LENGTH);
    }

    private void validateNonNegative(long value) {
        if (value < 0) throw new InvalidBalanceException();
    }
}
