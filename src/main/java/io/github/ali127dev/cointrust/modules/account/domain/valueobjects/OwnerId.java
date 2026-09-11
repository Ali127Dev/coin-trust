package io.github.ali127dev.cointrust.modules.account.domain.valueobjects;


import io.github.ali127dev.cointrust.modules.account.domain.exceptions.InvalidOwnerIdException;
import io.github.ali127dev.cointrust.shared.domain.valueobjects.Identifier;

import java.util.UUID;

public final class OwnerId extends Identifier {
    private OwnerId(UUID value) {
        super(value);
    }

    public static OwnerId fromString(String value) {
        try {
            return new OwnerId(UUID.fromString(value));
        } catch (IllegalArgumentException e) {
            throw new InvalidOwnerIdException();
        }
    }

    public static OwnerId fromUuid(UUID uuid) {
        return new OwnerId(uuid);
    }
}
