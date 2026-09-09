package io.github.ali127dev.cointrust.modules.account.infrastructure.adapters;

import io.github.ali127dev.cointrust.modules.account.domain.contracts.AccountRepository;
import io.github.ali127dev.cointrust.modules.account.domain.entities.Account;
import org.springframework.stereotype.Repository;

@Repository
public class AccountRepositoryAdapter implements AccountRepository {
    @Override
    public void save(Account account) {

    }
}
