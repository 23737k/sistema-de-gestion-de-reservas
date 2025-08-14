package com.kenti.antezana.sistema_de_gestion_reservas.controller;

import com.kenti.antezana.sistema_de_gestion_reservas.dto.request.EventoReq;
import com.kenti.antezana.sistema_de_gestion_reservas.dto.response.EventoRes;
import com.kenti.antezana.sistema_de_gestion_reservas.model.Evento;
import com.kenti.antezana.sistema_de_gestion_reservas.repository.EventoRepo;
import com.kenti.antezana.sistema_de_gestion_reservas.service.EventoService;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping ("/api/eventos")
@RequiredArgsConstructor
public class EventoController {
    private final EventoService eventoService;

    @GetMapping
    public ResponseEntity<List<EventoRes>> obtenerEventos(){
      return ResponseEntity.ok(eventoService.obtenerEventos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventoRes> obtenerEventoPorId(@PathVariable Long id) {
        return ResponseEntity.ok(eventoService.obtenerEventoPorId(id));
    }

    @PostMapping
    public ResponseEntity<EventoRes> create(@RequestBody EventoReq evento) {
        EventoRes eventoRes = eventoService.crearEvento(evento);
        return ResponseEntity.created(URI.create("/api/eventos/"+ eventoRes.id().toString())).body(eventoRes);
    }

}


