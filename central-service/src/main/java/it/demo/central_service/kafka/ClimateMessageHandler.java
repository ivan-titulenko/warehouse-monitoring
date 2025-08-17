package it.demo.central_service.kafka;

import it.demo.central_service.bean.SensorInfo;
import it.demo.central_service.configuration.SensorProperty;
import it.demo.central_service.configuration.SensorProperties;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ClimateMessageHandler {

    Logger log = LoggerFactory.getLogger(ClimateMessageHandler.class);

    private final Map<String, SensorProperty> props;

    public ClimateMessageHandler(SensorProperties props) {
        this.props = props.getSensors();
    }

    public void handle(ConsumerRecord<String, SensorInfo> record) {
        SensorInfo sensorInfo = record.value();
        if (!props.containsKey(sensorInfo.type())) {
            log.error("Sensor {} is not configured", sensorInfo.type());
        }
        if (props.get(sensorInfo.type()).threshold() < sensorInfo.value()) {
            System.out.printf("======== ALARM!!! ========= Warehouse %s, %s sensor %s at value %d!!! \n", record.key(), sensorInfo.type(), sensorInfo.sensorId(), sensorInfo.value());

        }
    }
}
