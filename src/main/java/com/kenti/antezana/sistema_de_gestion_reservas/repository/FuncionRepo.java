package com.kenti.antezana.sistema_de_gestion_reservas.repository;

import com.kenti.antezana.sistema_de_gestion_reservas.model.Funcion;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FuncionRepo extends JpaRepository<Funcion, Long> {
    List<Funcion> findFuncionByEvento_Id(Long eventoId);
}
