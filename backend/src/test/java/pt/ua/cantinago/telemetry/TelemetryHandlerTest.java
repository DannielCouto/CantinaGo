package pt.ua.cantinago.telemetry;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import pt.ua.cantinago.fleet.FleetService;
import tools.jackson.databind.json.JsonMapper;

class TelemetryHandlerTest {

    private static final Instant NOW = Instant.parse("2026-11-20T12:00:00Z");

    private FleetService fleet;
    private TelemetryHandler handler;

    @BeforeEach
    void setUp() {
        fleet = mock(FleetService.class);
        handler = new TelemetryHandler(JsonMapper.builder().build(), fleet, Clock.fixed(NOW, ZoneOffset.UTC));
    }

    @Test
    void appliesValidReading() {
        when(fleet.applyTelemetry("EB-07", 18, null, null, true, Instant.parse("2026-11-20T10:00:00Z")))
                .thenReturn(true);

        boolean applied = handler.handle(
                "{\"code\":\"EB-07\",\"battery\":18,\"locked\":true,\"timestamp\":\"2026-11-20T10:00:00Z\"}");

        assertThat(applied).isTrue();
    }

    @Test
    void usesClockWhenTimestampMissing() {
        when(fleet.applyTelemetry("EB-07", 50, 40.6, -8.6, null, NOW)).thenReturn(true);

        assertThat(handler.handle("{\"code\":\"EB-07\",\"battery\":50,\"lat\":40.6,\"lon\":-8.6}")).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "not json",
        "{\"battery\":50}",
        "{\"code\":\"EB-07\"}",
        "{\"code\":\"EB-07\",\"battery\":-1}",
        "{\"code\":\"EB-07\",\"battery\":101}"
    })
    void dropsInvalidPayloads(String payload) {
        assertThat(handler.handle(payload)).isFalse();
        verify(fleet, never()).applyTelemetry(anyString(), anyInt(), any(), any(), any(), any());
    }
}
