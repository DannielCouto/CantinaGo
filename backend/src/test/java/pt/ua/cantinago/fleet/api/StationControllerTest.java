package pt.ua.cantinago.fleet.api;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import pt.ua.cantinago.fleet.FleetService;
import pt.ua.cantinago.fleet.domain.EBike;
import pt.ua.cantinago.fleet.domain.EBikeStatus;
import pt.ua.cantinago.fleet.domain.EBikeType;
import pt.ua.cantinago.fleet.domain.Station;

@WebMvcTest(StationController.class)
class StationControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private FleetService fleet;

    @Test
    void listsStations() throws Exception {
        when(fleet.listStations()).thenReturn(List.of(new Station("Biblioteca", 40.63, -8.65, 10)));

        mvc.perform(get("/api/stations"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Biblioteca"))
                .andExpect(jsonPath("$[0].capacity").value(10));
    }

    @Test
    void listsReservableEBikesFilteredByType() throws Exception {
        Station station = new Station("Biblioteca", 40.63, -8.65, 10);
        when(fleet.findStation(1L)).thenReturn(Optional.of(station));
        when(fleet.reservableEBikes(1L, EBikeType.CARGO)).thenReturn(
                List.of(new EBike("EB-11", EBikeType.CARGO, EBikeStatus.AVAILABLE, 79, station)));

        mvc.perform(get("/api/stations/1/ebikes").param("type", "CARGO"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].code").value("EB-11"))
                .andExpect(jsonPath("$[0].battery").value(79));
    }

    @Test
    void unknownStationIs404() throws Exception {
        when(fleet.findStation(99L)).thenReturn(Optional.empty());

        mvc.perform(get("/api/stations/99/ebikes"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404));
    }
}
