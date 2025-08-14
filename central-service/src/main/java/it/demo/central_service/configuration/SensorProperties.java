package it.demo.central_service.configuration;

import it.demo.central_service.bean.SensorProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "central")
public class SensorProperties {

    private List<SensorProperty> sensors;

    public List<SensorProperty> getSensors() {
        return sensors;
    }

    public void setSensors(List<SensorProperty> sensors) {
        this.sensors = sensors;
    }
}
