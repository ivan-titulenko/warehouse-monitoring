package it.demo.central_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.integration.config.EnableIntegration;


@EnableIntegration
@SpringBootApplication
public class CentralServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CentralServiceApplication.class, args);
	}

}
