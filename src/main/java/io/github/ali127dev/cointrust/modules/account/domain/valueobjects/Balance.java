package io.github.ali127dev.cointrust.modules.account.domain.valueobjects;

import io.github.ali127dev.cointrust.modules.account.domain.exceptions.InsufficientBalanceException;
import io.github.ali127dev.cointrust.modules.account.domain.exceptions.InvalidBalanceException;

public record Balance(long value) {
    public Balance {
        validateNonNegative(value);
    }

    public static Balance zero() {
        return new Balance(0);
    }

    public Balance add(long amount) {
        validatePositiveAmount(amount);
        return new Balance(this.value + amount);
    }

    public Balance subtract(long amount) {
        validatePositiveAmount(amount);
        long newValue = this.value - amount;
        if (newValue < 0) throw new InsufficientBalanceException();
        return new Balance(newValue);
    }

    private static void validatePositiveAmount(long amount) {
        if (amount < 0) throw new InvalidBalanceException();
    }

    private static void validateNonNegative(long value) {
        if (value < 0) throw new InvalidBalanceException();
    }
}