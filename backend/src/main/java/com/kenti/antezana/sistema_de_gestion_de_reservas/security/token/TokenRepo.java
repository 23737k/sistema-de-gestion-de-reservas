package com.kenti.antezana.sistema_de_gestion_de_reservas.security.token;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface TokenRepo extends JpaRepository<Token, Long> {
    Optional<Token> findByToken(String token);
}
