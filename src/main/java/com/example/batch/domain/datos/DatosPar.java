package com.example.batch.domain.datos;

import lombok.*;

import java.util.Date;

@Data
@Builder
@ToString
@AllArgsConstructor
public class DatosPar {
    private Long numero;
    private String nombre;
    private Date fecha;
}
