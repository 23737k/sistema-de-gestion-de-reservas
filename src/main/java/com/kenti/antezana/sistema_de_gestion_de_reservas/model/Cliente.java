package com.kenti.antezana.sistema_de_gestion_de_reservas.model;

import jakarta.persistence.*;

import java.util.List;

import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String apellido;
    private String email;
    private String telefono;
    @Enumerated(EnumType.STRING)
    private TipoDeDocumento tipoDeDocumento;
    private String documento;
    @OneToMany(mappedBy = "cliente", orphanRemoval = true)
    private List<Reserva> reservas;
    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "cliente_id")
    private List<PaseGratis> pasesGratis;
    @OneToOne(cascade = CascadeType.ALL)
    private Usuario usuario;

    public List<Reserva> obtenerReservasAsistidas(int anio) {
        return reservas.stream()
            .filter(reserva ->
                reserva.getEstadoReserva().equals(EstadoReserva.ASISTIDO)
                    && reserva.getFechaCreacion().getYear() == anio).toList();
    }
}
