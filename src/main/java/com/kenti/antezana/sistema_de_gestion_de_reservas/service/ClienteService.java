package com.kenti.antezana.sistema_de_gestion_de_reservas.service;

import com.kenti.antezana.sistema_de_gestion_de_reservas.dto.mapper.ClienteMapper;
import com.kenti.antezana.sistema_de_gestion_de_reservas.dto.mapper.ReservaMapper;
import com.kenti.antezana.sistema_de_gestion_de_reservas.dto.request.ClienteReq;
import com.kenti.antezana.sistema_de_gestion_de_reservas.dto.request.EstadoReservaReq;
import com.kenti.antezana.sistema_de_gestion_de_reservas.dto.request.ReservaReq;
import com.kenti.antezana.sistema_de_gestion_de_reservas.dto.response.ClienteRes;
import com.kenti.antezana.sistema_de_gestion_de_reservas.dto.response.ReservaRes;
import com.kenti.antezana.sistema_de_gestion_de_reservas.exception.CambioDeEstadoInvalidoException;
import com.kenti.antezana.sistema_de_gestion_de_reservas.model.*;
import com.kenti.antezana.sistema_de_gestion_de_reservas.repository.ClienteRepo;
import com.kenti.antezana.sistema_de_gestion_de_reservas.repository.ReservaRepo;
import com.kenti.antezana.sistema_de_gestion_de_reservas.security.dto.RegisterReq;
import jakarta.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ClienteService {
    private final ClienteRepo clienteRepo;
    private final ClienteMapper clienteMapper;
    private final ReservaRepo reservaRepo;
    private final ReservaMapper reservaMapper;
    private final EventoService eventoService;
    private final PaseGratisService paseGratisService;

    public List<ClienteRes> obtenerClientes() {
        List<Cliente> clientes = clienteRepo.findAll();
        return clientes.stream().map(clienteMapper::toRes).toList();
    }

    public ClienteRes obtenerCliente(Long id) {
        Cliente cliente = clienteRepo.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Cliente no encontrado"));
        return clienteMapper.toRes(cliente);
    }

    public ClienteRes crearCliente(ClienteReq clienteReq) {
        Cliente cliente = clienteMapper.toEntity(clienteReq);
        cliente = clienteRepo.save(cliente);
        return clienteMapper.toRes(cliente);
    }

    public ClienteRes actualizarCliente(Long id, ClienteReq clienteReq) {
        Cliente cliente = clienteRepo.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Cliente no encontrado"));
        clienteMapper.updateEntity(clienteReq, cliente);
        return clienteMapper.toRes(clienteRepo.save(cliente));
    }

    public void eliminarCliente(Long id) {
        if (clienteRepo.existsById(id)) {
            clienteRepo.deleteById(id);
        } else {
            throw new EntityNotFoundException("Cliente no encontrado");
        }
    }


    public Cliente encontrarCliente(Long id) {
        return clienteRepo.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Cliente no encontrado"));
    }

    public List<ReservaRes> obtenerReservas(Long clienteId) {
        List<Reserva> reservas = encontrarCliente(clienteId).getReservas();
        return reservas.stream().map(reservaMapper::toRes).toList();
    }

    public ReservaRes obtenerReserva(Long clienteId, Long reservaId) {
        Reserva reserva = encontrarCliente(clienteId).getReservas().stream()
            .filter(r -> r.getId().equals(reservaId))
            .findFirst()
            .orElseThrow(() -> new EntityNotFoundException("Reserva no encontrada"));
        return reservaMapper.toRes(reserva);
    }


    public ReservaRes crearReserva(Long clienteId, ReservaReq reservaReq) {
        Cliente cliente = encontrarCliente(clienteId);
        Funcion funcion = eventoService.encontrarFuncionPorId(reservaReq.funcionId());
        Disponibilidad disponibilidad = funcion.encontrarDisponibilidad(reservaReq.tipoDeEntrada());
        disponibilidad.reservarCupo();

        Reserva reserva = Reserva.builder()
            .fechaCreacion(LocalDate.now())
            .funcion(funcion)
            .tipoDeEntrada(reservaReq.tipoDeEntrada())
            .cliente(cliente)
            .estadoReserva(EstadoReserva.RESERVADO)
            .montoTotal(disponibilidad.getPrecio())
            .build();
        verificarPasesGratis(reserva);

        return reservaMapper.toRes(reservaRepo.save(reserva));
    }

    public void verificarPasesGratis(Reserva reserva) {
        Cliente cliente = reserva.getCliente();
        List<PaseGratis> pasesGratis = cliente.getPasesGratis();
        Optional<PaseGratis> paseGratis = pasesGratis.stream().filter(p -> !p.isUsado()).findAny();
        if (paseGratis.isPresent()) {
            paseGratis.get().setUsado(true);
            reserva.setMontoTotal(BigDecimal.ZERO);
            reserva.setEstadoReserva(EstadoReserva.PAGADO);
            reserva.setBonificado(true);
        }
    }

    public ReservaRes modificarEstadoReserva(Long clienteId, Long reservaId,
                                             EstadoReservaReq estadoReservaReq) {
        EstadoReserva nuevoEstado = estadoReservaReq.estadoReserva();
        Reserva reserva = reservaRepo.findById(reservaId)
            .orElseThrow(() -> new EntityNotFoundException("Reserva no encontrada"));

        if (!reserva.getEstadoReserva().cambioDeEstadoValido(nuevoEstado)) {
            throw new CambioDeEstadoInvalidoException(
                "El cambio de estado a " + nuevoEstado.name()
                    + " no es valido con el estado actual "
                    + reserva.getEstadoReserva().name());
        }

        reserva.setEstadoReserva(nuevoEstado);
        if (nuevoEstado.equals(EstadoReserva.ASISTIDO)) {
            Cliente cliente = paseGratisService.calcularPaseGratis(encontrarCliente(clienteId));
            clienteRepo.save(cliente);
        }

        if (nuevoEstado.equals(EstadoReserva.CANCELADO)) {
            eventoService.devolverCupo(reserva.getFuncion().getId(),reserva.getTipoDeEntrada());
        }

        return reservaMapper.toRes(reservaRepo.save(reserva));
    }

    public Cliente crearCliente(RegisterReq req, Usuario usuario){
        Cliente cliente = Cliente.builder()
                .nombre(req.nombre())
                .apellido(req.apellido())
                .email(req.email())
                .telefono(req.telefono())
                .documento(req.documento())
                .tipoDeDocumento(req.tipoDeDocumento())
                .usuario(usuario)
                .build();
        return clienteRepo.save(cliente);
    }

}
