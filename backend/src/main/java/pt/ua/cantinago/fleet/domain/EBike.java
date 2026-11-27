package pt.ua.cantinago.fleet.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.Instant;

@Entity
@Table(name = "ebikes")
public class EBike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;

    @Enumerated(EnumType.STRING)
    private EBikeType type;

    @Enumerated(EnumType.STRING)
    private EBikeStatus status;

    private int battery;
    private Double latitude;
    private Double longitude;
    private boolean locked;
    private Instant lastTelemetryAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "station_id")
    private Station station;

    protected EBike() {
        // JPA
    }

    public EBike(String code, EBikeType type, EBikeStatus status, int battery, Station station) {
        this.code = code;
        this.type = type;
        this.status = status;
        this.battery = battery;
        this.station = station;
        this.locked = true;
    }

    /**
     * BR1: an e-bike can only be reserved or unlocked when it is available and its battery is at
     * or above the minimum.
     */
    public boolean isReservable(int minBattery) {
        return status == EBikeStatus.AVAILABLE && battery >= minBattery;
    }

    /** Applies a telemetry reading. Null fields mean "not reported" and keep the current value. */
    public void applyTelemetry(int battery, Double latitude, Double longitude, Boolean locked, Instant at) {
        if (battery < 0 || battery > 100) {
            throw new IllegalArgumentException("battery must be between 0 and 100, got " + battery);
        }
        this.battery = battery;
        if (latitude != null && longitude != null) {
            this.latitude = latitude;
            this.longitude = longitude;
        }
        if (locked != null) {
            this.locked = locked;
        }
        this.lastTelemetryAt = at;
    }

    public Long getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public EBikeType getType() {
        return type;
    }

    public EBikeStatus getStatus() {
        return status;
    }

    public void setStatus(EBikeStatus status) {
        this.status = status;
    }

    public int getBattery() {
        return battery;
    }

    public void setBattery(int battery) {
        this.battery = battery;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public boolean isLocked() {
        return locked;
    }

    public Instant getLastTelemetryAt() {
        return lastTelemetryAt;
    }

    public Station getStation() {
        return station;
    }
}
