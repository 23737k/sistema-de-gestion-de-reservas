package com.kenti.antezana.sistema_de_gestion_reservas.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Evento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String descripcion;
    private TipoDeEvento tipoDeEvento;
    @OneToMany(orphanRemoval = true,  cascade = CascadeType.ALL, mappedBy = "evento")
    private List<Funcion> funciones;
}
