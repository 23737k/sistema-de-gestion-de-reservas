package com.kenti.antezana.sistema_de_gestion_reservas.model;

import lombok.Getter;
import java.util.List;
import static com.kenti.antezana.sistema_de_gestion_reservas.model.TipoDeEntrada.*;

@Getter
public enum TipoDeEvento {
    OBRA_DE_TEATRO(
            List.of(
                    ENTRADA_GENERAL,
                    ENTRADA_VIP)
    ),
    RECITAL(
            List.of(
                    CAMPO,
                    PLATEA,
                    PALCO
            )
    ),
    CHARLA_CONFERENCIA(
            List.of(
                    CON_MEET_AND_GREET,
                    SIN_MEET_AND_GREET
            )
    );

    TipoDeEvento(List<TipoDeEntrada> tiposDeEntrada){
        this.tiposDeEntradas= tiposDeEntrada;
    }
    private final List<TipoDeEntrada> tiposDeEntradas;

    public boolean contieneTipoDeEntrada(TipoDeEntrada tipoDeEntrada){
        return tiposDeEntradas.contains(tipoDeEntrada);
    }

}
