package it.demo.warehouse_service.configuration;

import it.demo.warehouse_service.handler.SensorProcessor;
import jakarta.annotation.PreDestroy;
import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import reactor.netty.Connection;
import reactor.netty.DisposableChannel;
import reactor.netty.udp.UdpInbound;
import reactor.netty.udp.UdpOutbound;
import reactor.netty.udp.UdpServer;

import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

@Configuration
public class UpdEndpointConfiguration {

    Logger log = LoggerFactory.getLogger(UpdEndpointConfiguration.class);

    private final SensorsProperties sensorsProperties;
    private final Map<String, SensorProcessor> sensorHandlers;
    private List<Connection> connections;

    public UpdEndpointConfiguration(SensorsProperties sensorsProperties, Map<String, SensorProcessor> sensorHandlers) {
        this.sensorsProperties = sensorsProperties;
        this.sensorHandlers = sensorHandlers;
    }

    @EventListener(ContextRefreshedEvent.class)
    public void start() {
        connections = sensorsProperties.getSensors().entrySet()
                .stream()
                .map(e ->
                        UdpServer.create()
                                .host("::")
                                .port(e.getValue().port())
                                .handle(handleSensorMessageFunction(e.getKey()))
                                .bindNow())
                .toList();
    }

    @PreDestroy
    public void stop() {
        connections.forEach(DisposableChannel::disposeNow);
    }

    private BiFunction<UdpInbound, UdpOutbound, Publisher<Void>> handleSensorMessageFunction(String type) {
        return (in, out) ->
                in.receive()
                        .asString()
                        .map(String::trim)
                        .log()
                        .flatMap(sensorHandlers.get(type)::handleMessage)
                        .doOnError(e -> log.error("Error during message processing", e))
                        .then();
    }
}
