package io.github.ali127dev.cointrust.modules.account.application.usecases;

import io.github.ali127dev.cointrust.modules.account.domain.contracts.AccountRepository;
import io.github.ali127dev.cointrust.modules.account.domain.entities.Account;
import io.github.ali127dev.cointrust.modules.account.domain.exceptions.AccountNotFoundException;
import io.github.ali127dev.cointrust.modules.account.domain.valueobjects.AccountId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WithdrawUsecase {
    private final AccountRepository accountRepository;

    @Transactional
    public void execute(WithdrawInput input) {
        AccountId accountId = AccountId.fromString(input.accountId());

        Account account = accountRepository.findById(accountId)
                .orElseThrow(AccountNotFoundException::new);

        account.withdraw(input.amount());

        accountRepository.save(account);
    }

    public record WithdrawInput(String accountId, long amount) {
    }
}
