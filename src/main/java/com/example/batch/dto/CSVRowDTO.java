package com.example.batch.dto;

import lombok.*;

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CSVRowDTO {
    private String numero;

    private String nombre;

    private String fecha;
}
