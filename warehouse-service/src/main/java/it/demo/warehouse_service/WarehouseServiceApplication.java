package it.demo.warehouse_service;

import it.demo.warehouse_service.handler.SensorProcessor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.config.EnableIntegration;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@EnableIntegration
@SpringBootApplication
public class WarehouseServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(WarehouseServiceApplication.class, args);
	}

    @Bean
    public Map<String, SensorProcessor> sensorHandlers(List<SensorProcessor> handlers) {
        return handlers.stream()
                .collect(Collectors.toMap(SensorProcessor::sensorType, Function.identity()));
    }
}
