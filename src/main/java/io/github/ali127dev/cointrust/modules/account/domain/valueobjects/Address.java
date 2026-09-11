package io.github.ali127dev.cointrust.modules.account.domain.valueobjects;

import io.github.ali127dev.cointrust.modules.account.domain.exceptions.InvalidAddressException;

import java.util.Objects;

public record Address(String value) {
    private static final int MIN_LENGTH = 30;
    private static final int MAX_LENGTH = 200;

    public Address {
        Objects.requireNonNull(value);
        validate(value);
    }

    public static Address empty() {
        return new Address("");
    }

    private static void validate(String value) {
        if (value.isEmpty()) return;
        if (value.length() < MIN_LENGTH)
            throw new InvalidAddressException(MIN_LENGTH, null);
        if (value.length() > MAX_LENGTH)
            throw new InvalidAddressException(null, MAX_LENGTH);
    }
}
