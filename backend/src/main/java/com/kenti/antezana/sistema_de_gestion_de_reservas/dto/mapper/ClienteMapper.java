package com.kenti.antezana.sistema_de_gestion_de_reservas.dto.mapper;

import com.kenti.antezana.sistema_de_gestion_de_reservas.dto.request.ClienteReq;
import com.kenti.antezana.sistema_de_gestion_de_reservas.dto.response.ClienteRes;
import com.kenti.antezana.sistema_de_gestion_de_reservas.model.Cliente;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ClienteMapper {
    Cliente toEntity(ClienteReq clienteReq);

    ClienteRes toRes(Cliente cliente);

    void updateEntity(ClienteReq clienteReq, @MappingTarget Cliente cliente);
}
