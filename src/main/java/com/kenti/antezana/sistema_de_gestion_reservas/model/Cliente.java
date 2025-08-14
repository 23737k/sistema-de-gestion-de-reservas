package com.kenti.antezana.sistema_de_gestion_reservas.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.List;
import lombok.Data;

@Entity
@Data
public class Cliente {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String nombre;
  private String apellido;
  private String email;
  private String telefono;
  @Enumerated(EnumType.STRING)
  private TipoDeDocumento tipoDocumento;
  private String documento;
  @OneToMany(mappedBy = "cliente", orphanRemoval = true)
  private List<Reserva> reservas;
}
