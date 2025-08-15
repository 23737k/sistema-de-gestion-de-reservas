package com.kenti.antezana.sistema_de_gestion_reservas.dto.response;

import com.kenti.antezana.sistema_de_gestion_reservas.model.EstadoReserva;
import com.kenti.antezana.sistema_de_gestion_reservas.model.TipoDeEntrada;
import java.time.LocalDate;

public record ReservaRes(
    Long id,
    LocalDate fechaCreacion,
    Long funcionId,
    TipoDeEntrada tipoDeEntrada,
    Long clienteId,
    EstadoReserva estadoReserva,
    double montoTotal,
    boolean bonificado
) {
}
