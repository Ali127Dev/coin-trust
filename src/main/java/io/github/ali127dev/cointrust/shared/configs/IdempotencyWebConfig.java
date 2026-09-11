package io.github.ali127dev.cointrust.shared.configs;

import io.github.ali127dev.cointrust.shared.application.usecases.IdempotencyService;
import io.github.ali127dev.cointrust.shared.infrastructure.interceptors.IdempotencyInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class IdempotencyWebConfig implements WebMvcConfigurer {
    private final IdempotencyService idempotencyService;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new IdempotencyInterceptor(idempotencyService));
    }
}