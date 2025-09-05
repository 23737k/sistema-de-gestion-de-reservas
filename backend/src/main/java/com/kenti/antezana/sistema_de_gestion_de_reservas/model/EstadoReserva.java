package com.kenti.antezana.sistema_de_gestion_de_reservas.model;

import java.util.List;

public enum EstadoReserva {
    RESERVADO,
    PAGADO,
    CONFIRMADO,
    CANCELADO,
    ASISTIDO,
    AUSENTE;

    private List<EstadoReserva> estadosReservasValidos;

    static {
        RESERVADO.estadosReservasValidos = List.of(PAGADO, CANCELADO);
        PAGADO.estadosReservasValidos = List.of(CONFIRMADO, CANCELADO);
        CONFIRMADO.estadosReservasValidos = List.of(ASISTIDO, AUSENTE, CANCELADO);
        CANCELADO.estadosReservasValidos = List.of();
        ASISTIDO.estadosReservasValidos = List.of();
        AUSENTE.estadosReservasValidos = List.of();
    }

    public boolean cambioDeEstadoValido(EstadoReserva nuevoEstado) {
        return estadosReservasValidos.contains(nuevoEstado);
    }
}

