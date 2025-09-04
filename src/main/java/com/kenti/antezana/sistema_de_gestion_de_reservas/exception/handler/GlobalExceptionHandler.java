package com.kenti.antezana.sistema_de_gestion_de_reservas.exception.handler;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.kenti.antezana.sistema_de_gestion_de_reservas.exception.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
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

    //Error de validacion @Valid
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionRes<Set<String>>> handleException(
        MethodArgumentNotValidException e) {
        Set<String> errorMessages = e.getBindingResult().getAllErrors().stream().map(
            DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toSet());
        ExceptionRes<Set<String>> response = new ExceptionRes<>(400, "Error de validacion:",
            "Uno o mas campos de la request son invalidos", errorMessages);
        return ResponseEntity.badRequest().body(response);
    }

    //Error de validacion @Validated
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


    //Error en el json de la request
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ExceptionRes<?>> handleException(HttpMessageNotReadableException e) {
        Throwable cause = e.getMostSpecificCause();
        if (cause instanceof InvalidFormatException invalidFormatException) {
            return handleException(invalidFormatException);
        } else {
            ExceptionRes<?> response = new ExceptionRes<>(400, "Bad request",
                "JSON inválido o formato incorrecto en la request", null);
            return ResponseEntity.badRequest().body(response);
        }
    }

    //Error en el tipo de un valor esperado para un campo
    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<ExceptionRes<?>> handleException(InvalidFormatException e) {
        String fieldPath = e.getPath().stream()
            .map(JsonMappingException.Reference::getFieldName)
            .collect(Collectors.joining("."));
        Class<?> targetType = e.getTargetType();
        String receivedValue = e.getValue() != null ? e.getValue().toString() : "null";

        String expectedType;
        //Si el tipo del campo es un enum. Muestra los valores validos.
        if (targetType.isEnum()) {
            String knownValues = Arrays.stream(targetType.getEnumConstants())
                .map(Object::toString)
                .collect(Collectors.joining(", "));
            expectedType = "uno de los valores: " + knownValues;

        //Si el tipo es fecha
        } else if (targetType.equals(java.time.LocalDate.class)) {
            expectedType = "fecha en formato yyyy-MM-dd";
        //Si el tipo es tiempo
        } else if (targetType.equals(java.time.LocalTime.class)) {
            expectedType = "hora en formato HH:mm:ss";
        //Si el tipo es fecha y hora
        } else if (targetType.equals(java.time.LocalDateTime.class)) {
            expectedType = "fecha y hora en formato yyyy-MM-dd'T'HH:mm:ss";
        //cualquier otro tipo (string, int,..)
        } else {
            expectedType = targetType.getSimpleName();
        }

        String errorMessage = String.format("Campo '%s' recibió '%s', se esperaba %s",
            fieldPath, receivedValue, expectedType);

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

    @ExceptionHandler(DisponibilidadAgotadaException.class)
    public ResponseEntity<ExceptionRes<?>> handleException(DisponibilidadAgotadaException e) {
        ExceptionRes<?> response = new ExceptionRes<>(400, "Bad request", e.getMessage(), null);
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(CambioDeEstadoInvalidoException.class)
    public ResponseEntity<ExceptionRes<?>> handleException(CambioDeEstadoInvalidoException e) {
        ExceptionRes<?> response = new ExceptionRes<>(400, "Bad request", e.getMessage(), null);
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ExceptionRes<?>> handleException(UserAlreadyExistsException e) {
        ExceptionRes<?> response = new ExceptionRes<>(400, "Bad request", "This user is already registered", null);
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<ExceptionRes<?>> handleException(AuthorizationDeniedException e) {
        ExceptionRes<?> response = new ExceptionRes<>(401, "Unauthorized", e.getMessage(), null);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ExceptionRes<?>> handleException(BadCredentialsException ex) {
        ExceptionRes<?> response =  new ExceptionRes<>(401, "Unauthorized", ex.getMessage(), null );
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionRes<?>> handleException(Exception ex) {
        log.error("Se ha producido un error inesperado: ", ex);
        ExceptionRes<?> response = new ExceptionRes<>(500, "Internal Server Error", null, null);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
