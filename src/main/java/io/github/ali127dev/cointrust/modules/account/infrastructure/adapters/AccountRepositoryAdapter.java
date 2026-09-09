package io.github.ali127dev.cointrust.modules.account.infrastructure.adapters;

import io.github.ali127dev.cointrust.modules.account.domain.contracts.AccountRepository;
import io.github.ali127dev.cointrust.modules.account.domain.entities.Account;
import io.github.ali127dev.cointrust.modules.account.domain.valueobjects.AccountId;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class AccountRepositoryAdapter implements AccountRepository {
    @Override
    public Optional<Account> findById(AccountId id) {
        return Optional.empty();
    }

    @Override
    public void save(Account account) {

    }
}
