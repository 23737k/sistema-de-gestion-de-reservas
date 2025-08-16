package com.kenti.antezana.sistema_de_gestion_de_reservas.dto.mapper;

import com.kenti.antezana.sistema_de_gestion_de_reservas.dto.request.EventoReq;
import com.kenti.antezana.sistema_de_gestion_de_reservas.dto.response.EventoRes;
import com.kenti.antezana.sistema_de_gestion_de_reservas.model.Evento;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface EventoMapper {
    EventoRes toRes(Evento evento);

    Evento toEntity(EventoReq eventoReq);

    void updateEntity(EventoReq eventoReq, @MappingTarget Evento evento);
}
