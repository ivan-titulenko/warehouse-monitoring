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

    @Value("${warehouse.id}")
    String warehouseId;

    private final ReactiveKafkaProducerTemplate<String, SensorInfo> kafkaProducer;

    public SensorInformationProducer(KafkaProperties properties) {
        kafkaProducer = new ReactiveKafkaProducerTemplate<>(
                SenderOptions.create(properties.buildProducerProperties())
        );
    }

    public Mono<SenderResult<Void>> sendMessage(SensorInfo info) {
        return kafkaProducer.send("warehouse-climate", warehouseId, info);
    }
}
