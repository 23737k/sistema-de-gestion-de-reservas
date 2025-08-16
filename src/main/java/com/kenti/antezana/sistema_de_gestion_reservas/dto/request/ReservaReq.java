package com.kenti.antezana.sistema_de_gestion_reservas.dto.request;

import com.kenti.antezana.sistema_de_gestion_reservas.model.TipoDeEntrada;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record ReservaReq(
    @Schema(description = "ID de la función a la que se quiere reservar", example = "1")
    @NotNull(message = "El id de la función no debe estar vacío")
    Long funcionId,

    @Schema(description = "Tipo de entrada a reservar", example = "PLATEA")
    @NotNull(message = "El tipo de entrada no debe estar vacío")
    TipoDeEntrada tipoDeEntrada
) {
}
