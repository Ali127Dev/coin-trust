package io.github.ali127dev.cointrust.modules.account.application.usecases;

import io.github.ali127dev.cointrust.modules.account.domain.ports.AccountRepository;
import io.github.ali127dev.cointrust.modules.account.domain.entities.Account;
import io.github.ali127dev.cointrust.modules.account.domain.exceptions.AccountNotFoundException;
import io.github.ali127dev.cointrust.modules.account.domain.valueobjects.AccountId;
import io.github.ali127dev.cointrust.modules.account.domain.valueobjects.Address;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChangeAddressUsecase {
    private final AccountRepository accountRepository;

    @Transactional
    public void execute(ChangeAddressInput input) {
        AccountId accountId = AccountId.fromString(input.accountId());

        Account account = accountRepository.findById(accountId)
                .orElseThrow(AccountNotFoundException::new);

        var newAddress = new Address(input.address());
        account.changeAddress(newAddress);

        accountRepository.save(account);
    }

    public record ChangeAddressInput(String accountId, String address) {
    }
}
