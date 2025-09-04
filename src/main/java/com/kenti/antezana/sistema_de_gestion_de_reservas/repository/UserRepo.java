package com.kenti.antezana.sistema_de_gestion_de_reservas.repository;

import com.kenti.antezana.sistema_de_gestion_de_reservas.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);
}
