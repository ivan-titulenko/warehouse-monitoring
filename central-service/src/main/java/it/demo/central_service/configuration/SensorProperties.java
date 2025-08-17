package it.demo.central_service.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "central")
public class SensorProperties {

    private Map<String, SensorProperty> sensors;

    public Map<String, SensorProperty> getSensors() {
        return sensors;
    }

    public void setSensors(Map<String, SensorProperty> sensors) {
        this.sensors = sensors;
    }
}
