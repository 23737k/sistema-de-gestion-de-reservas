package com.kenti.antezana.sistema_de_gestion_reservas.model;

import com.kenti.antezana.sistema_de_gestion_reservas.exception.TipoDeEntradaInvalidaException;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
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
    @JoinColumn(name = "funcion_id")
    private List<Disponibilidad> disponibilidades;

    public Disponibilidad encontrarDisponibilidad(TipoDeEntrada tipoDeEntrada) {
        return disponibilidades.stream()
            .filter(d -> d.getTipoDeEntrada().equals(tipoDeEntrada))
            .findFirst()
            .orElseThrow(() -> {
                String entradasValidas = disponibilidades.stream()
                    .map(d -> d.getTipoDeEntrada().name())
                    .collect(Collectors.joining(", "));

                String mensaje = "El tipo de entrada '" + tipoDeEntrada.name() + "' no está disponible para esta función. "
                    + "Tipos disponibles: " + entradasValidas;

                return new TipoDeEntradaInvalidaException(mensaje);
            });
    }
}
