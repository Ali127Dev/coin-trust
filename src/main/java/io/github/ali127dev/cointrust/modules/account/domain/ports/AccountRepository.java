package io.github.ali127dev.cointrust.modules.account.domain.ports;

import io.github.ali127dev.cointrust.modules.account.domain.entities.Account;
import io.github.ali127dev.cointrust.modules.account.domain.valueobjects.AccountId;
import io.github.ali127dev.cointrust.modules.account.domain.valueobjects.OwnerId;

import java.util.Optional;

public interface AccountRepository {
    Optional<Account> findByIdAndOwnerId(AccountId accountId, OwnerId ownerId);

    Optional<Account> findAccountForUpdateByIdAndOwnerId(AccountId accountId, OwnerId ownerId);

    void save(Account account);
}
