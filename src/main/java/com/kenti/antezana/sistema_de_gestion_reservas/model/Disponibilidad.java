package com.kenti.antezana.sistema_de_gestion_reservas.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Disponibilidad {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private TipoDeEntrada tipoDeEntradaAdquirido;
    private Double precio;
    private Integer cuposTotales;
    private Integer cuposOcupados;

    public int cuposDisponibles(){
        return Math.max(this.cuposTotales - this.cuposOcupados, 0);
    }
    public void reservarCupo(){
        this.cuposOcupados++;
    }
}
