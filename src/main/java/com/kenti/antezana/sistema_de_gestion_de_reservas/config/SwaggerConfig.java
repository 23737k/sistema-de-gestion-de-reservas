package com.kenti.antezana.sistema_de_gestion_de_reservas.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

import static io.swagger.v3.oas.annotations.enums.SecuritySchemeType.HTTP;

@OpenAPIDefinition(info = @Info(
    title = "Sistema de Gestión de Reservas",
    description = "Sistema encargado de administrar de forma digital y automatizada todas las reservas de entradas" +
        " para eventos organizados por el Teatro Gran Espectáculo, incluyendo obras de teatro, recitales charlas " +
        "y conferencias. ",
    version = "1.0.0"
))
@SecurityScheme(
        type = HTTP,
        name = "bearer-jwt",
        in = SecuritySchemeIn.HEADER,
        scheme = "bearer",
        bearerFormat = "JWT"
)
public class SwaggerConfig {
}
