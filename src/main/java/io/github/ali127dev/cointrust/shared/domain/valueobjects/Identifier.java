package io.github.ali127dev.cointrust.shared.domain.valueobjects;

import lombok.Getter;

import java.util.Objects;
import java.util.UUID;

@Getter
public abstract class Identifier {
    private final UUID value;

    protected Identifier(UUID value) {
        this.value = Objects.requireNonNull(value, "id value must not be null");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return value.equals(((Identifier) o).value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
