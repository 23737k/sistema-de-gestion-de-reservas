package com.kenti.antezana.sistema_de_gestion_de_reservas.security.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthReq {
    @NotBlank
    @Schema(example = "john-doe@gmail.com")
    private String email;
    @NotBlank
    @Schema(example = "P@ssw0rd!")
    private String password;

}