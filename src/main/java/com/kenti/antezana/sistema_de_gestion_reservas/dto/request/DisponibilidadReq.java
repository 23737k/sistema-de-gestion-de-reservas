package com.kenti.antezana.sistema_de_gestion_reservas.dto.request;

import com.kenti.antezana.sistema_de_gestion_reservas.model.TipoDeEntrada;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record DisponibilidadReq(
    @NotNull(message = "El tipo de entrada no puede estar vacío.")
    TipoDeEntrada tipoDeEntrada,
    @NotNull(message = "El precio no puede estar vacío.")
    @Positive(message = "El valor debe ser mayor a cero.")
    Double precio,
    @NotNull(message = "Los cupos totales no puede estar vacío.")
    @Positive(message = "La cantidad de cupos totales debe ser mayor a cero")
    int cuposTotales
) {
}
