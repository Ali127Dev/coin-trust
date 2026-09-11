package io.github.ali127dev.cointrust.modules.account.application.usecases;

import io.github.ali127dev.cointrust.modules.account.domain.entities.Account;
import io.github.ali127dev.cointrust.modules.account.domain.exceptions.AccountNotFoundException;
import io.github.ali127dev.cointrust.modules.account.domain.ports.AccountRepository;
import io.github.ali127dev.cointrust.modules.account.domain.valueobjects.AccountId;
import io.github.ali127dev.cointrust.modules.account.domain.valueobjects.OwnerId;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ChangeAddressUsecaseTest {
    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private ChangeAddressUsecase changeAddressUsecase;

    @Test
    void shouldChangeAddress() {
        var accountId = AccountId.generate();
        var ownerId = OwnerId.fromUuid(UUID.randomUUID());
        var account = new Account(accountId, ownerId);

        when(accountRepository.findByIdAndOwnerId(any(AccountId.class), any(OwnerId.class)))
                .thenReturn(Optional.of(account));

        var input = new ChangeAddressUsecase.ChangeAddressInput(
                accountId.toString(),
                ownerId.getValue().toString(),
                "a".repeat(30)
        );
        changeAddressUsecase.execute(input);

        Assertions.assertThat(account.getAddress().value()).isEqualTo("a".repeat(30));
        verify(accountRepository).save(account);
    }

    @Test
    void shouldThrowWhenAccountDoesNotExistOrOwnerMismatch() {
        when(accountRepository.findByIdAndOwnerId(any(AccountId.class), any(OwnerId.class)))
                .thenReturn(Optional.empty());

        var input = new ChangeAddressUsecase.ChangeAddressInput(
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString(),
                "a".repeat(30)
        );

        assertThatThrownBy(() -> changeAddressUsecase.execute(input))
                .isInstanceOf(AccountNotFoundException.class);

        verify(accountRepository, never()).save(any());
    }
}