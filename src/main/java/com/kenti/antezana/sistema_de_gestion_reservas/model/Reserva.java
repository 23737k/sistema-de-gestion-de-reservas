package com.kenti.antezana.sistema_de_gestion_reservas.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Reserva {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @ManyToOne
  @JoinColumn(name = "funcion_id")
  private Funcion funcion;
  private TipoDeEntrada tipoDeEntrada;
  @ManyToOne
  @JoinColumn(name = "cliente_id")
  private Cliente cliente;
  private EstadoReserva estadoReserva;
  private Boolean bonificado;

}
