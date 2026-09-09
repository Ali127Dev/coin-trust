package io.github.ali127dev.cointrust.modules.account.domain.contracts;

import io.github.ali127dev.cointrust.modules.account.domain.entities.Account;

public interface AccountRepository {
    void save(Account account);
}
