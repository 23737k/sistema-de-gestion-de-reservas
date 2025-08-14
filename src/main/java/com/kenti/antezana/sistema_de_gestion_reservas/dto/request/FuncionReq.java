package com.kenti.antezana.sistema_de_gestion_reservas.dto.request;

import com.kenti.antezana.sistema_de_gestion_reservas.model.Disponibilidad;
import com.kenti.antezana.sistema_de_gestion_reservas.model.Lugar;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record FuncionReq(
    LocalDate fecha,
    LocalTime hora,
    Lugar lugar,
    List<Disponibilidad> disponibilidades
) {
}
