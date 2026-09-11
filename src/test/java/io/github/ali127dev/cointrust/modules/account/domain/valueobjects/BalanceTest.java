package io.github.ali127dev.cointrust.modules.account.domain.valueobjects;

import io.github.ali127dev.cointrust.modules.account.domain.exceptions.InsufficientBalanceException;
import io.github.ali127dev.cointrust.modules.account.domain.exceptions.InvalidBalanceException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class BalanceTest {
    @Test
    void shouldCreateBalanceWithNonNegativeValue() {
        var balance = new Balance(100);

        assertThat(balance.value()).isEqualTo(100);
    }

    @Test
    void shouldThrowWhenCreatingWithNegativeValue() {
        assertThatThrownBy(() -> new Balance(-1))
                .isInstanceOf(InvalidBalanceException.class);
    }

    @Test
    void shouldAddAmountCorrectly() {
        var balance = new Balance(100);

        var result = balance.add(50);

        assertThat(result.value()).isEqualTo(150);
    }

    @Test
    void shouldNotMutateOriginalBalanceOnAdd() {
        var balance = new Balance(100);

        balance.add(50);

        assertThat(balance.value()).isEqualTo(100);
    }

    @Test
    void shouldSubtractAmountCorrectly() {
        var balance = new Balance(100);

        var result = balance.subtract(30);

        assertThat(result.value()).isEqualTo(70);
    }

    @Test
    void shouldThrowInsufficientBalanceWhenSubtractingMoreThanAvailable() {
        var balance = new Balance(50);

        assertThatThrownBy(() -> balance.subtract(100))
                .isInstanceOf(InsufficientBalanceException.class);
    }

    @Test
    void shouldThrowWhenAddingNegativeAmount() {
        var balance = new Balance(100);

        assertThatThrownBy(() -> balance.add(-10))
                .isInstanceOf(InvalidBalanceException.class);
    }
}
