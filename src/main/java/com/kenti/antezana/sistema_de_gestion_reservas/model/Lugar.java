package com.kenti.antezana.sistema_de_gestion_reservas.model;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Embeddable
public class Lugar {
    @NotBlank(message = "La dirección no puede estar vacía")
    private String direccion;
    @NotBlank(message = "La altura no puede estar vacía")
    private String altura;
    @NotBlank(message = "La localidad no puede estar vacía")
    private String localidad;
    @NotBlank(message = "La provincia no puede estar vacía")
    private String provincia;
}