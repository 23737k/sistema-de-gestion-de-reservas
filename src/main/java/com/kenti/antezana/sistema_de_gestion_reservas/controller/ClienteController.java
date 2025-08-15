package com.kenti.antezana.sistema_de_gestion_reservas.controller;

import com.kenti.antezana.sistema_de_gestion_reservas.dto.request.ClienteReq;
import com.kenti.antezana.sistema_de_gestion_reservas.dto.request.EstadoReservaReq;
import com.kenti.antezana.sistema_de_gestion_reservas.dto.request.ReservaReq;
import com.kenti.antezana.sistema_de_gestion_reservas.dto.response.ClienteRes;
import com.kenti.antezana.sistema_de_gestion_reservas.dto.response.ReservaRes;
import com.kenti.antezana.sistema_de_gestion_reservas.service.ClienteService;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("${backend.api.base-path}/clientes")
public class ClienteController {
    private final ClienteService clienteService;

    @GetMapping
    public ResponseEntity<List<ClienteRes>> obtenerClientes() {
        return ResponseEntity.ok(clienteService.obtenerClientes());
    }

    @GetMapping("/{clienteId}")
    public ResponseEntity<ClienteRes> obtenerCliente(@PathVariable Long clienteId) {
        return ResponseEntity.ok(clienteService.obtenerCliente(clienteId));
    }

    @PostMapping
    public ResponseEntity<ClienteRes> crearCliente(@RequestBody @Valid ClienteReq clienteReq) {
        ClienteRes clienteRes = clienteService.crearCliente(clienteReq);
        return ResponseEntity.created(URI.create("/api/v1/clientes/" + clienteRes.id()))
            .body(clienteRes);
    }

    @PutMapping("/{clienteId}")
    public ResponseEntity<ClienteRes> actualizarCliente(@RequestBody @Valid ClienteReq clienteReq,
                                                        @PathVariable Long clienteId) {
        return ResponseEntity.accepted()
            .body(clienteService.actualizarCliente(clienteId, clienteReq));
    }

    @DeleteMapping("/{clienteId}")
    public ResponseEntity<Void> eliminarCliente(@PathVariable Long clienteId) {
        clienteService.eliminarCliente(clienteId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{clienteId}/reservas/{reservaId}")
    public ResponseEntity<ReservaRes> obtenerReserva(@PathVariable Long clienteId,
                                                     @PathVariable Long reservaId) {
        return ResponseEntity.ok(clienteService.obtenerReserva(clienteId, reservaId));
    }

    @PostMapping("/{clienteId}/reservas")
    public ResponseEntity<ReservaRes> crearReserva(@PathVariable Long clienteId,
                                                   @RequestBody @Valid ReservaReq reservaReq) {
        ReservaRes reserva = clienteService.crearReserva(clienteId, reservaReq);
        return ResponseEntity.created(
                URI.create("/api/v1/clientes/" + clienteId + "/reservas/" + reserva.id()))
            .body(reserva);
    }

    @PatchMapping("/{clienteId}/reservas/{reservaId}/estado")
    public ResponseEntity<ReservaRes> cambiarEstadoReserva(@PathVariable Long clienteId,
                                                           @PathVariable Long reservaId,
                                                           @RequestBody @Valid
                                                           EstadoReservaReq estadoReservaReq) {
        ReservaRes reserva =
            clienteService.modificarEstadoReserva(clienteId, reservaId, estadoReservaReq);
        return ResponseEntity.accepted().body(reserva);
    }


}
