package com.kenti.antezana.sistema_de_gestion_de_reservas.service;

import com.kenti.antezana.sistema_de_gestion_de_reservas.dto.mapper.EventoMapper;
import com.kenti.antezana.sistema_de_gestion_de_reservas.dto.mapper.FuncionMapper;
import com.kenti.antezana.sistema_de_gestion_de_reservas.dto.request.EventoReq;
import com.kenti.antezana.sistema_de_gestion_de_reservas.dto.request.FuncionReq;
import com.kenti.antezana.sistema_de_gestion_de_reservas.dto.response.EventoRes;
import com.kenti.antezana.sistema_de_gestion_de_reservas.dto.response.FuncionRes;
import com.kenti.antezana.sistema_de_gestion_de_reservas.exception.TipoDeEntradaDuplicadosException;
import com.kenti.antezana.sistema_de_gestion_de_reservas.exception.TipoDeEntradaInvalidaException;
import com.kenti.antezana.sistema_de_gestion_de_reservas.model.Disponibilidad;
import com.kenti.antezana.sistema_de_gestion_de_reservas.model.Evento;
import com.kenti.antezana.sistema_de_gestion_de_reservas.model.Funcion;
import com.kenti.antezana.sistema_de_gestion_de_reservas.model.TipoDeEntrada;
import com.kenti.antezana.sistema_de_gestion_de_reservas.repository.EventoRepo;
import com.kenti.antezana.sistema_de_gestion_de_reservas.repository.FuncionRepo;
import jakarta.persistence.EntityNotFoundException;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class EventoService {
    private final EventoRepo eventoRepo;
    private final FuncionRepo funcionRepo;
    private final EventoMapper eventoMapper;
    private final FuncionMapper funcionMapper;

    public EventoRes crearEvento(EventoReq eventoReq) {
        Evento evento = eventoMapper.toEntity(eventoReq);
        return eventoMapper.toRes(eventoRepo.save(evento));
    }

    public List<EventoRes> obtenerEventos() {
        return eventoRepo.findAll().stream().map(eventoMapper::toRes).toList();
    }

    public EventoRes obtenerEventoPorId(Long id) {
        return eventoRepo.findById(id).map(eventoMapper::toRes)
            .orElseThrow(() -> new EntityNotFoundException("Evento no encontrado"));
    }

    public EventoRes modificarEvento(Long id, EventoReq eventoReq) {
        Evento evento = eventoRepo.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Evento no encontrado"));
        eventoMapper.updateEntity(eventoReq, evento);
        eventoRepo.save(evento);
        return eventoMapper.toRes(evento);
    }

    public void eliminarEvento(Long id) {
        Evento evento = eventoRepo.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Evento no encontrado"));
        eventoRepo.delete(evento);
    }


    // CRUD Funciones

    public List<FuncionRes> obtenerFunciones(Long eventoId) {
        Evento evento = eventoRepo.findById(eventoId)
            .orElseThrow(() -> new EntityNotFoundException("Evento no encontrado"));
        return evento.getFunciones().stream().map(funcionMapper::toRes).toList();
    }

    public FuncionRes obtenerFuncion(Long eventoId, Long funcionId) {
        Evento evento = eventoRepo.findById(eventoId)
            .orElseThrow(() -> new EntityNotFoundException("Evento no encontrado"));

        return evento.getFunciones().stream().filter(f -> Objects.equals(f.getId(), funcionId))
            .findFirst().map(funcionMapper::toRes).orElseThrow(() ->
                new EntityNotFoundException("Funcion no encontrada"));
    }

    public FuncionRes agregarFuncion(Long eventoId, FuncionReq funcionReq) {
        Evento evento = eventoRepo.findById(eventoId)
            .orElseThrow(() -> new EntityNotFoundException("Evento no encontrado"));

        Funcion funcion = funcionMapper.toEntity(funcionReq);
        validarFuncion(evento, funcion);

        evento.getFunciones().add(funcion);
        funcion.setEvento(evento);

        funcionRepo.save(funcion);
        eventoRepo.save(evento);
        return funcionMapper.toRes(funcion);
    }

    public FuncionRes actualizarFuncion(Long eventoId, Long id, FuncionReq funcionReq) {
        Evento evento = eventoRepo.findById(eventoId)
            .orElseThrow(() -> new EntityNotFoundException("Evento no encontrado"));

        Funcion funcion = evento.getFunciones().stream()
            .filter(f -> f.getId().equals(id)).findFirst()
            .orElseThrow(() -> new EntityNotFoundException("Funcion no encontrada"));

        funcionMapper.updateFuncion(funcionReq, funcion);
        validarFuncion(evento, funcion);
        eventoRepo.save(evento);
        return funcionMapper.toRes(funcion);
    }

    public void eliminarFuncion(Long eventoId, Long id) {
        Evento evento = eventoRepo.findById(eventoId)
            .orElseThrow(() -> new EntityNotFoundException("Evento no encontrado"));
        boolean removed = evento.getFunciones().removeIf(f -> Objects.equals(f.getId(), id));
        if (removed) {
            eventoRepo.save(evento);
        } else {
            throw new EntityNotFoundException("Funcion no encontrada");
        }

    }

    public void validarFuncion(Evento evento, Funcion funcion) {
        List<Disponibilidad> disponibilidades = funcion.getDisponibilidades();
        disponibilidades.stream()
            .filter(d -> !evento.getTipoDeEvento().contieneTipoDeEntrada(d.getTipoDeEntrada()))
            .findFirst()
            .ifPresent(d -> {
                String entradasValidas = evento.getTipoDeEvento().getTiposDeEntradas().stream()
                    .map(Enum::name)
                    .collect(Collectors.joining(", "));

                String mensaje = "La función contiene un tipo de entrada '" + d.getTipoDeEntrada().name() +
                    "' que no corresponde al tipo de evento '" + evento.getTipoDeEvento().name() + "'. " +
                    "Tipos de entrada válidos para este evento: " + entradasValidas;
                throw new TipoDeEntradaInvalidaException(mensaje);
            });

        Set<TipoDeEntrada> tipoDeEntradaSinDuplicados = new HashSet<>();
        boolean tieneTipoDeEntradaDuplicados = disponibilidades.stream().anyMatch(disponibilidad ->
            !tipoDeEntradaSinDuplicados.add(disponibilidad.getTipoDeEntrada()));
        if (tieneTipoDeEntradaDuplicados) {
            throw new TipoDeEntradaDuplicadosException(
                "No pueden haber tipos de entrada duplicados en una misma funcion.");
        }
    }

    public Funcion encontrarFuncionPorId(Long id) {
        return funcionRepo.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Funcion no encontrada"));
    }

    public void devolverCupo(Long funcionId, TipoDeEntrada tipoDeEntrada ) {
        Funcion funcion = encontrarFuncionPorId(funcionId);
        Disponibilidad disponibilidad = funcion
            .encontrarDisponibilidad(tipoDeEntrada);

        disponibilidad.devolverCupo();
        funcionRepo.save(funcion);
    }

}
