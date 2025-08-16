package com.kenti.antezana.sistema_de_gestion_reservas.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate fechaCreacion = LocalDate.now();
    @ManyToOne
    @JoinColumn(name = "funcion_id")
    private Funcion funcion;
    @Enumerated(EnumType.STRING)
    private TipoDeEntrada tipoDeEntrada;
    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;
    @Enumerated(EnumType.STRING)
    private EstadoReserva estadoReserva;
    private BigDecimal montoTotal = BigDecimal.ZERO;
    private boolean bonificado = false;
}
