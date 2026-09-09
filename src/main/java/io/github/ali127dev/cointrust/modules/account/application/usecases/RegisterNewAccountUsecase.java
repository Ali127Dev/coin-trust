package io.github.ali127dev.cointrust.modules.account.application.usecases;

import io.github.ali127dev.cointrust.modules.account.domain.contracts.AccountRepository;
import io.github.ali127dev.cointrust.modules.account.domain.entities.Account;
import io.github.ali127dev.cointrust.modules.account.domain.valueobjects.AccountId;
import io.github.ali127dev.cointrust.modules.account.domain.valueobjects.OwnerId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterNewAccountUsecase {
    private final AccountRepository accountRepository;

    public void execute(RegisterNewAccountInput input) {
        AccountId accountId = AccountId.generate();
        OwnerId ownerId = OwnerId.fromString(input.ownerId());
        Account account = new Account(accountId, ownerId);

        accountRepository.save(account);
    }

    public record RegisterNewAccountInput(String ownerId) {
    }
}

