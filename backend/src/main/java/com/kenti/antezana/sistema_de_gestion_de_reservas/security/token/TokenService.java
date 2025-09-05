package com.kenti.antezana.sistema_de_gestion_de_reservas.security.token;

import com.kenti.antezana.sistema_de_gestion_de_reservas.exception.TokenNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenService {
    private final TokenRepo tokenRepo;

    public void revokeToken(String token) {
        tokenRepo.findByToken(token).ifPresentOrElse(tokenRepo::delete,() -> {throw new TokenNotFoundException("Token not found");});
    }

    public boolean exists(String token) {
        return tokenRepo.findByToken(token).isPresent();
    }

    public Token save(Token token) {
        return tokenRepo.save(token);
    }
}
