package io.github.ali127dev.cointrust.shared.infrastructure.interceptors;

import io.github.ali127dev.cointrust.shared.application.usecases.IdempotencyService;
import io.github.ali127dev.cointrust.shared.domain.exceptions.BusinessRuleViolationException;
import io.github.ali127dev.cointrust.shared.domain.exceptions.InvalidDataException;
import io.github.ali127dev.cointrust.shared.infrastructure.annotations.Idempotent;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@RequiredArgsConstructor
public class IdempotencyInterceptor implements HandlerInterceptor {

    private static final String HEADER_NAME = "Idempotency-Key";
    private static final String REQUEST_ATTRIBUTE = "idempotencyKey";

    private final IdempotencyService idempotencyService;

    @Override
    public boolean preHandle(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull Object handler
    ) {
        if (!(handler instanceof HandlerMethod handlerMethod)) return true;
        if (!handlerMethod.hasMethodAnnotation(Idempotent.class)) return true;

        String key = request.getHeader(HEADER_NAME);

        if (key == null || key.isBlank()) {
            throw new InvalidDataException(
                    "Idempotency-Key header is required"
            );
        }

        var result = idempotencyService.tryClaim(key);

        switch (result.outcome()) {
            case CLAIMED -> {
                request.setAttribute(REQUEST_ATTRIBUTE, key);
                return true;
            }

            case IN_PROGRESS -> throw new BusinessRuleViolationException(
                    "Request with this Idempotency-Key is already being processed"
            ) {
            };

            case ALREADY_COMPLETED -> {
                response.setStatus(result.httpStatus());
                return false;
            }
        }

        throw new IllegalStateException(
                "Unhandled idempotency outcome: " + result.outcome()
        );
    }

    @Override
    public void afterCompletion(
            HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull Object handler,
            Exception ex
    ) {
        String key = (String) request.getAttribute(REQUEST_ATTRIBUTE);

        if (key == null) {
            return;
        }

        int status = response.getStatus();

        if (status >= 500) {
            idempotencyService.release(key);
            return;
        }

        idempotencyService.complete(key, status);
    }
}
