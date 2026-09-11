package io.github.ali127dev.cointrust.shared.application.usecases;

import io.github.ali127dev.cointrust.shared.domain.enums.IdempotencyStatus;
import io.github.ali127dev.cointrust.shared.infrastructure.internals.IdempotencyKeyJpaRepository;
import io.github.ali127dev.cointrust.shared.infrastructure.models.IdempotencyKeyModel;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class IdempotencyService {

    private final IdempotencyKeyJpaRepository repository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public ClaimResult tryClaim(String key) {
        try {
            repository.saveAndFlush(new IdempotencyKeyModel(key));
            return ClaimResult.claimed();
        } catch (DataIntegrityViolationException e) {
            var existing = repository.findById(key)
                    .orElseThrow(() -> e);

            if (existing.getStatus() == IdempotencyStatus.COMPLETED) {
                return ClaimResult.alreadyCompleted(existing.getHttpStatus());
            }

            return ClaimResult.inProgress();
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void complete(String key, int httpStatus) {
        repository.findById(key).ifPresent(entry -> {
            entry.setStatus(IdempotencyStatus.COMPLETED);
            entry.setHttpStatus(httpStatus);
            entry.setCompletedAt(Instant.now());
            repository.save(entry);
        });
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void release(String key) {
        repository.deleteById(key);
    }

    public record ClaimResult(Outcome outcome, Integer httpStatus) {

        public enum Outcome {
            CLAIMED,
            IN_PROGRESS,
            ALREADY_COMPLETED
        }

        public static ClaimResult claimed() {
            return new ClaimResult(Outcome.CLAIMED, null);
        }

        public static ClaimResult inProgress() {
            return new ClaimResult(Outcome.IN_PROGRESS, null);
        }

        public static ClaimResult alreadyCompleted(int httpStatus) {
            return new ClaimResult(Outcome.ALREADY_COMPLETED, httpStatus);
        }
    }
}
