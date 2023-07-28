package com.example.batch;

import com.example.batch.dto.DatoDTO;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration implements CommandLineRunner {

    @Value("${inputFile}")
    private Resource inputFile;

    @Bean
    public FlatFileItemReader<DatoDTO> reader() {
        return new FlatFileItemReaderBuilder<DatoDTO>()
                .name("DatoItemReader")
                .resource(inputFile)
                .delimited()
                .names(new String[]{"numero", "nombre", "fecha"})
                .fieldSetMapper(new BeanWrapperFieldSetMapper<>() {{
                    setTargetType(DatoDTO.class);
                }})
                .build();
    }

    @Override
    public void run(String... args) {
        if (args.length > 0) {
            String inputFileArg = args[0];
            inputFile = new FileSystemResource(inputFileArg);
        } else {
            throw new IllegalArgumentException("Debe proporcionar el nombre del archivo de entrada como argumento.");
        }
    }
}
