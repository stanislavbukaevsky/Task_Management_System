package com.github.stanislavbukaevsky.taskmanagementsystem.token;

import com.github.stanislavbukaevsky.taskmanagementsystem.security.UserSecurityService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

/**
 * Этот класс пропускает запрос пользователя через фильтр.
 * Класс наследуется от абстрактного класса {@link GenericFilterBean}
 */
@Component
@RequiredArgsConstructor
public class TokenFilterService extends GenericFilterBean {
    private final UserSecurityService userSecurityService;
    private final TokenDetailsService tokenDetailsService;
    private static final String AUTHORIZATION = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";
    private static final int BEGIN_INDEX = 7;

    /**
     * Этот метод фильтрует доступ к ресурсам по jwt токену
     *
     * @param servletRequest  запрос пользователя
     * @param servletResponse ответ пользователю
     * @param filterChain     фильтр сервлета
     * @throws IOException      общий класс исключений ввода-вывода
     * @throws ServletException исключение сервлета
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        final String token = getTokenFromRequest((HttpServletRequest) servletRequest);

        if (token != null && tokenDetailsService.validateAccessToken(token)) {
            final Claims claims = tokenDetailsService.getAccessClaims(token);
            final UserDetails userDetails = userSecurityService.loadUserByUsername(claims.getSubject());
            UsernamePasswordAuthenticationToken detailsUser = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.getAuthorities()
            );
            detailsUser.setDetails(new WebAuthenticationDetailsSource().buildDetails((HttpServletRequest) servletRequest));
            SecurityContextHolder.getContext().setAuthentication(detailsUser);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    /**
     * Приватный метод для передачи токена по запросу пользователя
     *
     * @param request запрос пользователя
     * @return Возвращает токен по запросу пользователя
     */
    private String getTokenFromRequest(HttpServletRequest request) {
        final String bearer = request.getHeader(AUTHORIZATION);

        if (StringUtils.hasText(bearer) && bearer.startsWith(BEARER_PREFIX)) {
            return bearer.substring(BEGIN_INDEX);
        }
        return null;
    }
}
