package io.github.ali127dev.cointrust.shared.infrastructure.models;

import io.github.ali127dev.cointrust.shared.domain.enums.IdempotencyStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "idempotency_keys")
@Getter
@Setter
@NoArgsConstructor
public class IdempotencyKeyModel {
    @Id
    private String key;

    @Enumerated(EnumType.STRING)
    private IdempotencyStatus status;

    private Integer httpStatus;

    private Instant createdAt;

    private Instant completedAt;

    public IdempotencyKeyModel(String key) {
        this.key = key;
        this.status = IdempotencyStatus.IN_PROGRESS;
        this.createdAt = Instant.now();
    }
}