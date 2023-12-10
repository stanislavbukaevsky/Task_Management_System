package com.github.stanislavbukaevsky.taskmanagementsystem.configuration;

import com.github.stanislavbukaevsky.taskmanagementsystem.token.TokenFilterService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Конфигурационный класс для настройки Spring Security
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfiguration {
    private final TokenFilterService tokenFilterService;
    private static final String[] FREE_ACCESS = {
            "/swagger-resources",
            "/swagger-resources/**",
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/v3/api-docs",
            "/v3/api-docs/**",
            "/webjars/**",
            "/inputs/registration",
            "/inputs/authentication",
            "/tokens/access-new"
    };
    private static final String[] AUTHENTICATED_USER = {
            "/tasks/add-task",
            "/tasks/update-task/**",
            "/tasks/get-task/**",
            "/tasks/delete-task/**",
            "/tasks/update-status/**",
            "/tasks/update-priority/**",
            "/tasks/assign-performer/**",
            "/tasks/author-task/**",
            "/tasks/executor-task/**",
            "/comments/add-comment/**"
    };

    /**
     * Этот метод настраивает правила безопасности для работы с приложением и запрещает/разрешает доступ к определенным ресурсам
     *
     * @param http конфигурация http
     * @return Возвращает сконфигурированный и настроенный клиент http
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers(FREE_ACCESS).permitAll();
                    auth.requestMatchers(AUTHENTICATED_USER).authenticated();
                })
                .addFilterAfter(tokenFilterService, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
