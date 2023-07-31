package com.example.batch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BatchApplication {

	public static void main(String[] args) {
		if (args.length > 0) {
			String inputFile = args[0];
			System.setProperty("file.input", inputFile);
		} else {
			throw new IllegalArgumentException("Debe proporcionar el nombre del archivo de entrada como argumento.");
		}
		SpringApplication.run(BatchApplication.class, args);
	}

}
