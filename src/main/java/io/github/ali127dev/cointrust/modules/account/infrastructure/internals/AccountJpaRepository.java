package io.github.ali127dev.cointrust.modules.account.infrastructure.internals;

import io.github.ali127dev.cointrust.modules.account.infrastructure.models.AccountModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AccountJpaRepository extends JpaRepository<AccountModel, UUID> {
}
