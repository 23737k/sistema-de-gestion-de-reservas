package com.kenti.antezana.sistema_de_gestion_de_reservas.dto.request;

import com.kenti.antezana.sistema_de_gestion_de_reservas.model.EstadoReserva;
import jakarta.validation.constraints.NotNull;

public record EstadoReservaReq(
    @NotNull(message = "El estado de la reserva no puede estar vacio")
    EstadoReserva estadoReserva
) {
}
