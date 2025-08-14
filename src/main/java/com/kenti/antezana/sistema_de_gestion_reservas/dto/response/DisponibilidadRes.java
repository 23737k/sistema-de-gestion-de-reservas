package com.kenti.antezana.sistema_de_gestion_reservas.dto.response;

import com.kenti.antezana.sistema_de_gestion_reservas.model.TipoDeEntrada;

public record DisponibilidadRes(
    Long id,
    TipoDeEntrada tipoDeEntrada,
    double precio,
    int cuposTotales,
    int cuposOcupados
) {
}
