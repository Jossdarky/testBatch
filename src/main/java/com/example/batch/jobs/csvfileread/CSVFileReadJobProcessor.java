package com.example.batch.jobs.csvfileread;

import com.example.batch.dto.CSVRowDTO;
import com.example.batch.dto.DatoDTO;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

@Component
public class CSVFileReadJobProcessor implements ItemProcessor<CSVRowDTO, DatoDTO> {

    private static final int MAX_NOMBRE_LENGTH = 128;
    private static final int MAX_NUMERO_VALUE = 2_000_000;
    private static final String DATE_FORMAT = "yyyy-MM-dd";

    private List<CSVRowDTO> recordsWithErrors = new ArrayList<>();

    @Override
    public DatoDTO process(CSVRowDTO dato) throws Exception {
        try {
            Long numero = parseNumero(dato.getNumero());
            String nombre = validateNombre(dato.getNombre());
            Date fecha = parseFecha(dato.getFecha());

            return DatoDTO.builder()
                    .numero(numero)
                    .nombre(nombre)
                    .fecha(fecha)
                    .build();
        } catch (Exception e) {
            dato.setNumero("RUNTIME " + e.getMessage());
            recordsWithErrors.add(dato);
            return null;
        }
    }

    public List<CSVRowDTO> getRecordsWithErrors() {
        return recordsWithErrors;
    }

    private static Long parseNumero(String numeroStr) {
        Long numero = Long.parseLong(numeroStr);
        if (numero < 1 || numero > MAX_NUMERO_VALUE) {
            throw new IllegalArgumentException("El número debe ser un entero entre 1 y 2.000.000");
        }
        return numero;
    }

    private static String validateNombre(String nombre) {
        if (nombre.length() > MAX_NOMBRE_LENGTH) {
            throw new IllegalArgumentException("El nombre debe tener un máximo de 128 caracteres");
        }
        return nombre;
    }

    private static Date parseFecha(String fechaStr) throws ParseException {
        try {
            int year = Integer.parseInt(fechaStr.substring(0, 4));
            if (year < 1900) {
                throw new IllegalArgumentException("El año debe ser mayor a 1900");
            }

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT, Locale.ENGLISH);
            LocalDate localDate = LocalDate.parse(fechaStr, formatter);

            return java.sql.Date.valueOf(localDate);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Fecha en formato incorrecto. El año debe estar representado por 4 dígitos numéricos", e);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Fecha en formato incorrecto. Debe ser YYYY-MM-DD", e);
        }
    }
}