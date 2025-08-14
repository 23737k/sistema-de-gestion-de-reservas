package com.kenti.antezana.sistema_de_gestion_reservas.service;

import com.kenti.antezana.sistema_de_gestion_reservas.dto.mapper.FuncionMapper;
import com.kenti.antezana.sistema_de_gestion_reservas.dto.request.FuncionReq;
import com.kenti.antezana.sistema_de_gestion_reservas.dto.response.FuncionRes;
import com.kenti.antezana.sistema_de_gestion_reservas.model.Funcion;
import com.kenti.antezana.sistema_de_gestion_reservas.repository.FuncionRepo;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FuncionService {
    private final FuncionRepo funcionRepo;
    private final FuncionMapper funcionMapper;
    private final EventoService eventoService;

    public List<FuncionRes> obtenerFunciones(Long eventoId) {
        validarEvento(eventoId);
        return funcionRepo.findFuncionByEvento_Id(eventoId).stream().map(funcionMapper::toRes).toList();

    }

    public FuncionRes obtenerFuncion(Long eventoId, Long funcionId) {
        validarEvento(eventoId);
        Funcion funcion = funcionRepo.findById(funcionId)
                .orElseThrow(() -> new EntityNotFoundException("Funcion no encontrada"));
        return funcionMapper.toRes(funcion);
    }

    public FuncionRes crearFuncion(Long eventoId, FuncionReq funcionReq) {
        validarEvento(eventoId);
        Funcion funcion = funcionMapper.toEntity(funcionReq);
        funcion.setEvento();
        return funcionMapper.toRes(funcionRepo.save(funcion));
    }

    public FuncionRes actualizarFuncion(Long eventoId, Long id, FuncionReq funcionReq) {
        validarEvento(eventoId);
        if (funcionRepo.existsById(id)) {
            Funcion funcion = funcionMapper.toEntity(funcionReq);
            funcion.setId(id);
            return funcionMapper.toRes(funcionRepo.save(funcion));
        } else {
            throw new EntityNotFoundException("Funcion no encontrada");
        }

    }

    public void eliminarFuncion(Long eventoId, Long id) {
        validarEvento(eventoId);
        if (funcionRepo.existsById(id))
            funcionRepo.deleteById(id);
        else
            throw new EntityNotFoundException("Funcion no encontrada");

    }

    private void validarEvento(Long eventoId) {
        if(!eventoService.existeEvento(eventoId))
            throw new EntityNotFoundException("Evento no encontrado");
    }
}
