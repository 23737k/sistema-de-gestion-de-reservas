package com.kenti.antezana.sistema_de_gestion_reservas.dto.request;

import com.kenti.antezana.sistema_de_gestion_reservas.model.TipoDeEvento;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record EventoReq(
    @NotBlank(message = "El nombre no puede estar vacío")
    String nombre,
    @NotBlank(message = "La descripción no puede estar vacía")
    String descripcion,
    @NotNull(message = "El tipo de evento no puede estar vacío")
    TipoDeEvento tipoDeEvento
) { }
