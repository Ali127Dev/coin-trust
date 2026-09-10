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
public class DepositUsecaseTest {
    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private DepositUsecase depositUsecase;

    @Test
    void shouldDepositIntoExistingAccount() {
        var accountId = AccountId.generate();
        var account = new Account(accountId, OwnerId.fromUuid(UUID.randomUUID()));

        when(accountRepository.findAccountForUpdate(any(AccountId.class)))
                .thenReturn(Optional.of(account));

        var input = new DepositUsecase.DepositInput(accountId.getValue().toString(), 100L);
        depositUsecase.execute(input);

        assertThat(account.getBalance().value()).isEqualTo(100L);
        verify(accountRepository).save(account);
    }

    @Test
    void shouldThrowWhenAccountDoesNotExist() {
        when(accountRepository.findAccountForUpdate(any(AccountId.class)))
                .thenReturn(Optional.empty());

        var input = new DepositUsecase.DepositInput(UUID.randomUUID().toString(), 100L);

        assertThatThrownBy(() -> depositUsecase.execute(input))
                .isInstanceOf(AccountNotFoundException.class);

        verify(accountRepository, never()).save(any());
    }
}
