package com.kenti.antezana.sistema_de_gestion_de_reservas.repository;

import com.kenti.antezana.sistema_de_gestion_de_reservas.model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservaRepo extends JpaRepository<Reserva, Long> {
}
