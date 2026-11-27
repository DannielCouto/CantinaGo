package pt.ua.cantinago.fleet;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pt.ua.cantinago.fleet.domain.EBike;
import pt.ua.cantinago.fleet.domain.EBikeRepository;
import pt.ua.cantinago.fleet.domain.EBikeType;
import pt.ua.cantinago.fleet.domain.Station;
import pt.ua.cantinago.fleet.domain.StationRepository;

@Service
public class FleetService {

    private static final Logger log = LoggerFactory.getLogger(FleetService.class);

    private final StationRepository stations;
    private final EBikeRepository ebikes;
    private final UsageRulesProperties rules;

    public FleetService(StationRepository stations, EBikeRepository ebikes, UsageRulesProperties rules) {
        this.stations = stations;
        this.ebikes = ebikes;
        this.rules = rules;
    }

    @Transactional(readOnly = true)
    public List<Station> listStations() {
        return stations.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Station> findStation(long id) {
        return stations.findById(id);
    }

    /** US7.4: e-bikes a courier can pick up at a station (BR1), optionally filtered by type. */
    @Transactional(readOnly = true)
    public List<EBike> reservableEBikes(long stationId, EBikeType type) {
        return ebikes.findByStationIdOrderByCode(stationId).stream()
                .filter(b -> b.isReservable(rules.minBattery()))
                .filter(b -> type == null || b.getType() == type)
                .toList();
    }

    /** US7.9: telemetry keeps the fleet state in line with reality. Unknown codes are ignored. */
    @Transactional
    public boolean applyTelemetry(String code, int battery, Double lat, Double lon, Boolean locked, Instant at) {
        Optional<EBike> bike = ebikes.findByCode(code);
        if (bike.isEmpty()) {
            log.warn("Telemetry for unknown e-bike {}", code);
            return false;
        }
        bike.get().applyTelemetry(battery, lat, lon, locked, at);
        return true;
    }
}
