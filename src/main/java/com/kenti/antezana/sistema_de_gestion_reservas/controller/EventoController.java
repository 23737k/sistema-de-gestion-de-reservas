package com.kenti.antezana.sistema_de_gestion_reservas.controller;

import com.kenti.antezana.sistema_de_gestion_reservas.dto.request.EventoReq;
import com.kenti.antezana.sistema_de_gestion_reservas.dto.response.EventoRes;
import com.kenti.antezana.sistema_de_gestion_reservas.service.EventoService;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${backend.api.base-path}/eventos")
@RequiredArgsConstructor
public class EventoController {
    private final EventoService eventoService;

    @GetMapping
    public ResponseEntity<List<EventoRes>> obtenerEventos() {
        return ResponseEntity.ok(eventoService.obtenerEventos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventoRes> obtenerEventoPorId(@PathVariable Long id) {
        return ResponseEntity.ok(eventoService.obtenerEventoPorId(id));
    }

    @PostMapping
    public ResponseEntity<EventoRes> crearEvento(@RequestBody @Valid EventoReq evento) {
        EventoRes eventoRes = eventoService.crearEvento(evento);
        return ResponseEntity.created(URI.create("/api/eventos/" + eventoRes.id().toString()))
            .body(eventoRes);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventoRes> modificarEvento(@PathVariable Long id,
                                                     @RequestBody EventoReq evento) {
        return ResponseEntity.accepted().body(eventoService.modificarEvento(id, evento));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<EventoRes> eliminarEvento(@PathVariable Long id) {
        eventoService.eliminarEvento(id);
        return ResponseEntity.noContent().build();
    }

}


