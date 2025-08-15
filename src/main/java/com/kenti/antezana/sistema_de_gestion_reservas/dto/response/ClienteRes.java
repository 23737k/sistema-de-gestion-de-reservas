package com.kenti.antezana.sistema_de_gestion_reservas.dto.response;

import com.kenti.antezana.sistema_de_gestion_reservas.model.TipoDeDocumento;
import java.util.List;

public record ClienteRes(
    Long id,
    String nombre,
    String apellido,
    String email,
    String telefono,
    TipoDeDocumento tipoDeDocumento,
    String documento,
    List<PaseGratisRes> pasesGratis
) {
}
