package io.github.ali127dev.cointrust.modules.account.domain.valueobjects;


import io.github.ali127dev.cointrust.modules.account.domain.exceptions.InvalidOwnerIdException;
import io.github.ali127dev.cointrust.shared.domain.valueobjects.Identifier;

import java.util.UUID;

public class OwnerId extends Identifier {
    private OwnerId(UUID value) {
        super(value);
    }

    public static OwnerId fromString(String rawOwnerId) {
        try {
            return new OwnerId(UUID.fromString(rawOwnerId));
        } catch (IllegalArgumentException e) {
            throw new InvalidOwnerIdException(rawOwnerId);
        }
    }

    public static OwnerId fromUuid(UUID uuid) {
        return new OwnerId(uuid);
    }
}
