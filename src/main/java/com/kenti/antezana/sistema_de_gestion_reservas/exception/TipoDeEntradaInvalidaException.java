package com.kenti.antezana.sistema_de_gestion_reservas.exception;

import com.kenti.antezana.sistema_de_gestion_reservas.model.TipoDeEntrada;
import com.kenti.antezana.sistema_de_gestion_reservas.model.TipoDeEvento;
import java.util.stream.Collectors;

public class TipoDeEntradaInvalidaException extends RuntimeException {
    public TipoDeEntradaInvalidaException(TipoDeEvento tipoDeEvento, TipoDeEntrada tipoDeEntrada) {
        super(crearMensaje(tipoDeEvento, tipoDeEntrada));

    }

    public TipoDeEntradaInvalidaException(String s) {
    }

    private static String crearMensaje(TipoDeEvento tipoDeEvento, TipoDeEntrada tipoDeEntrada) {
        String entradasValidas = tipoDeEvento.getTiposDeEntradas().stream().map(Enum::name)
            .collect(Collectors.joining(", "));
        return "El tipo de entrada  " + tipoDeEntrada.name() + " no es valido para " +
            tipoDeEvento.name() + ". Tipos de entrada validos: " +
            entradasValidas;
    }
}
