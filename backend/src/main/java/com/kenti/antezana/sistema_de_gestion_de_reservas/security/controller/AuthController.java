package com.kenti.antezana.sistema_de_gestion_de_reservas.security.controller;

import com.kenti.antezana.sistema_de_gestion_de_reservas.security.dto.AuthReq;
import com.kenti.antezana.sistema_de_gestion_de_reservas.security.dto.AuthRes;
import com.kenti.antezana.sistema_de_gestion_de_reservas.security.dto.RegisterReq;
import com.kenti.antezana.sistema_de_gestion_de_reservas.security.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("${backend.api.base-path}/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthRes> login(@RequestBody @Valid AuthReq authReq) {
        return ResponseEntity.ok(authService.authenticate(authReq));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthRes> register(@RequestBody @Valid RegisterReq registerReq) {
        return ResponseEntity.ok(authService.register(registerReq));
    }

}
