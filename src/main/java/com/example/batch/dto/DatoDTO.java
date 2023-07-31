package com.example.batch.dto;

import lombok.*;

import java.util.Date;

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class DatoDTO {

    private Long numero;

    private String nombre;

    private Date fecha;
}
