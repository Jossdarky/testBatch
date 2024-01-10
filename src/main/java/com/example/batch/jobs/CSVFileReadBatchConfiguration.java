package com.example.batch.jobs;

import com.example.batch.dto.CSVRowDTO;
import com.example.batch.dto.DatoDTO;
import com.example.batch.jobs.csvfileread.CSVFileReadJobListener;
import com.example.batch.jobs.csvfileread.CSVFileReadJobProcessor;
import com.example.batch.jobs.csvfileread.CSVFileReadJobReader;
import com.example.batch.jobs.csvfileread.CSVFileReadJobWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
public class CSVFileReadBatchConfiguration {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Bean
    public Step step1(CSVFileReadJobReader reader, CSVFileReadJobProcessor processor, CSVFileReadJobWriter writer) {
        return stepBuilderFactory.get("step1")
                .<CSVRowDTO, DatoDTO> chunk(10)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .faultTolerant() // Enables fault tolerance
                .skip(Exception.class) // Skip exception type
                .skipLimit(100) // Maximum number of exceptions to skip
                .build();
    }

    @Bean
    public Job createCSVFileReadJob(CSVFileReadJobReader reader, CSVFileReadJobProcessor processor, CSVFileReadJobWriter writer, CSVFileReadJobListener listener) {
        return jobBuilderFactory.get("CSVFileReadJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(step1(reader, processor, writer))
                .end()
                .build();
    }

}
