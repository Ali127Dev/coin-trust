package io.github.ali127dev.cointrust.modules.account.presentation.http.v1;

import io.github.ali127dev.cointrust.modules.account.application.usecases.WithdrawUsecase;
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
public class WithdrawController {
    private final WithdrawUsecase withdrawUsecase;

    @PostMapping("/{accountId}/withdraw")
    public ResponseEntity<Void> withdraw(
            @PathVariable String accountId,
            @RequestBody @Valid WithdrawInput dto
    ) {
        // TODO: Replace request-body ownerId with the authenticated user's ID from Spring SecurityContext.
        var input = new WithdrawUsecase.WithdrawInput(accountId, dto.ownerId(), dto.amount());

        withdrawUsecase.execute(input);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    public record WithdrawInput(@NotBlank String ownerId, @Positive long amount) {
    }
}