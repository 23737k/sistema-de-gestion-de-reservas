package com.kenti.antezana.sistema_de_gestion_de_reservas.security.dto;

import com.kenti.antezana.sistema_de_gestion_de_reservas.model.TipoDeDocumento;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegisterReq (
    @NotBlank
    @Schema(example = "John")
    String nombre,

    @NotBlank
    @Schema(example = "Doe")
    String apellido,

    @Schema(description = "Tipo de documento del cliente", example = "DNI")
    @NotNull(message = "El tipo de documento no puede estar vacío")
    TipoDeDocumento tipoDeDocumento,

    @Schema(description = "Número de documento del cliente", example = "12345678")
    @NotBlank(message = "El documento no puede estar vacío")
    String documento,

    @NotBlank
    @Schema(example = "123456789")
    String telefono,

    @Email
    @NotBlank
    @Schema(example = "john-doe@gmail.com")
    String email,

    @NotBlank
    @Schema(example = "P@ssw0rd!")
    String password
){}