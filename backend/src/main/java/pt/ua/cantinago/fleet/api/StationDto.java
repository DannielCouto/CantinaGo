package pt.ua.cantinago.fleet.api;

import pt.ua.cantinago.fleet.domain.Station;

public record StationDto(Long id, String name, double latitude, double longitude, int capacity) {

    static StationDto from(Station s) {
        return new StationDto(s.getId(), s.getName(), s.getLatitude(), s.getLongitude(), s.getCapacity());
    }
}
