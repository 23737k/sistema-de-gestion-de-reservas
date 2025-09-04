package com.kenti.antezana.sistema_de_gestion_de_reservas.security.service;

import com.kenti.antezana.sistema_de_gestion_de_reservas.exception.UserAlreadyExistsException;
import com.kenti.antezana.sistema_de_gestion_de_reservas.model.User;
import com.kenti.antezana.sistema_de_gestion_de_reservas.security.dto.AuthReq;
import com.kenti.antezana.sistema_de_gestion_de_reservas.security.dto.AuthRes;
import com.kenti.antezana.sistema_de_gestion_de_reservas.security.dto.RegisterReq;
import com.kenti.antezana.sistema_de_gestion_de_reservas.security.jwt.JwtService;
import com.kenti.antezana.sistema_de_gestion_de_reservas.security.token.Token;
import com.kenti.antezana.sistema_de_gestion_de_reservas.security.token.TokenService;
import com.kenti.antezana.sistema_de_gestion_de_reservas.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtService jwtService;
    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    @Transactional
    public AuthRes authenticate(AuthReq authReq) {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(authReq.getEmail(),authReq.getPassword());
        UserDetails userDetails = (UserDetails) authenticationManager.authenticate(authentication).getPrincipal();

        Token token = new Token(jwtService.getAccessToken(userDetails));
        tokenService.save(token);
        return new AuthRes(token.getToken());
    }

    @Transactional
    public AuthRes register(RegisterReq req) {
        if(userService.userExists(req.email()))
            throw new UserAlreadyExistsException("This email is already registered");

        User user = userService.crearUsuario(req);

        Token token = new Token(jwtService.getAccessToken(user));
        tokenService.save(token);
        return new AuthRes(token.getToken());

    }
}
