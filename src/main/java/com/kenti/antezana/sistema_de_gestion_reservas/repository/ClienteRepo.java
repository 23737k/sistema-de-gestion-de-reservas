package com.kenti.antezana.sistema_de_gestion_reservas.repository;

import com.kenti.antezana.sistema_de_gestion_reservas.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepo extends JpaRepository<Cliente, Long> {
}
