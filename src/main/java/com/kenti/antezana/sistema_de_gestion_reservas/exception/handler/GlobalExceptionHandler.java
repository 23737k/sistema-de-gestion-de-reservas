package com.kenti.antezana.sistema_de_gestion_reservas.exception.handler;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.kenti.antezana.sistema_de_gestion_reservas.exception.TipoDeEntradaDuplicadosException;
import com.kenti.antezana.sistema_de_gestion_reservas.exception.TipoDeEntradaInvalidaException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public record ExceptionRes<T>(
        Integer status,
        String message,
        String description,
        T data
    ) {
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ExceptionRes<?>> handleException(IllegalStateException e) {
        ExceptionRes<?> response = new ExceptionRes<>(400, "Bad request", null, null);
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ExceptionRes<?>> handleException(final EntityNotFoundException e) {
        ExceptionRes<?> response = new ExceptionRes<>(404, "Not found", e.getMessage(), null);
        return ResponseEntity.status(404).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionRes<Set<String>>> handleException(
        MethodArgumentNotValidException e) {
        Set<String> errorMessages = e.getBindingResult().getAllErrors().stream().map(
            DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toSet());
        ExceptionRes<Set<String>> response = new ExceptionRes<>(400, "Error de validacion:",
            "Uno o mas campos de la request son invalidos", errorMessages);
        return ResponseEntity.badRequest().body(response);
    }


    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ExceptionRes<Set<String>>> handleException(
        ConstraintViolationException e) {
        Set<String>
            errorMessages =
            e.getConstraintViolations().stream().map(ConstraintViolation::getMessage).collect(
                Collectors.toSet());
        ExceptionRes<Set<String>> response = new ExceptionRes<>(400, "Error de validacion: ",
            "Uno o mas campos de la request son invalidos", errorMessages);
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ExceptionRes<?>> handleException(HttpMessageNotReadableException e) {
        Throwable cause = e.getMostSpecificCause();
        if (cause instanceof InvalidFormatException invalidFormatException) {
            return handleException(invalidFormatException);
        } else {
            ExceptionRes<?> response =
                new ExceptionRes<>(400, "Bad request", "Formato inválido en la request", null);
            return ResponseEntity.badRequest().body(response);
        }
    }

    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<ExceptionRes<?>> handleException(InvalidFormatException e) {
        Class<?> targetType = e.getTargetType();
        String errorMessage;
        if (targetType.isEnum()) {
            String knownValues = Arrays.stream(targetType.getEnumConstants())
                .map(Object::toString)
                .collect(Collectors.joining(", "));
            errorMessage = "Formato invalido: " + e.getValue() + " no es un valor correcto para " +
                targetType.getSimpleName() +
                ". Valores posibles: " + knownValues;
        } else {
            errorMessage = "Formato incorrecto en el Json";
        }
        ExceptionRes<?> response = new ExceptionRes<>(400, "Bad request", errorMessage, null);
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ExceptionRes<?>> handleException(MethodArgumentTypeMismatchException e) {
        String errorMessage = "Tipo invalido: " + e.getValue() + " no es un valor valido para " +
            Objects.requireNonNull(
                e.getRequiredType()).getSimpleName();
        ExceptionRes<?> response = new ExceptionRes<>(400, "Bad request", errorMessage, null);
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ExceptionRes<?>> handleException(NoResourceFoundException e) {
        ExceptionRes<?> response =
            new ExceptionRes<>(404, "Not found", "No se ha encontrado el recurso.", null);
        return ResponseEntity.status(404).body(response);
    }

    @ExceptionHandler(TipoDeEntradaInvalidaException.class)
    public ResponseEntity<ExceptionRes<?>> handleException(TipoDeEntradaInvalidaException e) {
        ExceptionRes<?> response = new ExceptionRes<>(400, "Bad request", e.getMessage(), null);
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(TipoDeEntradaDuplicadosException.class)
    public ResponseEntity<ExceptionRes<?>> handleException(TipoDeEntradaDuplicadosException e) {
        ExceptionRes<?> response = new ExceptionRes<>(400, "Bad request", e.getMessage(), null);
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionRes<?>> handleException(Exception ex) {
        log.error("Se ha producido un error inesperado: ", ex);
        ExceptionRes<?> response = new ExceptionRes<>(500, "Internal Server Error", null, null);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
