package io.github.ali127dev.cointrust.modules.account.presentation.http.v1;

import io.github.ali127dev.cointrust.modules.account.application.usecases.ChangeAddressUsecase;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/v1/accounts")
@Tag(name = "Account")
@RequiredArgsConstructor
public class ChangeAddressController {
    private final ChangeAddressUsecase changeAddressUsecase;

    @PatchMapping("/addresses")
    public ResponseEntity<Void> changeAddress(@RequestBody @Valid ChangeAddressInput dto) {
        // TODO: Replace request-body ownerId with the authenticated user's ID from Spring SecurityContext.
        var input = new ChangeAddressUsecase.ChangeAddressInput(
                dto.ownerId(),
                dto.address()
        );

        changeAddressUsecase.execute(input);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    public record ChangeAddressInput(@NotBlank String ownerId, @NotNull String address) {
    }
}
