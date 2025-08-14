package com.kenti.antezana.sistema_de_gestion_reservas.dto.mapper;

import com.kenti.antezana.sistema_de_gestion_reservas.dto.request.DisponibilidadReq;
import com.kenti.antezana.sistema_de_gestion_reservas.dto.response.DisponibilidadRes;
import com.kenti.antezana.sistema_de_gestion_reservas.model.Disponibilidad;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DisponibilidadMapper {
    Disponibilidad toEntity(DisponibilidadReq disponibilidadReq);

    DisponibilidadRes toRes(Disponibilidad disponibilidad);
}
