package com.kenti.antezana.sistema_de_gestion_de_reservas.repository;

import com.kenti.antezana.sistema_de_gestion_de_reservas.model.Funcion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FuncionRepo extends JpaRepository<Funcion, Long> {
}
