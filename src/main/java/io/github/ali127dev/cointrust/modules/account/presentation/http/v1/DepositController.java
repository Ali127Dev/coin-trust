package io.github.ali127dev.cointrust.modules.account.presentation.http.v1;

import io.github.ali127dev.cointrust.modules.account.application.usecases.DepositUsecase;
import io.github.ali127dev.cointrust.shared.infrastructure.annotations.Idempotent;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/accounts")
@Tag(name = "Account")
@RequiredArgsConstructor
public class DepositController {
    private final DepositUsecase depositUsecase;

    @PostMapping("/{accountId}/deposit")
    @Idempotent
    @Parameter(
            name = "Idempotency-Key",
            required = true,
            in = ParameterIn.HEADER
    )
    public ResponseEntity<Void> deposit(
            @PathVariable String accountId,
            @RequestBody @Valid DepositInput dto
    ) {
        // TODO: Replace request-body ownerId with the authenticated user's ID from Spring SecurityContext.
        var input = new DepositUsecase.DepositInput(accountId, dto.ownerId(), dto.amount());

        depositUsecase.execute(input);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    public record DepositInput(@NotBlank String ownerId, @Positive long amount) {
    }
}