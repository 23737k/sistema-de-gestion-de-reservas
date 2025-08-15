package com.kenti.antezana.sistema_de_gestion_reservas.controller;

import com.kenti.antezana.sistema_de_gestion_reservas.dto.request.EventoReq;
import com.kenti.antezana.sistema_de_gestion_reservas.dto.request.FuncionReq;
import com.kenti.antezana.sistema_de_gestion_reservas.dto.response.EventoRes;
import com.kenti.antezana.sistema_de_gestion_reservas.dto.response.FuncionRes;
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

    @GetMapping("/{eventoId}")
    public ResponseEntity<EventoRes> obtenerEventoPorId(@PathVariable("eventoId") Long eventoId) {
        return ResponseEntity.ok(eventoService.obtenerEventoPorId(eventoId));
    }

    @PostMapping
    public ResponseEntity<EventoRes> crearEvento(@RequestBody @Valid EventoReq evento) {
        EventoRes eventoRes = eventoService.crearEvento(evento);
        return ResponseEntity.created(URI.create("/api/eventos/" + eventoRes.id().toString()))
            .body(eventoRes);
    }

    @PutMapping("/{eventoId}")
    public ResponseEntity<EventoRes> modificarEvento(@PathVariable("eventoId") Long eventoId,
                                                     @Valid @RequestBody EventoReq evento) {
        return ResponseEntity.accepted().body(eventoService.modificarEvento(eventoId, evento));
    }

    @DeleteMapping("/{eventoId}")
    public ResponseEntity<Void> eliminarEvento(@PathVariable("eventoId") Long eventoId) {
        eventoService.eliminarEvento(eventoId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{eventoId}/funciones")
    public ResponseEntity<List<FuncionRes>> obtenerFunciones(
        @PathVariable("eventoId") Long eventoId) {
        return ResponseEntity.ok(eventoService.obtenerFunciones(eventoId));
    }

    @GetMapping("/{eventoId}/funciones/{funcionId}")
    public ResponseEntity<FuncionRes> obtenerFuncion(@PathVariable("eventoId") Long eventoId,
                                                     @PathVariable("funcionId") Long funcionId) {
        return ResponseEntity.ok(eventoService.obtenerFuncion(eventoId, funcionId));
    }

    @PostMapping("/{eventoId}/funciones")
    public ResponseEntity<FuncionRes> crearFuncion(@PathVariable("eventoId") Long eventoId,
                                                   @Valid @RequestBody FuncionReq funcionReq) {
        FuncionRes funcionRes = eventoService.agregarFuncion(eventoId, funcionReq);
        return ResponseEntity.created(
                URI.create("/api/v1/" + "/eventos/" + eventoId + "/funciones/" + funcionRes.id()))
            .body(funcionRes);
    }

    @PutMapping("/{eventoId}/funciones/{funcionId}")
    public ResponseEntity<FuncionRes> modificarFuncion(@PathVariable("eventoId") Long eventoId,
                                                       @PathVariable("funcionId") Long funcionId,
                                                       @Valid @RequestBody FuncionReq funcionReq) {
        return ResponseEntity.accepted()
            .body(eventoService.actualizarFuncion(eventoId, funcionId, funcionReq));
    }

    @DeleteMapping("/{eventoId}/funciones/{funcionId}")
    public ResponseEntity<Void> eliminarFuncion(@PathVariable("eventoId") Long eventoId,
                                                @PathVariable("funcionId") Long funcionId) {
        eventoService.eliminarFuncion(eventoId, funcionId);
        return ResponseEntity.noContent().build();
    }


}


