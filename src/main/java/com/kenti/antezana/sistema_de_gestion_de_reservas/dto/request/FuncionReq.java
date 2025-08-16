package com.kenti.antezana.sistema_de_gestion_de_reservas.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kenti.antezana.sistema_de_gestion_de_reservas.model.Lugar;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record FuncionReq(
    @Schema(description = "Fecha de la función en formato yyyy-MM-dd", example = "2025-08-31")
    @Future(message = "La fecha de la función debe ser posterior a la fecha actual")
    @NotNull(message = "La fecha no puede estar vacía")
    LocalDate fecha,

    @Schema(description = "Hora de la función en formato HH:mm:ss (24h)", example = "14:30:00")
    @NotNull(message = "La hora no puede estar vacía")
    @JsonFormat(pattern = "HH:mm:ss")
    LocalTime hora,

    @Schema(description = "Lugar donde se realiza la función")
    @Valid
    Lugar lugar,

    @Schema(description = "Lista de disponibilidades por tipo de entrada")
    @NotEmpty(message = "Debe haber al menos una disponibilidad")
    @Valid
    List<DisponibilidadReq> disponibilidades
) {
}
