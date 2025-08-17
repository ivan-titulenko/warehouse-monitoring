package it.demo.warehouse_service.kafka;

import it.demo.warehouse_service.bean.SensorInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.kafka.sender.SenderOptions;
import reactor.kafka.sender.SenderResult;

@Component
public class SensorInformationProducer {

    private final String warehouseId;
    private final String topic;
    private final ReactiveKafkaProducerTemplate<String, SensorInfo> kafkaProducer;

    public SensorInformationProducer(KafkaProperties properties, @Value("${warehouse.id}") String warehouseId, @Value("${warehouse.kafka-topic}") String topic) {
        kafkaProducer = new ReactiveKafkaProducerTemplate<>(
                SenderOptions.create(properties.buildProducerProperties())
        );
        this.warehouseId = warehouseId;
        this.topic = topic;
    }

    public Mono<SenderResult<Void>> sendMessage(SensorInfo info) {
        return kafkaProducer.send(topic, warehouseId, info);
    }
}
