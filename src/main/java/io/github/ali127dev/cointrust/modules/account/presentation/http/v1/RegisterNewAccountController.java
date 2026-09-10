package io.github.ali127dev.cointrust.modules.account.presentation.http.v1;

import io.github.ali127dev.cointrust.modules.account.application.usecases.RegisterNewAccountUsecase;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/v1/accounts")
@RequiredArgsConstructor
public class RegisterNewAccountController {
    private final RegisterNewAccountUsecase registerNewAccountUsecase;

    @PostMapping
    public ResponseEntity<RegisterNewAccountOutput> registerNewAccount(@RequestBody @Valid RegisterNewAccountInput dto) {
        // TODO: Replace request-body ownerId with the authenticated user's ID from Spring SecurityContext.
        var input = new RegisterNewAccountUsecase.RegisterNewAccountInput(dto.ownerId());

        var output = registerNewAccountUsecase.execute(input);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new RegisterNewAccountOutput(output.accountId()));
    }

    public record RegisterNewAccountInput(@NotBlank String ownerId) {
    }

    public record RegisterNewAccountOutput(UUID accountId) {
    }
}
