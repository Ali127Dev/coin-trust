package io.github.ali127dev.cointrust.modules.account.domain.entities;

import io.github.ali127dev.cointrust.modules.account.domain.exceptions.InsufficientBalanceException;
import io.github.ali127dev.cointrust.modules.account.domain.valueobjects.AccountId;
import io.github.ali127dev.cointrust.modules.account.domain.valueobjects.Address;
import io.github.ali127dev.cointrust.modules.account.domain.valueobjects.OwnerId;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class AccountTest {
    private static OwnerId randomOwnerId() {
        return OwnerId.fromUuid(UUID.randomUUID());
    }

    @Test
    void shouldDepositAndIncreaseBalance() {
        var account = new Account(AccountId.generate(), randomOwnerId());

        account.deposit(100);

        assertThat(account.getBalance().value()).isEqualTo(100);
    }

    @Test
    void shouldUpdateTimestampAfterDeposit() {
        var account = new Account(AccountId.generate(), randomOwnerId());
        var originalUpdatedAt = account.getUpdatedAt();

        account.deposit(100);

        assertThat(account.getUpdatedAt()).isAfterOrEqualTo(originalUpdatedAt);
    }

    @Test
    void shouldThrowWhenWithdrawingMoreThanBalance() {
        var account = new Account(AccountId.generate(), randomOwnerId());
        account.deposit(50);

        assertThatThrownBy(() -> account.withdraw(100))
                .isInstanceOf(InsufficientBalanceException.class);
    }

    @Test
    void shouldChangeAddressSuccessfully() {
        var account = new Account(AccountId.generate(), randomOwnerId());
        var newAddress = new Address("a".repeat(30));

        account.changeAddress(newAddress);

        assertThat(account.getAddress()).isEqualTo(newAddress);
    }
}