package com.kenti.antezana.sistema_de_gestion_de_reservas.dto.response;

import com.kenti.antezana.sistema_de_gestion_de_reservas.model.TipoDeEntrada;
import java.math.BigDecimal;

public record DisponibilidadRes(
    Long id,
    TipoDeEntrada tipoDeEntrada,
    BigDecimal precio,
    int cuposTotales,
    int cuposOcupados
) {
}
