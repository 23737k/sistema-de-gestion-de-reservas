package com.kenti.antezana.sistema_de_gestion_de_reservas.security.config;

import com.kenti.antezana.sistema_de_gestion_de_reservas.exception.TokenNotFoundException;
import com.kenti.antezana.sistema_de_gestion_de_reservas.security.token.TokenService;
import com.kenti.antezana.sistema_de_gestion_de_reservas.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import java.io.IOException;

@Configuration
@RequiredArgsConstructor
public class AppConfig {
    @Bean
    public String[] whiteListedUrls(){
        return new String[]{
                "/api/v1/auth/login",
                "/api/v1/auth/register",
                "/api/v1/auth/renew-token",
                "/api/v1/keepAlive",
                "/v2/api-docs",
                "/v3/api-docs",
                "/v3/api-docs/**",
                "/swagger-resources",
                "/swagger-resources/**",
                "/configuration/ui",
                "/configuration/security",
                "/swagger-ui/**",
                "/swagger-ui/*",
                "/webjars/**",
                "/swagger-ui.html"
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(UserService userService){
        return userService::findUserByEmail;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }


    @Bean
    public LogoutHandler logoutHandler(TokenService tokenService) throws IOException {
        return (request, response, authentication) -> {
            try {
                String header = request.getHeader("Authorization");
                if(header != null && header.startsWith("Bearer ")) {
                    String token = header.substring(7);
                    tokenService.revokeToken(token);
                }
                else throw new TokenNotFoundException("Token is null");
            }
            catch (Exception e){
                try {
                    response.getWriter().write("Access denied");
                    response.setStatus(401);
                    response.setContentType("application/json");
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        };
    }
}
