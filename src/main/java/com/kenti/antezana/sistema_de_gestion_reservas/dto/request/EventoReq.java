package com.kenti.antezana.sistema_de_gestion_reservas.dto.request;

import com.kenti.antezana.sistema_de_gestion_reservas.model.TipoDeEvento;

public record EventoReq(
    String nombre,
    String descripcion,
    TipoDeEvento tipoDeEvento
) { }
