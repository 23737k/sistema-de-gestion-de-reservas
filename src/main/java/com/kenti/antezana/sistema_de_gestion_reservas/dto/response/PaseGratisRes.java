package com.kenti.antezana.sistema_de_gestion_reservas.dto.response;

import java.time.LocalDate;

public record PaseGratisRes(
    LocalDate fechaDeOtorgamiento,
    boolean usado
) {
}
