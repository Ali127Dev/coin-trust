package io.github.ali127dev.cointrust.modules.account.infrastructure.mappers;

import io.github.ali127dev.cointrust.modules.account.domain.entities.Account;
import io.github.ali127dev.cointrust.modules.account.domain.valueobjects.AccountId;
import io.github.ali127dev.cointrust.modules.account.domain.valueobjects.Address;
import io.github.ali127dev.cointrust.modules.account.domain.valueobjects.Balance;
import io.github.ali127dev.cointrust.modules.account.domain.valueobjects.OwnerId;
import io.github.ali127dev.cointrust.modules.account.infrastructure.models.AccountModel;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;


public class AccountMapperTest {
    private final AccountMapper mapper = Mappers.getMapper(AccountMapper.class);

    private static OwnerId randomOwnerId() {
        return OwnerId.fromUuid(UUID.randomUUID());
    }

    @Test
    void shouldMapModelToDomainCorrectly() {
        var model = new AccountModel();
        model.setId(java.util.UUID.randomUUID());
        model.setOwnerId(java.util.UUID.randomUUID());
        model.setAddress("a".repeat(30));
        model.setBalance(100L);
        model.setCreatedAt(Instant.now());
        model.setUpdatedAt(Instant.now());

        var account = mapper.toDomain(model);

        assertThat(account.getBalance().value()).isEqualTo(100L);
        assertThat(account.getAddress().value()).isEqualTo(model.getAddress());
    }

    @Test
    void shouldMapDomainToModelCorrectly() {
        var account = new Account(
                AccountId.generate(),
                randomOwnerId(),
                new Address("a".repeat(30)),
                new Balance(200)
        );

        var model = mapper.toModel(account);

        assertThat(model.getBalance()).isEqualTo(200L);
        assertThat(model.getAddress()).isEqualTo("a".repeat(30));
    }
}
