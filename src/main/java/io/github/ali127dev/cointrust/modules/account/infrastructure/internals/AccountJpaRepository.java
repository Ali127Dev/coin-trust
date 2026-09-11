package io.github.ali127dev.cointrust.modules.account.infrastructure.internals;

import io.github.ali127dev.cointrust.modules.account.infrastructure.models.AccountModel;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface AccountJpaRepository extends JpaRepository<AccountModel, UUID> {
    Optional<AccountModel> findByIdAndOwnerId(UUID id, UUID ownerId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT a FROM AccountModel a WHERE a.id = :id AND a.ownerId = :ownerId")
    Optional<AccountModel> findAccountForUpdateByIdAndOwnerId(@Param("id") UUID id, @Param("ownerId") UUID ownerId);
}