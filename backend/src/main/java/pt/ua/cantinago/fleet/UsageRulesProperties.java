package pt.ua.cantinago.fleet;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Usage rules defined by the institutional client (Product Specs, 3.2). For now they come from
 * configuration; US7.14 will move them to the database so the backoffice can edit them.
 *
 * @param minBattery BR1 minimum battery (%) to reserve or unlock an e-bike
 */
@ConfigurationProperties(prefix = "cantinago.rules")
public record UsageRulesProperties(int minBattery) {}
