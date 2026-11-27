package pt.ua.cantinago.fleet.domain;

/** E-bike lifecycle states (Product Specs, section 3.3). */
public enum EBikeStatus {
    AVAILABLE,
    RESERVED,
    IN_USE,
    MAINTENANCE,
    RETIRED
}
