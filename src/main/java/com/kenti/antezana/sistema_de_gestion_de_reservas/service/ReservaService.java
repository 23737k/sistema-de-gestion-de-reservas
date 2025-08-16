package com.kenti.antezana.sistema_de_gestion_de_reservas.service;

import com.kenti.antezana.sistema_de_gestion_de_reservas.dto.mapper.ReservaMapper;
import com.kenti.antezana.sistema_de_gestion_de_reservas.repository.ReservaRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ReservaService {
    private final ReservaRepo reservaRepo;
    private final EventoService eventoService;
    private final ClienteService clienteService;
    private final ReservaMapper reservaMapper;
    private final PaseGratisService paseGratisService;

//    public ReservaRes obtenerReserva(Long id) {
//        Reserva reserva = reservaRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Reserva no encontrada"));
//        return reservaMapper.toRes(reserva);
//    }
//
//
//
//    public ReservaRes crearReserva(ReservaReq reservaReq) {
//        Cliente cliente = clienteService.encontrarCliente(reservaReq.clienteId());
//        Funcion funcion = eventoService.encontrarFuncionPorId(reservaReq.funcionId());
//        Disponibilidad disponibilidad = funcion.encontrarDisponibilidad(reservaReq.tipoDeEntrada());
//        disponibilidad.reservarCupo();
//
//        Reserva reserva = Reserva.builder()
//            .funcion(funcion)
//            .tipoDeEntrada(reservaReq.tipoDeEntrada())
//            .cliente(cliente)
//            .estadoReserva(EstadoReserva.RESERVADO)
//            .montoTotal(disponibilidad.getPrecio())
//            .build();
//        verificarPasesGratis(reserva);
//
//        return reservaMapper.toRes(reservaRepo.save(reserva));
//    }
//
//    public void verificarPasesGratis(Reserva reserva) {
//        Cliente cliente = reserva.getCliente();
//        List<PaseGratis> pasesGratis = cliente.getPasesGratis();
//        Optional<PaseGratis> paseGratis = pasesGratis.stream().filter(p -> !p.isUsado()).findAny();
//        if (paseGratis.isPresent()) {
//            paseGratis.get().setUsado(true);
//            reserva.setMontoTotal(0);
//            reserva.setEstadoReserva(EstadoReserva.PAGADO);
//            reserva.setBonificado(true);
//        }
//    }
//
//    public void modificarEstadoReserva(Long reservaId, EstadoReservaReq estadoReservaReq) {
//        EstadoReserva nuevoEstado = estadoReservaReq.estadoReserva();
//        Reserva reserva = reservaRepo.findById(reservaId)
//            .orElseThrow(() -> new EntityNotFoundException("Reserva no encontrada"));
//
//        if (!reserva.getEstadoReserva().cambioDeEstadoValido(nuevoEstado)) {
//            throw new CambioDeEstadoInvalidoException(
//                "El cambio de estado a " + nuevoEstado.name()
//                    + " no es valido con el estado actual "
//                    + reserva.getEstadoReserva().name());
//        }
//
//        reserva.setEstadoReserva(nuevoEstado);
//        if (nuevoEstado.equals(EstadoReserva.ASISTIDO)) {
//            paseGratisService.calcularPaseGratis(clienteService.encontrarCliente(reservaId));
//        }
//        reservaRepo.save(reserva);
//    }

}
