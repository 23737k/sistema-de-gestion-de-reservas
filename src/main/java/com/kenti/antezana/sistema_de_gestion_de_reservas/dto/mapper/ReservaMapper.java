package com.kenti.antezana.sistema_de_gestion_de_reservas.dto.mapper;

import com.kenti.antezana.sistema_de_gestion_de_reservas.dto.request.ReservaReq;
import com.kenti.antezana.sistema_de_gestion_de_reservas.dto.response.ReservaRes;
import com.kenti.antezana.sistema_de_gestion_de_reservas.model.Reserva;
import com.kenti.antezana.sistema_de_gestion_de_reservas.service.ClienteService;
import com.kenti.antezana.sistema_de_gestion_de_reservas.service.EventoService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", uses = {EventoService.class, ClienteService.class})
public interface ReservaMapper {
    @Mappings({
        @Mapping(source = "funcion.id", target = "funcionId"),
        @Mapping(source = "cliente.id", target = "clienteId")
    })
    ReservaRes toRes(Reserva reserva);

    void updateEntity(ReservaReq reservaReq, @MappingTarget Reserva reserva);
}
