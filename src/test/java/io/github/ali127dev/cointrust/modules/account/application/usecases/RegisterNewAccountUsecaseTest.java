package io.github.ali127dev.cointrust.modules.account.application.usecases;

import io.github.ali127dev.cointrust.modules.account.domain.entities.Account;
import io.github.ali127dev.cointrust.modules.account.domain.ports.AccountRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;


import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class RegisterNewAccountUsecaseTest {
    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private RegisterNewAccountUsecase registerNewAccountUsecase;

    @Test
    void shouldRegisterNewAccountWithGivenOwnerAndZeroBalance() {
        var ownerId = UUID.randomUUID().toString();
        var input = new RegisterNewAccountUsecase.RegisterNewAccountInput(ownerId);
        ArgumentCaptor<Account> accountCaptor = ArgumentCaptor.forClass(Account.class);

        var output = registerNewAccountUsecase.execute(input);

        verify(accountRepository).save(accountCaptor.capture());
        Account capturedAccount = accountCaptor.getValue();

        assertThat(capturedAccount.getOwnerId().getValue()).isEqualTo(UUID.fromString(ownerId));
        assertThat(capturedAccount.getId().getValue()).isEqualTo(output.accountId());
        assertThat(capturedAccount.getBalance().value()).isEqualTo(0L);
    }
}
