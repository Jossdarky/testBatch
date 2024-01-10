package com.example.batch.jobs.csvfileread;

import com.example.batch.dto.CSVRowDTO;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStream;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;
@Component
public class CSVFileReadJobReader extends FlatFileItemReader<CSVRowDTO> implements ItemStream {

    @Value("${file.input}")
    private String fileInput;

    public CSVFileReadJobReader() {
        setLineMapper(createLineMapper());
    }

    private LineMapper<CSVRowDTO> createLineMapper() {
        DefaultLineMapper<CSVRowDTO> lineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer(";");
        lineTokenizer.setNames("numero", "nombre", "fecha");
        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(createFieldSetMapper());
        return lineMapper;
    }

    private FieldSetMapper<CSVRowDTO> createFieldSetMapper() {
        BeanWrapperFieldSetMapper<CSVRowDTO> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(CSVRowDTO.class);
        return fieldSetMapper;
    }

    @Override
    public void open(ExecutionContext executionContext) throws ItemStreamException {
        setResource(new FileSystemResource(fileInput));
        super.open(executionContext);
    }

    @Override
    public void update(ExecutionContext executionContext) throws ItemStreamException {
        // No es necesario hacer nada aquí para este ejemplo
    }

    @Override
    public void close() throws ItemStreamException {
        // No es necesario hacer nada aquí para este ejemplo
    }
}