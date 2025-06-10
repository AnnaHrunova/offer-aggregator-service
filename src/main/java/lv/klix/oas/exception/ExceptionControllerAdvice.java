package lv.klix.oas.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;

@RestControllerAdvice
@Slf4j
public class ExceptionControllerAdvice {

    @ExceptionHandler(RuntimeException.class)
    public Mono<ResponseEntity<String>> handleRuntimeException(RuntimeException ex) {
        log.error("RuntimeException occurred: ", ex);
        return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Unexpected error"));
    }

    @ExceptionHandler(InvalidDataException.class)
    public Mono<ResponseEntity<String>> handleInvalidDataException(InvalidDataException ex) {
        log.error("InvalidDataException occurred: ", ex);
        return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage()));
    }

    @ExceptionHandler(InvalidOperationException.class)
    public Mono<ResponseEntity<String>> handleInvalidOperationException(InvalidOperationException ex) {
        log.error("InvalidOperationException occurred: ", ex);
        return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage()));
    }

    @ExceptionHandler(WebExchangeBindException.class)
    public Mono<ResponseEntity<Map<String, String>>> handleConstraintViolation(WebExchangeBindException ex) {
        var errors = ex.getFieldErrors()
                .stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        f -> ofNullable(f.getDefaultMessage()).orElse("")));
        log.error("WebExchangeBindException occurred: ", ex);
        return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors));
    }
}