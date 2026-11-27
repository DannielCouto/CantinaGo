package pt.ua.cantinago.fleet.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.Instant;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class EBikeTest {

    private static final int MIN_BATTERY = 20;

    @ParameterizedTest(name = "{0} with {1}% -> reservable={2}")
    @CsvSource({
        "AVAILABLE,   20, true",
        "AVAILABLE,   19, false",
        "AVAILABLE,  100, true",
        "RESERVED,    90, false",
        "IN_USE,      90, false",
        "MAINTENANCE, 90, false",
        "RETIRED,     90, false"
    })
    void br1ReservableOnlyWhenAvailableAndAboveMinimumBattery(EBikeStatus status, int battery, boolean expected) {
        EBike bike = new EBike("EB-07", EBikeType.NORMAL, status, battery, null);

        assertThat(bike.isReservable(MIN_BATTERY)).isEqualTo(expected);
    }

    @Test
    void telemetryUpdatesBatteryAndKeepsUnreportedFields() {
        EBike bike = new EBike("EB-07", EBikeType.NORMAL, EBikeStatus.AVAILABLE, 80, null);
        Instant at = Instant.parse("2026-11-20T10:00:00Z");

        bike.applyTelemetry(42, null, null, null, at);

        assertThat(bike.getBattery()).isEqualTo(42);
        assertThat(bike.isLocked()).isTrue();
        assertThat(bike.getLatitude()).isNull();
        assertThat(bike.getLastTelemetryAt()).isEqualTo(at);
    }

    @Test
    void telemetryUpdatesPositionAndLockWhenReported() {
        EBike bike = new EBike("EB-07", EBikeType.NORMAL, EBikeStatus.AVAILABLE, 80, null);

        bike.applyTelemetry(80, 40.63, -8.65, false, Instant.EPOCH);

        assertThat(bike.getLatitude()).isEqualTo(40.63);
        assertThat(bike.getLongitude()).isEqualTo(-8.65);
        assertThat(bike.isLocked()).isFalse();
    }

    @Test
    void telemetryRejectsBatteryOutOfRange() {
        EBike bike = new EBike("EB-07", EBikeType.NORMAL, EBikeStatus.AVAILABLE, 80, null);

        assertThatThrownBy(() -> bike.applyTelemetry(101, null, null, null, Instant.EPOCH))
                .isInstanceOf(IllegalArgumentException.class);
        assertThat(bike.getBattery()).isEqualTo(80);
    }
}
