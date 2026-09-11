package io.github.ali127dev.cointrust.shared.infrastructure.interceptors;

import io.github.ali127dev.cointrust.shared.application.usecases.IdempotencyService;
import io.github.ali127dev.cointrust.shared.domain.exceptions.BusinessRuleViolationException;
import io.github.ali127dev.cointrust.shared.domain.exceptions.InvalidDataException;
import io.github.ali127dev.cointrust.shared.infrastructure.annotations.Idempotent;
import io.github.ali127dev.cointrust.shared.infrastructure.filters.CachedBodyHttpServletRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;

@RequiredArgsConstructor
public class IdempotencyInterceptor implements HandlerInterceptor {

    private static final String HEADER_NAME = "Idempotency-Key";
    private static final String REQUEST_ATTRIBUTE = "idempotencyKey";
    private static final String EMPTY_BODY_HASH = "EMPTY";

    private final IdempotencyService idempotencyService;

    @Override
    public boolean preHandle(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull Object handler
    ) throws java.io.IOException {
        if (!(handler instanceof HandlerMethod handlerMethod)) return true;
        if (!handlerMethod.hasMethodAnnotation(Idempotent.class)) return true;

        String key = request.getHeader(HEADER_NAME);

        if (key == null || key.isBlank()) {
            throw new InvalidDataException("Idempotency-Key header is required");
        }

        String payloadHash = computePayloadHash(request);
        String requestKey = key + ":" + request.getRequestURI();
        var result = idempotencyService.tryClaim(requestKey, payloadHash);

        switch (result.outcome()) {
            case CLAIMED -> {
                request.setAttribute(REQUEST_ATTRIBUTE, requestKey);
                return true;
            }

            case IN_PROGRESS -> throw new BusinessRuleViolationException(
                    "Request with this Idempotency-Key is already being processed"
            );

            case ALREADY_COMPLETED -> {
                response.setStatus(result.httpStatus());
                if (result.responseBody() != null) {
                    response.setContentType("application/json");
                    response.getWriter().write(result.responseBody());
                }
                return false;
            }
        }

        throw new IllegalStateException("Unhandled idempotency outcome: " + result.outcome());
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

        String responseBody = null;
        if (response instanceof ContentCachingResponseWrapper wrapper) {
            responseBody = new String(wrapper.getContentAsByteArray(), StandardCharsets.UTF_8);
        }

        idempotencyService.complete(key, status, responseBody);
    }

    private String computePayloadHash(HttpServletRequest request) {
        byte[] body = (request instanceof CachedBodyHttpServletRequest cached)
                ? cached.getCachedBody()
                : new byte[0];

        if (body.length == 0) {
            return EMPTY_BODY_HASH;
        }

        try {
            var digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(body);
            return HexFormat.of().formatHex(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 not available", e);
        }
    }
}