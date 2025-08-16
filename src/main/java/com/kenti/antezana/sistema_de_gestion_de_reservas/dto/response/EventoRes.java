package com.kenti.antezana.sistema_de_gestion_de_reservas.dto.response;

import com.kenti.antezana.sistema_de_gestion_de_reservas.model.TipoDeEvento;

public record EventoRes (
    Long id,
    String nombre,
    String descripcion,
    TipoDeEvento tipoDeEvento
){}
