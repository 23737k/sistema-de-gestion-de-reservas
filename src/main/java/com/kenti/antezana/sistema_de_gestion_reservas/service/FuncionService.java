package com.kenti.antezana.sistema_de_gestion_reservas.service;

import com.kenti.antezana.sistema_de_gestion_reservas.dto.mapper.FuncionMapper;
import com.kenti.antezana.sistema_de_gestion_reservas.dto.request.FuncionReq;
import com.kenti.antezana.sistema_de_gestion_reservas.dto.response.FuncionRes;
import com.kenti.antezana.sistema_de_gestion_reservas.model.Funcion;
import com.kenti.antezana.sistema_de_gestion_reservas.repository.FuncionRepo;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FuncionService {
    private final FuncionRepo funcionRepo;
    private final FuncionMapper funcionMapper;
    private final EventoService eventoService;

    public List<FuncionRes> obtenerFunciones(Long funcionId) {
        List<Funcion> funciones = funcionRepo.findFuncionByEvento_Id(funcionId);
        return funciones.stream().map(funcionMapper::toRes).toList();
    }

    public FuncionRes obtenerFuncion(Long eventoId, Long funcionId) {
        if (eventoService.existeEvento(eventoId)) {
            Funcion funcion = funcionRepo.findById(funcionId)
                .orElseThrow(() -> new EntityNotFoundException("Funcion no encontrada"));
            return funcionMapper.toRes(funcion);
        } else {
            throw new EntityNotFoundException("Evento no encontrado");
        }
    }

    public FuncionRes crearFuncion(FuncionReq funcionReq) {
        Funcion funcion = funcionMapper.toEntity(funcionReq);
        return funcionMapper.toRes(funcionRepo.save(funcion));
    }

    public FuncionRes actualizarFuncion(Long id, FuncionReq funcionReq) {
        if (funcionRepo.existsById(id)) {
            Funcion funcion = funcionMapper.toEntity(funcionReq);
            funcion.setId(id);
            return funcionMapper.toRes(funcionRepo.save(funcion));
        } else {
            throw new EntityNotFoundException("Funcion no encontrada");
        }

    }

    public void eliminarFuncion(Long id) {
        if (funcionRepo.existsById(id)) {
            funcionRepo.deleteById(id);
        } else {
            throw new EntityNotFoundException("Funcion no encontrada");
        }
    }
}
