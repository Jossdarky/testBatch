package com.example.batch.domain.ejecuciones;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.sql.Timestamp;

@Data
@Builder
@ToString
@AllArgsConstructor
public class Ejecuciones {

    private Timestamp fechaHora;
    private String archivoEntrada;
    private Long numeroRegistrosTotales;
    private Long numeroRegistrosValidos;
    private Long numerosRegistrosInvalidos;
    private Long numeroErroresTiempoEjecucion;

}
