package com.kenti.antezana.sistema_de_gestion_de_reservas.repository;

import com.kenti.antezana.sistema_de_gestion_de_reservas.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
