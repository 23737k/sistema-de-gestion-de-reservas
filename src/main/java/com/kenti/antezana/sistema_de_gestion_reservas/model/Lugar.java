package com.kenti.antezana.sistema_de_gestion_reservas.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Lugar {
    @Schema(description = "Dirección de la función", example = "Av. Siempre Viva")
    @NotBlank(message = "La dirección no puede estar vacía")
    private String direccion;

    @Schema(description = "Altura de la dirección", example = "742")
    @NotBlank(message = "La altura no puede estar vacía")
    private String altura;

    @Schema(description = "Localidad", example = "Springfield")
    @NotBlank(message = "La localidad no puede estar vacía")
    private String localidad;

    @Schema(description = "Provincia", example = "Buenos Aires")
    @NotBlank(message = "La provincia no puede estar vacía")
    private String provincia;
}