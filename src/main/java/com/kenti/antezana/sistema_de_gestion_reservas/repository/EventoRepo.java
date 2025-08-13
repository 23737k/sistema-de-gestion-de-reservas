package com.kenti.antezana.sistema_de_gestion_reservas.repository;

import com.kenti.antezana.sistema_de_gestion_reservas.model.Evento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventoRepo extends JpaRepository<Evento, Long> {
}
