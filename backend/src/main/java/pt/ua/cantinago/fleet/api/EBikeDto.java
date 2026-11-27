package pt.ua.cantinago.fleet.api;

import pt.ua.cantinago.fleet.domain.EBike;
import pt.ua.cantinago.fleet.domain.EBikeStatus;
import pt.ua.cantinago.fleet.domain.EBikeType;

public record EBikeDto(String code, EBikeType type, EBikeStatus status, int battery) {

    static EBikeDto from(EBike b) {
        return new EBikeDto(b.getCode(), b.getType(), b.getStatus(), b.getBattery());
    }
}
