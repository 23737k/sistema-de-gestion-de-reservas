package com.kenti.antezana.sistema_de_gestion_reservas.dto.response;

import com.kenti.antezana.sistema_de_gestion_reservas.model.Lugar;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record FuncionRes(
    Long id,
    LocalDate fecha,
    LocalTime hora,
    Lugar lugar,
    List<DisponibilidadRes> disponibilidades
) {
}
