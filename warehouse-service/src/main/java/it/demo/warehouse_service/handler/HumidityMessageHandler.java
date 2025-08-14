package it.demo.warehouse_service.handler;

import it.demo.warehouse_service.kafka.SensorInformationProducer;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class HumidityMessageHandler implements SensorMessageHandler {

    private final SensorInformationProducer sensorInformationProducer;

    public HumidityMessageHandler(SensorInformationProducer sensorInformationProducer) {
        this.sensorInformationProducer = sensorInformationProducer;
    }

    @Override
    public Mono<Void> handleMessage(Message<?> message) {
        return sensorInformationProducer.sendMessage(getSensorInformation(message))
                .then();
    }

    @Override
    public String sensorType() {
        return "humidity";
    }
}
