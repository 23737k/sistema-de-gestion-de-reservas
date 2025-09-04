package com.kenti.antezana.sistema_de_gestion_de_reservas.controller;

import com.kenti.antezana.sistema_de_gestion_de_reservas.dto.request.EventoReq;
import com.kenti.antezana.sistema_de_gestion_de_reservas.dto.request.FuncionReq;
import com.kenti.antezana.sistema_de_gestion_de_reservas.dto.response.EventoRes;
import com.kenti.antezana.sistema_de_gestion_de_reservas.dto.response.FuncionRes;
import com.kenti.antezana.sistema_de_gestion_de_reservas.service.EventoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
@SecurityRequirement(name = "bearer-jwt")
public class EventoController {
    private final EventoService eventoService;

    @GetMapping
    @Operation(
        summary = "Obtiene la lista de todos los eventos",
        description = "Retorna todos los eventos registrados en el sistema, incluyendo su disponibilidad y tipo de evento.",
        tags = {"Eventos"}
    )
    public ResponseEntity<List<EventoRes>> obtenerEventos() {
        return ResponseEntity.ok(eventoService.obtenerEventos());
    }

    @GetMapping("/{eventoId}")
    @Operation(
        summary = "Obtiene los detalles de un evento específico",
        description = "Retorna la información completa de un evento identificado por su ID, incluyendo nombre, descripción y tipo de evento.",
        tags = {"Eventos"}
    )
    public ResponseEntity<EventoRes> obtenerEventoPorId(@PathVariable("eventoId") Long eventoId) {
        return ResponseEntity.ok(eventoService.obtenerEventoPorId(eventoId));
    }

    @PostMapping
    @Operation(
        summary = "Crea un nuevo evento",
        description = "Registra un nuevo evento en el sistema con los datos proporcionados en el request, incluyendo nombre, descripción y tipo de evento.",
        tags = {"Eventos"}
    )
    public ResponseEntity<EventoRes> crearEvento(@RequestBody @Valid EventoReq evento) {
        EventoRes eventoRes = eventoService.crearEvento(evento);
        return ResponseEntity.created(URI.create("/api/eventos/" + eventoRes.id().toString()))
            .body(eventoRes);
    }

    @PutMapping("/{eventoId}")
    @Operation(
        summary = "Modifica un evento existente",
        description = "Actualiza los datos de un evento identificado por su ID, como nombre, descripción o tipo de evento.",
        tags = {"Eventos"}
    )
    public ResponseEntity<EventoRes> modificarEvento(@PathVariable("eventoId") Long eventoId,
                                                     @Valid @RequestBody EventoReq evento) {
        return ResponseEntity.accepted().body(eventoService.modificarEvento(eventoId, evento));
    }

    @DeleteMapping("/{eventoId}")
    @Operation(
        summary = "Elimina un evento",
        description = "Elimina un evento del sistema usando su ID y todas las funciones asociadas a él.",
        tags = {"Eventos"}
    )
    public ResponseEntity<Void> eliminarEvento(@PathVariable("eventoId") Long eventoId) {
        eventoService.eliminarEvento(eventoId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{eventoId}/funciones")
    @Operation(
        summary = "Obtiene la lista de funciones de un evento",
        description = "Retorna todas las funciones asociadas a un evento específico, incluyendo fecha, hora y disponibilidad de entradas.",
        tags = {"Funciones"}
    )
    public ResponseEntity<List<FuncionRes>> obtenerFunciones(
        @PathVariable("eventoId") Long eventoId) {
        return ResponseEntity.ok(eventoService.obtenerFunciones(eventoId));
    }

    @GetMapping("/{eventoId}/funciones/{funcionId}")
    @Operation(
        summary = "Obtiene los detalles de una función específica de un evento",
        description = "Retorna la información completa de una función de un evento, incluyendo fecha, hora, ubicación y entradas disponibles.",
        tags = {"Funciones"}
    )
    public ResponseEntity<FuncionRes> obtenerFuncion(@PathVariable("eventoId") Long eventoId,
                                                     @PathVariable("funcionId") Long funcionId) {
        return ResponseEntity.ok(eventoService.obtenerFuncion(eventoId, funcionId));
    }

    @PostMapping("/{eventoId}/funciones")
    @Operation(
        summary = "Crea una nueva función para un evento",
        description = "Agrega una función a un evento existente, indicando fecha, hora y capacidad de entradas.",
        tags = {"Funciones"}
    )
    public ResponseEntity<FuncionRes> crearFuncion(@PathVariable("eventoId") Long eventoId,
                                                   @Valid @RequestBody FuncionReq funcionReq) {
        FuncionRes funcionRes = eventoService.agregarFuncion(eventoId, funcionReq);
        return ResponseEntity.created(
                URI.create("/api/v1/" + "/eventos/" + eventoId + "/funciones/" + funcionRes.id()))
            .body(funcionRes);
    }

    @PutMapping("/{eventoId}/funciones/{funcionId}")
    @Operation(
        summary = "Modifica una función de un evento",
        description = "Actualiza los datos de una función de un evento, como fecha, hora o capacidad de entradas.",
        tags = {"Funciones"}
    )
    public ResponseEntity<FuncionRes> modificarFuncion(@PathVariable("eventoId") Long eventoId,
                                                       @PathVariable("funcionId") Long funcionId,
                                                       @Valid @RequestBody FuncionReq funcionReq) {
        return ResponseEntity.accepted()
            .body(eventoService.actualizarFuncion(eventoId, funcionId, funcionReq));
    }

    @DeleteMapping("/{eventoId}/funciones/{funcionId}")
    @Operation(
        summary = "Elimina una función de un evento",
        description = "Elimina una función específica de un evento, liberando todas las entradas asociadas.",
        tags = {"Funciones"}
    )
    public ResponseEntity<Void> eliminarFuncion(@PathVariable("eventoId") Long eventoId,
                                                @PathVariable("funcionId") Long funcionId) {
        eventoService.eliminarFuncion(eventoId, funcionId);
        return ResponseEntity.noContent().build();
    }


}


