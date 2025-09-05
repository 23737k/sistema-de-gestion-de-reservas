package com.kenti.antezana.sistema_de_gestion_de_reservas.model;

import com.kenti.antezana.sistema_de_gestion_de_reservas.exception.DisponibilidadAgotadaException;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Disponibilidad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private TipoDeEntrada tipoDeEntrada;
    private BigDecimal precio;
    private int cuposTotales;
    private int cuposOcupados;

    public int cuposDisponibles() {
        return Math.max(this.cuposTotales - this.cuposOcupados, 0);
    }

    public void reservarCupo() {
        if (cuposDisponibles() > 0) {
            this.cuposOcupados++;
        } else {
            throw new DisponibilidadAgotadaException(
                "Error al reservar cupo. La disponibilidad se encuentra agotada.");
        }
    }

    public void devolverCupo() {
        if (this.cuposOcupados > 0)
            this.cuposOcupados --;
    }
}
