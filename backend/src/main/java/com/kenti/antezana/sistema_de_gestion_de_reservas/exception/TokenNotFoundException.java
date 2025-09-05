package com.kenti.antezana.sistema_de_gestion_de_reservas.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TokenNotFoundException extends RuntimeException {
    private String message;
}
