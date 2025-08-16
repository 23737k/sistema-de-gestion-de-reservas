package com.kenti.antezana.sistema_de_gestion_reservas.service;

import com.kenti.antezana.sistema_de_gestion_reservas.model.Cliente;
import com.kenti.antezana.sistema_de_gestion_reservas.model.PaseGratis;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
public class PaseGratisService {

    public Cliente calcularPaseGratis(Cliente cliente) {
        if (calificaParaPaseGratis(cliente)) {
            PaseGratis paseGratis = PaseGratis.builder()
                .fechaDeOtorgamiento(LocalDate.now())
                .usado(false)
                .build();
            cliente.getPasesGratis().add(paseGratis);
        }
        return cliente;
    }

    public boolean calificaParaPaseGratis(Cliente cliente) {
        boolean noTienePaseEsteAnio = cliente.getPasesGratis().stream()
            .noneMatch(p -> p.getFechaDeOtorgamiento().getYear() == LocalDate.now().getYear());

        boolean tieneMasDeCincoEventosAsistidos =
            cliente.obtenerReservasAsistidas(LocalDate.now().getYear()).size() >= 5;

        return tieneMasDeCincoEventosAsistidos && noTienePaseEsteAnio;
    }

}
