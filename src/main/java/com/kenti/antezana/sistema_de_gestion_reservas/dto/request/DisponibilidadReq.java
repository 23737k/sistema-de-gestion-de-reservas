package com.kenti.antezana.sistema_de_gestion_reservas.dto.request;

import com.kenti.antezana.sistema_de_gestion_reservas.model.TipoDeEntrada;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

public record DisponibilidadReq(

    @Schema(description = "Tipo de entrada disponible", example = "ENTRADA_GENERAL")
    @NotNull(message = "El tipo de entrada no puede estar vacío.")
    TipoDeEntrada tipoDeEntrada,

    @Schema(description = "Precio de la entrada", example = "1500")
    @NotNull @Positive(message = "El precio debe ser mayor que cero.")
    BigDecimal precio,

    @Schema(description = "Cantidad de cupos totales", example = "150")
    @NotNull(message = "Los cupos totales no puede estar vacío.")
    @Positive(message = "La cantidad de cupos totales debe ser mayor a cero")
    int cuposTotales
) {
}
