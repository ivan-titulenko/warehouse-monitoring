package it.demo.warehouse_service.handler;

import it.demo.warehouse_service.bean.SensorInfo;
import it.demo.warehouse_service.configuration.SensorProperty;
import it.demo.warehouse_service.configuration.SensorsProperties;
import it.demo.warehouse_service.kafka.SensorInformationProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.regex.Matcher;

@Service
public class TemperatureProcessor implements SensorProcessor {

    Logger log = LoggerFactory.getLogger(TemperatureProcessor.class);

    private final SensorProperty sensorProperty;

    private final SensorInformationProducer sensorInformationProducer;

    public TemperatureProcessor(SensorInformationProducer sensorInformationProducer, SensorsProperties sensorsProperties) {
        this.sensorInformationProducer = sensorInformationProducer;
        this.sensorProperty = sensorsProperties.getSensors().get(sensorType());
    }

    @Override
    public Mono<Void> handleMessage(String message) {
        Matcher matcher = sensorProperty.format().matcher(message);
        if (!matcher.matches()) {
            log.warn("Invalid temperature sensor message format: {}",  message);
            return Mono.empty();
        }

        SensorInfo sensorInfo = new SensorInfo(matcher.group("id"), sensorType(), Integer.parseInt(matcher.group("value")));

        return sensorInformationProducer.sendMessage(sensorInfo)
                .then();
    }

    @Override
    public String sensorType() {
        return "temperature";
    }
}
