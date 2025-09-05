package com.kenti.antezana.sistema_de_gestion_de_reservas.dto.request;

import com.kenti.antezana.sistema_de_gestion_de_reservas.model.TipoDeEvento;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record EventoReq(
    @Schema(description = "Nombre del evento", example = "Concierto de Rock")
    @NotBlank(message = "El nombre no puede estar vacío")
    String nombre,

    @Schema(description = "Descripción del evento", example = "Un concierto para fans del rock clásico")
    @NotBlank(message = "La descripción no puede estar vacía")
    String descripcion,

    @Schema(description = "Tipo de evento", example = "RECITAL")
    @NotNull(message = "El tipo de evento no puede estar vacío")
    TipoDeEvento tipoDeEvento
) { }
