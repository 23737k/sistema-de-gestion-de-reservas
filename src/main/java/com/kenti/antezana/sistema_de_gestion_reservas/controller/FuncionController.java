package com.kenti.antezana.sistema_de_gestion_reservas.controller;

import com.kenti.antezana.sistema_de_gestion_reservas.dto.request.FuncionReq;
import com.kenti.antezana.sistema_de_gestion_reservas.dto.response.FuncionRes;
import com.kenti.antezana.sistema_de_gestion_reservas.service.FuncionService;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("${backend.api.base-path}/eventos/{eventoId}/funciones")
public class    FuncionController {
    private final FuncionService funcionService;

    @GetMapping
    public ResponseEntity<List<FuncionRes>> obtenerFunciones(
        @PathVariable("eventoId") Long eventoId) {
        return ResponseEntity.ok(funcionService.obtenerFunciones(eventoId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FuncionRes> obtenerFuncion(@PathVariable("eventoId") Long eventoId,
                                                     @PathVariable("id") Long id) {
        return ResponseEntity.ok(funcionService.obtenerFuncion(eventoId, id));
    }

    @PostMapping
    public ResponseEntity<FuncionRes> crearFuncion(@PathVariable("eventoId") Long eventoId,
                                                   @RequestBody FuncionReq funcionReq) {
        FuncionRes funcionRes = funcionService.crearFuncion(eventoId, funcionReq);
        return ResponseEntity.created(
                URI.create("/api/v1/" + "/eventos/" + eventoId + "/funciones/" + funcionRes.id()))
            .body(funcionRes);
    }


}
