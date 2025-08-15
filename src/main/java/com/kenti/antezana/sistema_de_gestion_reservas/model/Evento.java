package com.kenti.antezana.sistema_de_gestion_reservas.model;

import com.kenti.antezana.sistema_de_gestion_reservas.exception.TipoDeEntradaInvalidaException;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import java.util.List;
import lombok.Data;

@Entity
@Data
public class Evento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String descripcion;
    private TipoDeEvento tipoDeEvento;
    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "eventoId")
    private List<Funcion> funciones;

    public void agregarFuncion(Funcion funcion) {
        List<Disponibilidad> disponibilidades = funcion.getDisponibilidades();

        disponibilidades.stream()
            .filter(d -> !tipoDeEvento.contieneTipoDeEntrada(d.getTipoDeEntrada()))
            .findFirst()
            .ifPresent(d -> {
                throw new TipoDeEntradaInvalidaException(tipoDeEvento, d.getTipoDeEntrada());
            });
        funciones.add(funcion);
    }
}
