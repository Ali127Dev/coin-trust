package io.github.ali127dev.cointrust.shared.infrastructure.models;

import io.github.ali127dev.cointrust.shared.domain.enums.IdempotencyStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Persistable;

import java.time.Instant;

@Entity
@Table(name = "idempotency_keys")
@Getter
@Setter
@NoArgsConstructor
public class IdempotencyKeyModel implements Persistable<String> {
    @Id
    private String key;

    @Enumerated(EnumType.STRING)
    private IdempotencyStatus status;

    private Integer httpStatus;

    private Instant createdAt;

    private Instant completedAt;

    @Column(nullable = false)
    private String payloadHash;

    @Column(columnDefinition = "TEXT")
    private String responseBody;

    @Transient
    private boolean isNew = true;

    public IdempotencyKeyModel(String key, String payloadHash) {
        this.key = key;
        this.payloadHash = payloadHash;
        this.status = IdempotencyStatus.IN_PROGRESS;
        this.createdAt = Instant.now();
    }

    @Override
    public String getId() {
        return key;
    }

    @Override
    public boolean isNew() {
        return isNew;
    }

    @PostLoad
    @PostPersist
    void markNotNew() {
        this.isNew = false;
    }
}