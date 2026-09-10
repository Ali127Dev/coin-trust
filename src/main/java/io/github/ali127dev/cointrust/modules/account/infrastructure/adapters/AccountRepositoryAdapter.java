package io.github.ali127dev.cointrust.modules.account.infrastructure.adapters;

import io.github.ali127dev.cointrust.modules.account.domain.ports.AccountRepository;
import io.github.ali127dev.cointrust.modules.account.domain.entities.Account;
import io.github.ali127dev.cointrust.modules.account.domain.valueobjects.AccountId;
import io.github.ali127dev.cointrust.modules.account.infrastructure.internals.AccountJpaRepository;
import io.github.ali127dev.cointrust.modules.account.infrastructure.mappers.AccountMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AccountRepositoryAdapter implements AccountRepository {
    private final AccountJpaRepository accountJpaRepository;
    private final AccountMapper accountMapper;

    @Override
    public Optional<Account> findById(AccountId id) {
        return accountJpaRepository
                .findById(id.getValue())
                .map(accountMapper::toDomain);
    }

    @Override
    public Optional<Account> findAccountForUpdate(AccountId id) {
        return accountJpaRepository
                .findAccountForUpdate(id.getValue())
                .map(accountMapper::toDomain);
    }

    @Override
    public void save(Account account) {
        var model = accountMapper.toModel(account);
        accountJpaRepository.save(model);
    }
}
