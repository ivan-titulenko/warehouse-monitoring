package it.demo.warehouse_service.configuration;

import java.util.regex.Pattern;

public record SensorProperty(Integer port, Pattern format) {
}
