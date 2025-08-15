package com.kenti.antezana.sistema_de_gestion_reservas.service;

import com.kenti.antezana.sistema_de_gestion_reservas.model.Cliente;
import com.kenti.antezana.sistema_de_gestion_reservas.model.PaseGratis;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaseGratisService {

    public void calcularPaseGratis(Cliente cliente) {
        if (calificaParaPaseGratis(cliente)) {
            PaseGratis paseGratis = PaseGratis.builder()
                .fechaDeOtorgamiento(LocalDate.now())
                .usado(false)
                .build();
            cliente.getPasesGratis().add(paseGratis);
        }
    }

    public boolean calificaParaPaseGratis(Cliente cliente) {
        boolean tienePasesGratisEsteAnio = cliente.getPasesGratis().stream()
            .anyMatch(p -> !p.isUsado() &&
                p.getFechaDeOtorgamiento().getYear() != LocalDate.now().getYear());

        boolean tieneMasDeCincoEventosAsistidos =
            (long) cliente.obtenerReservasAsistidas(LocalDate.now().getYear()).size() >= 5;

        return tieneMasDeCincoEventosAsistidos && tienePasesGratisEsteAnio;
    }

}
