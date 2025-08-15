package com.kenti.antezana.sistema_de_gestion_reservas.dto.request;

import com.kenti.antezana.sistema_de_gestion_reservas.model.TipoDeDocumento;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ClienteReq(
    @NotBlank(message = "El nombre no puede estar vacío")
    String nombre,
    @NotBlank(message = "El apellido no puede estar vacío")
    String apellido,
    @NotBlank(message = "El email no puede estar vacío")
    @Email(message = "email con formato invalido")
    String email,
    @NotBlank(message = "El telefono no puede estar vacío")
    String telefono,
    @NotNull(message = "El tipo de documento no puede estar vacío")
    TipoDeDocumento tipoDeDocumento,
    @NotBlank(message = "El documento no puede estar vacío")
    String documento
) {
}
