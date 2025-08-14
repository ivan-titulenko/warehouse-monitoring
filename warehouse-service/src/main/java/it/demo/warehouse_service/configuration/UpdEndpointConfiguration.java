package it.demo.warehouse_service.configuration;

import it.demo.warehouse_service.handler.SensorMessageHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.ip.udp.UnicastReceivingChannelAdapter;

import java.util.Map;

@Configuration
public class UpdEndpointConfiguration {

    //TODO Make dynamic bean definition

    @Bean
    public IntegrationFlow tempIntegrationFlow(WarehouseProperties props, Map<String, SensorMessageHandler> sensorHandlers) {
        return IntegrationFlow
                .from(new UnicastReceivingChannelAdapter(props.getSensors().get(0).port()))
                .transform((byte[] p) -> new String(p, java.nio.charset.StandardCharsets.UTF_8))
                .handleReactive(sensorHandlers.get(props.getSensors().get(0).type()));
    }

    @Bean
    public IntegrationFlow humIntegrationFlow(WarehouseProperties props, Map<String, SensorMessageHandler> sensorHandlers) {
        return IntegrationFlow
                .from(new UnicastReceivingChannelAdapter(props.getSensors().get(1).port()))
                .transform((byte[] p) -> new String(p, java.nio.charset.StandardCharsets.UTF_8))
                .handleReactive(sensorHandlers.get(props.getSensors().get(1).type()));
    }
}
