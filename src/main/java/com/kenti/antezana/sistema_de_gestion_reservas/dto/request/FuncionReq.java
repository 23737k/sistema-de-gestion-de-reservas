package com.kenti.antezana.sistema_de_gestion_reservas.dto.request;

import com.kenti.antezana.sistema_de_gestion_reservas.model.Lugar;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record FuncionReq(
    @Future(message = "La fecha de la función debe ser posterior a la fecha actual")
    @NotNull(message = "La fecha no puede estar vacía")
    @Pattern(
        regexp = "^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])$",
        message = "El formato de fecha debe ser yyyy-MM-dd"
    )
    LocalDate fecha,
    @Pattern(
        regexp = "^([01]\\d|2[0-3]):[0-5]\\d$",
        message = "El formato de hora debe ser HH:mm"
    )
    @NotNull(message = "La hora no puede estar vacía")
    LocalTime hora,
    @Valid
    Lugar lugar,
    @NotEmpty(message = "Debe haber al menos una disponibilidad")
    @Valid
    List<DisponibilidadReq> disponibilidades
) {
}
