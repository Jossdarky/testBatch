package com.example.batch.jobs.csvfileread;

import com.example.batch.dto.CSVRowDTO;
import com.example.batch.dto.DatoDTO;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Component
public class CSVFileReadJobWriter implements ItemWriter<DatoDTO> {

    private static final String OUTPUT_FILE = "./csv/ARCHIVO-SALIDA.csv";

    @Autowired
    CSVFileReadJobProcessor csvFileReadJobProcessor;

    @Override
    public void write(List<? extends DatoDTO> items) throws Exception {
        for (DatoDTO item : items) {
            System.out.println("Elemento del Chunk: " + item.toString());
        }

        List<CSVRowDTO> recordsWithErrors = csvFileReadJobProcessor.getRecordsWithErrors();

        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(OUTPUT_FILE))) {
            for (CSVRowDTO record : recordsWithErrors) {
                writer.write(record.getNumero() + ";" + record.getNombre() + ";" + record.getFecha() + ";RUNTIME Error");
                writer.newLine();
            }
        }
    }
}