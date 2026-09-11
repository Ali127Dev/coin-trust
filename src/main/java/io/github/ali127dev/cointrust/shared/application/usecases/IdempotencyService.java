package io.github.ali127dev.cointrust.shared.application.usecases;

import io.github.ali127dev.cointrust.shared.domain.enums.IdempotencyStatus;
import io.github.ali127dev.cointrust.shared.domain.exceptions.IdempotencyException;
import io.github.ali127dev.cointrust.shared.infrastructure.internals.IdempotencyKeyJpaRepository;
import io.github.ali127dev.cointrust.shared.infrastructure.models.IdempotencyKeyModel;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
public class IdempotencyService {
    private final IdempotencyKeyJpaRepository repository;
    private final IdempotencyService self;

    public IdempotencyService(
            IdempotencyKeyJpaRepository repository,
            @Lazy IdempotencyService self
    ) {
        this.repository = repository;
        this.self = self;
    }

    public ClaimResult tryClaim(String key, String payloadHash) {
        try {
            self.insertNew(key, payloadHash);
            return ClaimResult.claimed();
        } catch (DataIntegrityViolationException | JpaSystemException e) {
            return self.lookupExisting(key, payloadHash, e);
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void insertNew(String key, String payloadHash) {
        repository.saveAndFlush(new IdempotencyKeyModel(key, payloadHash));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public ClaimResult lookupExisting(String key, String payloadHash, DataAccessException e) {
        var existing = repository.findById(key).orElseThrow(() -> e);

        if (!existing.getPayloadHash().equals(payloadHash)) {
            throw new IdempotencyException(
                    "Idempotency-Key '" + key + "' was already used with a different request body"
            );
        }

        if (existing.getStatus() == IdempotencyStatus.COMPLETED) {
            return ClaimResult.alreadyCompleted(existing.getHttpStatus(), existing.getResponseBody());
        }
        return ClaimResult.inProgress();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void complete(String key, int httpStatus, String responseBody) {
        repository.findById(key).ifPresent(entry -> {
            entry.setStatus(IdempotencyStatus.COMPLETED);
            entry.setHttpStatus(httpStatus);
            entry.setResponseBody(responseBody);
            entry.setCompletedAt(Instant.now());
            repository.save(entry);
        });
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void release(String key) {
        repository.deleteById(key);
    }

    public record ClaimResult(Outcome outcome, Integer httpStatus, String responseBody) {
        public enum Outcome {CLAIMED, IN_PROGRESS, ALREADY_COMPLETED}

        public static ClaimResult claimed() {
            return new ClaimResult(Outcome.CLAIMED, null, null);
        }

        public static ClaimResult inProgress() {
            return new ClaimResult(Outcome.IN_PROGRESS, null, null);
        }

        public static ClaimResult alreadyCompleted(int httpStatus, String responseBody) {
            return new ClaimResult(Outcome.ALREADY_COMPLETED, httpStatus, responseBody);
        }
    }
}