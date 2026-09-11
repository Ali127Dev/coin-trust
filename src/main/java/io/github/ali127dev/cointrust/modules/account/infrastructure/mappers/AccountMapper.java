package io.github.ali127dev.cointrust.modules.account.infrastructure.mappers;

import io.github.ali127dev.cointrust.modules.account.domain.entities.Account;
import io.github.ali127dev.cointrust.modules.account.domain.valueobjects.AccountId;
import io.github.ali127dev.cointrust.modules.account.domain.valueobjects.Address;
import io.github.ali127dev.cointrust.modules.account.domain.valueobjects.Balance;
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
                mapAddress(model.getAddress()),
                mapBalance(model.getBalance()),
                model.getCreatedAt(),
                model.getUpdatedAt()
        );
    }

    default AccountModel toModel(Account account) {
        var model = new AccountModel();
        model.setId(mapAccountId(account.getId()));
        model.setOwnerId(mapOwnerId(account.getOwnerId()));
        model.setAddress(mapAddress(account.getAddress()));
        model.setBalance(mapBalance(account.getBalance()));
        model.setCreatedAt(account.getCreatedAt());
        model.setUpdatedAt(account.getUpdatedAt());
        return model;
    }

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

    default Address mapAddress(String value) {
        return new Address(value);
    }

    default String mapAddress(Address value) {
        return value.value();
    }

    default Balance mapBalance(long value) {
        return new Balance(value);
    }

    default long mapBalance(Balance value) {
        return value.value();
    }
}