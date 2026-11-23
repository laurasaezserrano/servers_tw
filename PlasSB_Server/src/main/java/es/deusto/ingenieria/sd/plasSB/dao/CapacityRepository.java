package es.deusto.ingenieria.sd.plasSB.dao;

import java.time.LocalDate;
import java.util.Optional;
import es.deusto.ingenieria.sd.plasSB.entity.Capacity;

public interface CapacityRepository extends JpaRepository<Capacity, Long> {
    Optional<Capacity> findByDate(LocalDate date);
}
