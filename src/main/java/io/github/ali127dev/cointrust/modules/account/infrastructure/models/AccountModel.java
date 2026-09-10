package io.github.ali127dev.cointrust.modules.account.infrastructure.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "accounts")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AccountModel {
    @Id
    private UUID id;

    private UUID ownerId;

    private String address;

    private long balance;

    private Instant createdAt;

    private Instant updatedAt;
}