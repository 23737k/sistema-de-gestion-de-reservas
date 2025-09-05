package com.kenti.antezana.sistema_de_gestion_de_reservas.dto.request;

import com.kenti.antezana.sistema_de_gestion_de_reservas.model.TipoDeDocumento;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ClienteReq(
    @Schema(description = "Nombre del cliente", example = "Juan")
    @NotBlank(message = "El nombre no puede estar vacío")
    String nombre,

    @Schema(description = "Apellido del cliente", example = "Pérez")
    @NotBlank(message = "El apellido no puede estar vacío")
    String apellido,

    @Schema(description = "Email del cliente", example = "juan.perez@email.com")
    @NotBlank(message = "El email no puede estar vacío")
    @Email(message = "email con formato invalido")
    String email,

    @Schema(description = "Teléfono del cliente", example = "+54 9 11 1234-5678")
    @NotBlank(message = "El telefono no puede estar vacío")
    String telefono,

    @Schema(description = "Tipo de documento del cliente", example = "DNI")
    @NotNull(message = "El tipo de documento no puede estar vacío")
    TipoDeDocumento tipoDeDocumento,

    @Schema(description = "Número de documento del cliente", example = "12345678")
    @NotBlank(message = "El documento no puede estar vacío")
    String documento
) {
}
