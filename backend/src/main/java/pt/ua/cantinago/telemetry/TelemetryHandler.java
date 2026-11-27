package pt.ua.cantinago.telemetry;

import java.time.Clock;
import java.time.Instant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import pt.ua.cantinago.fleet.FleetService;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.json.JsonMapper;

/**
 * Transport-independent entry point for telemetry. The MQTT adapter hands raw payloads here, so a
 * different transport only needs a new adapter (Product Specs, 4.1).
 */
@Component
public class TelemetryHandler {

    private static final Logger log = LoggerFactory.getLogger(TelemetryHandler.class);

    private final JsonMapper json;
    private final FleetService fleet;
    private final Clock clock;

    public TelemetryHandler(JsonMapper json, FleetService fleet, Clock clock) {
        this.json = json;
        this.fleet = fleet;
        this.clock = clock;
    }

    /** Returns true when the reading was valid and applied. Bad payloads are logged and dropped. */
    public boolean handle(String payload) {
        TelemetryReading reading;
        try {
            reading = json.readValue(payload, TelemetryReading.class);
        } catch (JacksonException e) {
            log.warn("Dropping malformed telemetry payload: {}", e.getOriginalMessage());
            return false;
        }
        if (reading.code() == null || reading.battery() == null
                || reading.battery() < 0 || reading.battery() > 100) {
            log.warn("Dropping invalid telemetry reading: {}", reading);
            return false;
        }
        Instant at = reading.timestamp() != null ? reading.timestamp() : clock.instant();
        return fleet.applyTelemetry(
                reading.code(), reading.battery(), reading.lat(), reading.lon(), reading.locked(), at);
    }
}
