package pt.ua.cantinago.acceptance;

import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.postgresql.PostgreSQLContainer;
import org.testcontainers.utility.MountableFile;

/** Boots the whole backend against real PostgreSQL and Mosquitto containers. */
@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CucumberSpringConfiguration {

    static final PostgreSQLContainer POSTGRES = new PostgreSQLContainer("postgres:16-alpine");

    static final GenericContainer<?> MOSQUITTO = new GenericContainer<>("eclipse-mosquitto:2")
            .withCopyFileToContainer(
                    MountableFile.forClasspathResource("mosquitto.conf"), "/mosquitto/config/mosquitto.conf")
            .withExposedPorts(1883);

    static {
        POSTGRES.start();
        MOSQUITTO.start();
    }

    static String brokerUrl() {
        return "tcp://" + MOSQUITTO.getHost() + ":" + MOSQUITTO.getMappedPort(1883);
    }

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", POSTGRES::getJdbcUrl);
        registry.add("spring.datasource.username", POSTGRES::getUsername);
        registry.add("spring.datasource.password", POSTGRES::getPassword);
        registry.add("cantinago.mqtt.enabled", () -> "true");
        registry.add("cantinago.mqtt.broker-url", CucumberSpringConfiguration::brokerUrl);
        registry.add("cantinago.mqtt.reconnect-delay", () -> "500");
    }
}
