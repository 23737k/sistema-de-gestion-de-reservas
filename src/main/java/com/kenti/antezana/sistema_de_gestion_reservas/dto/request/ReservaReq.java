package com.kenti.antezana.sistema_de_gestion_reservas.dto.request;

import com.kenti.antezana.sistema_de_gestion_reservas.model.TipoDeEntrada;
import jakarta.validation.constraints.NotNull;

public record ReservaReq(
    @NotNull(message = "El id de la funcion no debe estar vacio.")
    Long funcionId,
    @NotNull(message = "El tipo de entrada no debe estar vacio.")
    TipoDeEntrada tipoDeEntrada,
    @NotNull(message = "El id del cliente no debe estar vacio")
    Long clienteId
) {
}
