package it.demo.central_service.kafka;

import it.demo.central_service.bean.SensorInfo;
import it.demo.central_service.bean.SensorProperty;
import it.demo.central_service.configuration.SensorProperties;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ClimateMessageHandler {

    private final Map<String, Integer> threshold;

    public ClimateMessageHandler(SensorProperties props) {
        this.threshold = props.getSensors().stream().collect(Collectors.toMap(SensorProperty::type, SensorProperty::threshold));
    }

    public void handle(ConsumerRecord<String, SensorInfo> record) {
        SensorInfo sensorInfo = record.value();
        if (!threshold.containsKey(sensorInfo.type())) {
            System.out.println("Unknown type of sensor: " + record.value().type());
        }
        if (threshold.get(sensorInfo.type()) < sensorInfo.value()) {
            System.out.printf("======== ALARM!!! ========= Warehouse %s sensor %s at value %d \n", record.key(), sensorInfo.sensorId(), sensorInfo.value());
        }
    }
}
