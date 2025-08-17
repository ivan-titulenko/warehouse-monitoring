package it.demo.warehouse_service.handler;

import reactor.core.publisher.Mono;

public interface SensorProcessor {

    Mono<Void> handleMessage(String message);

    String sensorType();
}
