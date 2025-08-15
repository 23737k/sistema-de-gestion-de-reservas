package com.kenti.antezana.sistema_de_gestion_reservas.service;

import com.kenti.antezana.sistema_de_gestion_reservas.dto.mapper.EventoMapper;
import com.kenti.antezana.sistema_de_gestion_reservas.dto.mapper.FuncionMapper;
import com.kenti.antezana.sistema_de_gestion_reservas.dto.request.EventoReq;
import com.kenti.antezana.sistema_de_gestion_reservas.dto.request.FuncionReq;
import com.kenti.antezana.sistema_de_gestion_reservas.dto.response.EventoRes;
import com.kenti.antezana.sistema_de_gestion_reservas.dto.response.FuncionRes;
import com.kenti.antezana.sistema_de_gestion_reservas.model.Evento;
import com.kenti.antezana.sistema_de_gestion_reservas.model.Funcion;
import com.kenti.antezana.sistema_de_gestion_reservas.repository.EventoRepo;
import com.kenti.antezana.sistema_de_gestion_reservas.repository.FuncionRepo;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Objects;
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

        evento.agregarFuncion(funcion);

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


}
