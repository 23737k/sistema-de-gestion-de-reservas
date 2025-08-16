package com.kenti.antezana.sistema_de_gestion_reservas.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
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

    public List<Reserva> obtenerReservasAsistidas(int anio) {
        return reservas.stream()
            .filter(reserva ->
                reserva.getEstadoReserva().equals(EstadoReserva.ASISTIDO)
                    && reserva.getFechaCreacion().getYear() == anio).toList();
    }
}
