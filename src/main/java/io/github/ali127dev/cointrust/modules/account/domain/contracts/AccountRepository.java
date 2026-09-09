package io.github.ali127dev.cointrust.modules.account.domain.contracts;

import io.github.ali127dev.cointrust.modules.account.domain.entities.Account;
import io.github.ali127dev.cointrust.modules.account.domain.valueobjects.AccountId;

import java.util.Optional;

public interface AccountRepository {
    void save(Account account);

    Optional<Account> findById(AccountId id);
}
