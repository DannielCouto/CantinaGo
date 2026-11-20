package pt.ua.cantinago.telemetry;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "cantinago.mqtt")
public record MqttProperties(boolean enabled, String brokerUrl, String clientId, String topic) {}
