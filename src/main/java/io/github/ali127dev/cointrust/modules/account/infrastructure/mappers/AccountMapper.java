package io.github.ali127dev.cointrust.modules.account.infrastructure.mappers;

import io.github.ali127dev.cointrust.modules.account.domain.entities.Account;
import io.github.ali127dev.cointrust.modules.account.domain.valueobjects.AccountId;
import io.github.ali127dev.cointrust.modules.account.domain.valueobjects.OwnerId;
import io.github.ali127dev.cointrust.modules.account.infrastructure.models.AccountModel;
import org.mapstruct.Mapper;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    default Account toDomain(AccountModel model) {
        return new Account(
                mapAccountId(model.getId()),
                mapOwnerId(model.getOwnerId()),
                model.getAddress(),
                model.getBalance(),
                model.getCreatedAt(),
                model.getUpdatedAt()
        );
    }

    AccountModel toModel(Account account);

    default AccountId mapAccountId(UUID value) {
        return AccountId.fromUuid(value);
    }

    default OwnerId mapOwnerId(UUID value) {
        return OwnerId.fromUuid(value);
    }

    default UUID mapAccountId(AccountId value) {
        return value.getValue();
    }

    default UUID mapOwnerId(OwnerId value) {
        return value.getValue();
    }
}
