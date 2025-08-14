package it.demo.warehouse_service.handler;

import it.demo.warehouse_service.bean.SensorInfo;
import org.springframework.messaging.Message;
import org.springframework.messaging.ReactiveMessageHandler;

public interface SensorMessageHandler extends ReactiveMessageHandler {

    default SensorInfo getSensorInformation(Message<?> message) {
        String[] split = message.getPayload().toString().trim().split(";");
        return new SensorInfo(split[0].replace("sensor_id=", ""), sensorType(), Integer.valueOf(split[1].replace(" value=", "")));
    }

    String sensorType();
}
