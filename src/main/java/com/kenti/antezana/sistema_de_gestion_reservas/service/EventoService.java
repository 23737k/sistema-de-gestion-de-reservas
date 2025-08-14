package com.kenti.antezana.sistema_de_gestion_reservas.service;

import com.kenti.antezana.sistema_de_gestion_reservas.dto.mapper.EventoMapper;
import com.kenti.antezana.sistema_de_gestion_reservas.dto.request.EventoReq;
import com.kenti.antezana.sistema_de_gestion_reservas.dto.response.EventoRes;
import com.kenti.antezana.sistema_de_gestion_reservas.model.Evento;
import com.kenti.antezana.sistema_de_gestion_reservas.repository.EventoRepo;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventoService {
  private final EventoRepo eventoRepo;
  private final EventoMapper eventoMapper;

  public EventoRes crearEvento(EventoReq eventoReq) {
    Evento evento = eventoMapper.toEntity(eventoReq);
    return eventoMapper.toRes(eventoRepo.save(evento));
  }

  public List<EventoRes> obtenerEventos() {
    return eventoRepo.findAll().stream().map(eventoMapper::toRes).toList();
  }

  public EventoRes obtenerEventoPorId(Long id) {
    return eventoRepo.findById(id).map(eventoMapper::toRes).orElseThrow(()-> new EntityNotFoundException("Evento no encontrado"));
  }
}
