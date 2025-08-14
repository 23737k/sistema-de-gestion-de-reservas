package com.kenti.antezana.sistema_de_gestion_reservas.dto.mapper;

import com.kenti.antezana.sistema_de_gestion_reservas.dto.request.FuncionReq;
import com.kenti.antezana.sistema_de_gestion_reservas.dto.response.FuncionRes;
import com.kenti.antezana.sistema_de_gestion_reservas.model.Funcion;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = DisponibilidadMapper.class)
public interface FuncionMapper {
    Funcion toEntity(FuncionReq funcionReq);

    FuncionRes toRes(Funcion funcion);
}
