package pt.ua.cantinago.telemetry;

import java.time.Instant;

/**
 * A reading published by the fleet simulator. Position and lock are optional.
 *
 * <p>Payload example: {"code":"EB-07","battery":64,"locked":true,"timestamp":"2026-11-20T10:00:00Z"}
 */
public record TelemetryReading(
        String code, Integer battery, Double lat, Double lon, Boolean locked, Instant timestamp) {}
