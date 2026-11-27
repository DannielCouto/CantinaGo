package pt.ua.cantinago.telemetry;

import jakarta.annotation.PreDestroy;
import java.nio.charset.StandardCharsets;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/** Subscribes to the fleet simulator's MQTT topic and forwards readings to the handler. */
@Component
@ConditionalOnProperty(prefix = "cantinago.mqtt", name = "enabled", havingValue = "true")
public class MqttTelemetryAdapter {

    private static final Logger log = LoggerFactory.getLogger(MqttTelemetryAdapter.class);

    private final MqttProperties props;
    private final TelemetryHandler handler;
    private final MqttClient client;

    public MqttTelemetryAdapter(MqttProperties props, TelemetryHandler handler) throws MqttException {
        this.props = props;
        this.handler = handler;
        this.client = new MqttClient(props.brokerUrl(), props.clientId(), new MemoryPersistence());
    }

    /** Connects (and reconnects) in the background so the app starts even if the broker is down. */
    @Scheduled(initialDelay = 0, fixedDelayString = "${cantinago.mqtt.reconnect-delay:5000}")
    public synchronized void ensureConnected() {
        if (client.isConnected()) {
            return;
        }
        try {
            MqttConnectOptions options = new MqttConnectOptions();
            options.setCleanSession(true);
            options.setAutomaticReconnect(false);
            client.connect(options);
            client.subscribe(props.topic(), 1, (topic, message) ->
                    handler.handle(new String(message.getPayload(), StandardCharsets.UTF_8)));
            log.info("Subscribed to {} on {}", props.topic(), props.brokerUrl());
        } catch (MqttException e) {
            log.warn("MQTT broker {} not reachable yet: {}", props.brokerUrl(), e.getMessage());
        }
    }

    public boolean isConnected() {
        return client.isConnected();
    }

    @PreDestroy
    public void close() {
        try {
            if (client.isConnected()) {
                client.disconnect();
            }
            client.close();
        } catch (MqttException e) {
            log.debug("Error closing MQTT client", e);
        }
    }
}
