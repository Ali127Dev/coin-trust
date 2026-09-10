package io.github.ali127dev.cointrust.modules.account.application.usecases;

import io.github.ali127dev.cointrust.modules.account.domain.ports.AccountRepository;
import io.github.ali127dev.cointrust.modules.account.domain.entities.Account;
import io.github.ali127dev.cointrust.modules.account.domain.exceptions.AccountNotFoundException;
import io.github.ali127dev.cointrust.modules.account.domain.valueobjects.AccountId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DepositUsecase {
    private final AccountRepository accountRepository;

    @Transactional
    public void execute(DepositInput input) {
        AccountId accountId = AccountId.fromString(input.accountId());

        Account account = accountRepository.findAccountForUpdate(accountId)
                .orElseThrow(AccountNotFoundException::new);

        account.deposit(input.amount());

        accountRepository.save(account);
    }

    public record DepositInput(String accountId, long amount) {
    }
}
