package com.ratna.batch.springbatchprocessing;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableBatchProcessing
public class SpringbatchprocessingApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbatchprocessingApplication.class, args);
	}

}
