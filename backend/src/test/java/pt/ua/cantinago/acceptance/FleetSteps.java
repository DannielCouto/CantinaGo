package pt.ua.cantinago.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

import io.cucumber.java.After;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClient;
import pt.ua.cantinago.fleet.domain.EBike;
import pt.ua.cantinago.fleet.domain.EBikeRepository;
import pt.ua.cantinago.fleet.domain.EBikeStatus;
import pt.ua.cantinago.telemetry.MqttTelemetryAdapter;

public class FleetSteps {

    private static final Map<String, EBikeStatus> STATUS = Map.of(
            "disponível", EBikeStatus.AVAILABLE,
            "reservada", EBikeStatus.RESERVED,
            "em uso", EBikeStatus.IN_USE,
            "manutenção", EBikeStatus.MAINTENANCE,
            "retirada", EBikeStatus.RETIRED);

    @LocalServerPort
    private int port;

    @Autowired
    private EBikeRepository ebikes;

    @Autowired
    private MqttTelemetryAdapter adapter;

    private final Map<String, EBikeSnapshot> original = new HashMap<>();

    private record EBikeSnapshot(EBikeStatus status, int battery) {}

    @Dado("que a e-bike {string} está {string} com {int} % de bateria")
    public void eBikeIsWithBattery(String code, String status, int battery) {
        EBike bike = ebikes.findByCode(code).orElseThrow();
        original.putIfAbsent(code, new EBikeSnapshot(bike.getStatus(), bike.getBattery()));
        bike.setStatus(STATUS.get(status));
        bike.setBattery(battery);
        ebikes.save(bike);
    }

    @Quando("o simulador envia uma leitura de {int} % para a e-bike {string}")
    public void simulatorSendsReading(int battery, String code) throws MqttException {
        await().atMost(Duration.ofSeconds(10)).until(adapter::isConnected);
        MqttClient publisher = new MqttClient(
                CucumberSpringConfiguration.brokerUrl(), "test-" + UUID.randomUUID(), new MemoryPersistence());
        publisher.connect();
        String payload = "{\"code\":\"%s\",\"battery\":%d,\"locked\":true}".formatted(code, battery);
        publisher.publish("cantinago/telemetry/" + code, payload.getBytes(StandardCharsets.UTF_8), 1, false);
        publisher.disconnect();
        publisher.close();
        // SLO2: state is updated less than 5 s after the reading.
        await().atMost(Duration.ofSeconds(5))
                .until(() -> ebikes.findByCode(code).orElseThrow().getBattery() == battery);
    }

    @Entao("a e-bike {string} aparece nas e-bikes reserváveis da estação")
    public void eBikeIsListed(String code) {
        assertThat(reservableCodesAtStationOf(code)).contains(code);
    }

    @Entao("a e-bike {string} não aparece nas e-bikes reserváveis da estação")
    public void eBikeIsNotListed(String code) {
        assertThat(reservableCodesAtStationOf(code)).doesNotContain(code);
    }

    @After
    public void restoreSeedData() {
        original.forEach((code, snap) -> {
            EBike bike = ebikes.findByCode(code).orElseThrow();
            bike.setStatus(snap.status());
            bike.setBattery(snap.battery());
            ebikes.save(bike);
        });
    }

    private List<String> reservableCodesAtStationOf(String code) {
        Long stationId = ebikes.findStationIdByCode(code);
        List<Map<String, Object>> body = RestClient.create("http://localhost:" + port)
                .get()
                .uri("/api/stations/{id}/ebikes", stationId)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});
        return body.stream().map(m -> (String) m.get("code")).toList();
    }
}
