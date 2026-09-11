package io.github.ali127dev.cointrust.shared.infrastructure.internals;

import io.github.ali127dev.cointrust.shared.infrastructure.models.IdempotencyKeyModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IdempotencyKeyJpaRepository extends JpaRepository<IdempotencyKeyModel, String> {
}