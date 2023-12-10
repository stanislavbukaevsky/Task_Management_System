package com.github.stanislavbukaevsky.taskmanagementsystem.handler;

import com.github.stanislavbukaevsky.taskmanagementsystem.exception.AuthenticationUsersException;
import com.github.stanislavbukaevsky.taskmanagementsystem.exception.LoginAlreadyExistsException;
import com.github.stanislavbukaevsky.taskmanagementsystem.exception.TaskNotFoundException;
import com.github.stanislavbukaevsky.taskmanagementsystem.exception.UserByIdNotFoundException;
import com.github.stanislavbukaevsky.taskmanagementsystem.util.ResponseApiException;
import com.github.stanislavbukaevsky.taskmanagementsystem.util.ResponseValidationException;
import com.github.stanislavbukaevsky.taskmanagementsystem.util.Violation;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.github.stanislavbukaevsky.taskmanagementsystem.constant.ExceptionTextMessageConstant.MALFORMED_JWT_EXCEPTION_MESSAGE_SERVICE;

/**
 * Этот класс для обработки всех исключений приложения на уровне контроллеров
 */
@Slf4j
@RestControllerAdvice
public class ExceptionApiHandler {

    /**
     * Этот метод обрабатывает все исключения, возникшие с логином пользователя
     *
     * @param exception исключение
     * @return Возвращает сформированное сообщение пользователю об ошибке, возникшей в результате неправильного запроса
     */
    @ExceptionHandler(LoginAlreadyExistsException.class)
    public ResponseEntity<ResponseApiException> loginAlreadyExistsException(LoginAlreadyExistsException exception) {
        log.error(exception.getMessage(), exception);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ResponseApiException(HttpStatus.BAD_REQUEST.value(), exception.getMessage(), getDateTime()));
    }

    /**
     * Этот метод обрабатывает все исключения, возникшие с аутентификацией
     *
     * @param exception исключение
     * @return Возвращает сформированное сообщение пользователю об ошибке, возникшей в результате неправильного запроса
     */
    @ExceptionHandler(AuthenticationUsersException.class)
    public ResponseEntity<ResponseApiException> authenticationUsersException(AuthenticationUsersException exception) {
        log.error(exception.getMessage(), exception);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResponseApiException(HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.getMessage(), getDateTime()));
    }

    /**
     * Этот метод обрабатывает все исключения, возникшие с поиском пользователей
     *
     * @param exception исключение
     * @return Возвращает сформированное сообщение пользователю об ошибке, возникшей в результате неправильного запроса
     */
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ResponseApiException> usernameNotFoundException(UsernameNotFoundException exception) {
        log.error(exception.getMessage(), exception);
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ResponseApiException(HttpStatus.NOT_FOUND.value(), exception.getMessage(), getDateTime()));
    }

    /**
     * Этот метод обрабатывает все исключения, возникшие с аутентификацией при помощи JWT
     *
     * @param exception исключение
     * @return Возвращает сформированное сообщение пользователю об ошибке, возникшей в результате неправильного запроса
     */
    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ResponseApiException> expiredJwtException(ExpiredJwtException exception) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResponseApiException(HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.getMessage(), getDateTime()));
    }

    /**
     * Этот метод обрабатывает все исключения, возникшие с аутентификацией при помощи JWT
     *
     * @param exception исключение
     * @return Возвращает сформированное сообщение пользователю об ошибке, возникшей в результате неправильного запроса
     */
    @ExceptionHandler(UnsupportedJwtException.class)
    public ResponseEntity<ResponseApiException> unsupportedJwtException(UnsupportedJwtException exception) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResponseApiException(HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.getMessage(), getDateTime()));
    }

    /**
     * Этот метод обрабатывает все исключения, возникшие с аутентификацией при помощи JWT
     *
     * @param exception исключение
     * @return Возвращает сформированное сообщение пользователю об ошибке, возникшей в результате неправильного запроса
     */
    @ExceptionHandler(MalformedJwtException.class)
    public ResponseEntity<ResponseApiException> malformedJwtException(MalformedJwtException exception) {
        log.error(exception.getMessage(), exception);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResponseApiException(HttpStatus.INTERNAL_SERVER_ERROR.value(), MALFORMED_JWT_EXCEPTION_MESSAGE_SERVICE, getDateTime()));
    }

    /**
     * Этот метод обрабатывает все исключения, возникшие с аутентификацией при помощи JWT
     *
     * @param exception исключение
     * @return Возвращает сформированное сообщение пользователю об ошибке, возникшей в результате неправильного запроса
     */
    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<ResponseApiException> signatureException(SignatureException exception) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResponseApiException(HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.getMessage(), getDateTime()));
    }

    /**
     * Этот метод обрабатывает все исключения, возникшие с неправильной валидацией
     *
     * @param exception исключение
     * @return Возвращает сформированное сообщение пользователю об ошибке, возникшей в результате неправильного запроса
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ResponseValidationException> onConstraintValidationException(ConstraintViolationException exception) {
        final List<Violation> violations = exception.getConstraintViolations().stream()
                .map(violation ->
                        new Violation(violation.getPropertyPath().toString(), violation.getMessage(), getDateTime())
                ).collect(Collectors.toList());
        log.error(exception.getMessage(), exception);
        return ResponseEntity.ok(new ResponseValidationException(violations));
    }

    /**
     * Этот метод обрабатывает все исключения, возникшие с неправильной валидацией
     *
     * @param exception исключение
     * @return Возвращает сформированное сообщение пользователю об ошибке, возникшей в результате неправильного запроса
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseValidationException> onMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        final List<Violation> violations = exception.getBindingResult().getFieldErrors().stream()
                .map(violation ->
                        new Violation(violation.getField(), violation.getDefaultMessage(), getDateTime())
                ).collect(Collectors.toList());
        log.error(exception.getMessage(), exception);
        return ResponseEntity.ok(new ResponseValidationException(violations));
    }

    /**
     * Этот метод обрабатывает все исключения, возникшие с запрещением входа на ресурс
     *
     * @param exception исключение
     * @return Возвращает сформированное сообщение пользователю об ошибке, возникшей в результате неправильного запроса
     */
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ResponseApiException> responseStatusException(ResponseStatusException exception) {
        log.error(exception.getMessage(), exception);
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(new ResponseApiException(HttpStatus.FORBIDDEN.value(), exception.getMessage(), getDateTime()));
    }

    /**
     * Этот метод обрабатывает все исключения, возникшие с неверным поиском задачи
     *
     * @param exception исключение
     * @return Возвращает сформированное сообщение пользователю об ошибке, возникшей в результате неправильного запроса
     */
    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<ResponseApiException> taskNotFoundException(TaskNotFoundException exception) {
        log.error(exception.getMessage(), exception);
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ResponseApiException(HttpStatus.NOT_FOUND.value(), exception.getMessage(), getDateTime()));
    }

    /**
     * Этот метод обрабатывает все исключения, возникшие с неверным поиском пользователя по идентификатору
     *
     * @param exception исключение
     * @return Возвращает сформированное сообщение пользователю об ошибке, возникшей в результате неправильного запроса
     */
    @ExceptionHandler(UserByIdNotFoundException.class)
    public ResponseEntity<ResponseApiException> userByIdNotFoundException(UserByIdNotFoundException exception) {
        log.error(exception.getMessage(), exception);
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ResponseApiException(HttpStatus.NOT_FOUND.value(), exception.getMessage(), getDateTime()));
    }

    /**
     * Приватный метод, который формирует настоящие дату и время
     *
     * @return Возвращает настоящие дату и время, когда было выброшено исключение
     */
    private LocalDateTime getDateTime() {
        return LocalDateTime.now();
    }
}
