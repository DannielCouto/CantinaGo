package pt.ua.cantinago.fleet.api;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import pt.ua.cantinago.fleet.FleetService;
import pt.ua.cantinago.fleet.domain.EBikeType;

@RestController
@RequestMapping("/api/stations")
public class StationController {

    private final FleetService fleet;

    public StationController(FleetService fleet) {
        this.fleet = fleet;
    }

    @GetMapping
    public List<StationDto> list() {
        return fleet.listStations().stream().map(StationDto::from).toList();
    }

    @GetMapping("/{id}/ebikes")
    public List<EBikeDto> reservableEBikes(@PathVariable long id, @RequestParam(required = false) EBikeType type) {
        if (fleet.findStation(id).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Station " + id + " not found");
        }
        return fleet.reservableEBikes(id, type).stream().map(EBikeDto::from).toList();
    }
}
