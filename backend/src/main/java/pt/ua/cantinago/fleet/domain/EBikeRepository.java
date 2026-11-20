package pt.ua.cantinago.fleet.domain;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EBikeRepository extends JpaRepository<EBike, Long> {

    Optional<EBike> findByCode(String code);

    List<EBike> findByStationIdOrderByCode(Long stationId);

    @Query("select b.station.id from EBike b where b.code = :code")
    Long findStationIdByCode(String code);
}
