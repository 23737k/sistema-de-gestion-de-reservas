package com.kenti.antezana.sistema_de_gestion_reservas.model;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Embeddable
public class Lugar {
    private String direccion;
    private String altura;
    private String localidad;
    private String provincia;
}