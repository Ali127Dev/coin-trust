package io.github.ali127dev.cointrust.modules.account.presentation.http.v1;

import io.github.ali127dev.cointrust.modules.account.application.usecases.DepositUsecase;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/accounts")
@Tag(name = "Account")
@RequiredArgsConstructor
public class DepositController {
    private final DepositUsecase depositUsecase;

    @PostMapping("/deposits")
    public ResponseEntity<Void> deposit(@RequestBody @Valid DepositInput dto) {
        var input = new DepositUsecase.DepositInput(dto.accountId(), dto.amount());

        depositUsecase.execute(input);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    public record DepositInput(@NotBlank String accountId, @Positive long amount) {
    }
}