package com.kenti.antezana.sistema_de_gestion_de_reservas.config;

import com.kenti.antezana.sistema_de_gestion_de_reservas.model.Rol;
import com.kenti.antezana.sistema_de_gestion_de_reservas.model.Usuario;
import com.kenti.antezana.sistema_de_gestion_de_reservas.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InitConfig {
    @Value("${ADMIN_EMAIL}")
    private String adminEmail;

    @Value("${ADMIN_PASSWORD}")
    private String adminPassword;

    @Bean
    CommandLineRunner commandLineRunner(UserService userService) {
        return args -> {
            if (!userService.userExists(adminEmail)) {
                Usuario usuario = Usuario.builder()
                        .email(adminEmail).
                        password(adminPassword)
                        .rol(Rol.ADMIN)
                        .build();
                userService.crearUsuarioAdmin(usuario);
            }

        };
    }
}
