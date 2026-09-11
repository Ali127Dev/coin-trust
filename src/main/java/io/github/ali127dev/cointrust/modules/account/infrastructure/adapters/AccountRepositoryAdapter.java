package io.github.ali127dev.cointrust.modules.account.infrastructure.adapters;

import io.github.ali127dev.cointrust.modules.account.domain.ports.AccountRepository;
import io.github.ali127dev.cointrust.modules.account.domain.entities.Account;
import io.github.ali127dev.cointrust.modules.account.domain.valueobjects.AccountId;
import io.github.ali127dev.cointrust.modules.account.domain.valueobjects.OwnerId;
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
    public Optional<Account> findByIdAndOwnerId(AccountId id, OwnerId ownerId) {
        return accountJpaRepository
                .findByIdAndOwnerId(id.getValue(), ownerId.getValue())
                .map(accountMapper::toDomain);
    }

    @Override
    public Optional<Account> findAccountForUpdateByIdAndOwnerId(AccountId id, OwnerId ownerId) {
        return accountJpaRepository
                .findAccountForUpdateByIdAndOwnerId(id.getValue(), ownerId.getValue())
                .map(accountMapper::toDomain);
    }

    @Override
    public void save(Account account) {
        var model = accountMapper.toModel(account);
        accountJpaRepository.save(model);
    }
}