package com.kenti.antezana.sistema_de_gestion_reservas.controller;

import com.kenti.antezana.sistema_de_gestion_reservas.model.Evento;
import com.kenti.antezana.sistema_de_gestion_reservas.repository.EventoRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping ("/api/eventos")
@RequiredArgsConstructor
public class EventoController {
    private final EventoRepo eventoRepo;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Evento findById(@PathVariable Long id) {
        return eventoRepo.findById(id).orElse(null);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Evento create(@RequestBody Evento evento) {
        return eventoRepo.save(evento);
    }

}


