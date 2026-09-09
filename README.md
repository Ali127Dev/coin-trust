# CoinTrust

A double-entry ledger and wallet system built with **Domain-Driven Design** in Java and Spring Boot — designed to explore the hard problems behind financial systems: concurrency, consistency, and correctness under load.

> This is not another CRUD e-commerce clone. CoinTrust has few modules, but each one carries real business complexity: race conditions, atomic transfers, idempotency, and strict data isolation guarantees.

---

## Why this project exists

Most portfolio projects stop at "it works on the happy path." CoinTrust is built around the opposite question: **what happens when it doesn't?**

- What happens when two withdrawals hit the same wallet at the same millisecond?
- What happens when a client retries a payment request because of a network timeout?
- What happens when two transfers try to lock the same two accounts in opposite order?

These are the questions real financial systems have to answer, and this project answers them explicitly, with tests to prove it.

---

## Core concepts implemented

### Double-entry bookkeeping
Every transaction produces two balanced ledger entries — one debit, one credit — never a single mutable balance field. This mirrors how real accounting and banking systems guarantee correctness: the books always balance, by construction.

### Concurrency control
- **Optimistic locking** via version columns, with retry logic on conflict
- **Pessimistic locking** for high-contention paths, with a fixed account-locking order to eliminate deadlocks
- Concurrency correctness verified with multi-threaded integration tests (not just assumed)

### Idempotent transaction handling
Every transfer request carries an idempotency key. Duplicate requests — from retries, double-clicks, or network issues — are detected and short-circuited to the original result, never processed twice.

### Isolation-level awareness
Transaction isolation levels (`READ COMMITTED`, `REPEATABLE READ`, `SERIALIZABLE`) are chosen deliberately per operation, not left at the default — with the trade-offs documented and tested.

### Immutable audit trail
Transactions are never updated or deleted. Corrections happen through compensating (reversal) entries, preserving a complete, tamper-evident history — the same principle real ledgers and banks rely on.

### Domain-Driven Design
- **Domain layer**: pure business logic (`Wallet`, `Account`, `Money`), with zero framework dependencies
- **Application layer**: use cases orchestrating domain behavior
- **Infrastructure layer**: persistence, security, and framework wiring
- **Interface layer**: REST controllers and DTOs

Identifiers are modeled as typed Value Objects (`AccountId`, `OwnerId`, etc.) rather than raw UUIDs, preventing entire classes of bugs where IDs get swapped or misused across module boundaries.

### Authentication & authorization
JWT-based authentication with a custom filter chain, and role-based authorization enforcing who can view, transfer, or administer wallet operations.

---

## Tech stack

| Layer | Technology |
|---|---|
| Language | Java |
| Framework | Spring Boot, Spring Security |
| Persistence | Spring Data JPA |
| Architecture | Domain-Driven Design, layered/hexagonal |
| Concurrency | Optimistic & pessimistic locking, isolation-level tuning |
| Testing | Multi-threaded concurrency tests |
