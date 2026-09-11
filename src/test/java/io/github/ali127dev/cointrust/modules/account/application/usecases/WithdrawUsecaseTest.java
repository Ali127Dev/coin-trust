package io.github.ali127dev.cointrust.modules.account.application.usecases;

import io.github.ali127dev.cointrust.modules.account.domain.entities.Account;
import io.github.ali127dev.cointrust.modules.account.domain.exceptions.AccountNotFoundException;
import io.github.ali127dev.cointrust.modules.account.domain.ports.AccountRepository;
import io.github.ali127dev.cointrust.modules.account.domain.valueobjects.AccountId;
import io.github.ali127dev.cointrust.modules.account.domain.valueobjects.OwnerId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class WithdrawUsecaseTest {
    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private WithdrawUsecase withdrawUsecase;

    @Test
    void shouldWithdrawFromExistingAccount() {
        var accountId = AccountId.generate();
        var ownerId = OwnerId.fromUuid(UUID.randomUUID());
        var account = new Account(accountId, ownerId);
        account.deposit(100L);

        when(accountRepository.findAccountForUpdateByIdAndOwnerId(any(AccountId.class), any(OwnerId.class)))
                .thenReturn(Optional.of(account));

        var input = new WithdrawUsecase.WithdrawInput(
                accountId.toString(),
                ownerId.getValue().toString(),
                50L
        );
        withdrawUsecase.execute(input);

        assertThat(account.getBalance().value()).isEqualTo(50L);
        verify(accountRepository).save(account);
    }

    @Test
    void shouldThrowWhenAccountDoesNotExistOrOwnerMismatch() {
        when(accountRepository.findAccountForUpdateByIdAndOwnerId(any(AccountId.class), any(OwnerId.class)))
                .thenReturn(Optional.empty());

        var input = new WithdrawUsecase.WithdrawInput(
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString(),
                100L
        );

        assertThatThrownBy(() -> withdrawUsecase.execute(input))
                .isInstanceOf(AccountNotFoundException.class);

        verify(accountRepository, never()).save(any());
    }
}