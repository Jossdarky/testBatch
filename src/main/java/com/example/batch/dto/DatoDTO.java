package com.example.batch.dto;

import lombok.*;

import java.util.Date;

@Data
@Builder
@ToString
@AllArgsConstructor
public class DatoDTO {
    private Long numero;
    private String nombre;
    private Date fecha;
}
