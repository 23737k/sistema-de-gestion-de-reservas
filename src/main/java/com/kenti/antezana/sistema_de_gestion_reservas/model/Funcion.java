package com.kenti.antezana.sistema_de_gestion_reservas.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Data
public class Funcion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate fecha;
    private LocalTime hora;
    @Embedded
    private Lugar lugar;
    @ManyToOne
    private Evento evento;
    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "funcionId")
    private List<Disponibilidad> disponibilidades;
}
