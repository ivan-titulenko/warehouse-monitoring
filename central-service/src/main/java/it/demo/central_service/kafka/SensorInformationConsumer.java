package it.demo.central_service.kafka;

import it.demo.central_service.bean.SensorInfo;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import org.springframework.stereotype.Service;
import reactor.kafka.receiver.ReceiverOptions;

import java.util.List;

@Service
public class SensorInformationConsumer {

    Logger log = LoggerFactory.getLogger(SensorInformationConsumer.class);

    private final ReactiveKafkaConsumerTemplate<String, SensorInfo> kafkaConsumer;
    private final ClimateMessageHandler climateMessageHandler;

    public SensorInformationConsumer(KafkaProperties properties, ClimateMessageHandler climateMessageHandler) {
        kafkaConsumer = new ReactiveKafkaConsumerTemplate<>(
                ReceiverOptions.<String, SensorInfo>create(properties.buildConsumerProperties())
                        .subscription(List.of("warehouse-climate"))
        );
        this.climateMessageHandler = climateMessageHandler;
    }

    @PostConstruct
    public void consume() {
        kafkaConsumer
                .receiveAutoAck()
                .doOnNext(climateMessageHandler::handle)
                .doOnError(e -> log.error("Kafka consumer error ", e))
                .subscribe();
    }
}
