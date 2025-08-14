package com.kenti.antezana.sistema_de_gestion_reservas.dto.mapper;

import com.kenti.antezana.sistema_de_gestion_reservas.dto.request.EventoReq;
import com.kenti.antezana.sistema_de_gestion_reservas.dto.response.EventoRes;
import com.kenti.antezana.sistema_de_gestion_reservas.model.Evento;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EventoMapper {
  public EventoRes toRes(Evento evento);
  public Evento toEntity(EventoReq eventoReq);
}
