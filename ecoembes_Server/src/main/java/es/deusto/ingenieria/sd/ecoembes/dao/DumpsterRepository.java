package es.deusto.ingenieria.sd.ecoembes.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import es.deusto.ingenieria.sd.ecoembes.entity.Dumpster;

public interface DumpsterRepository extends JpaRepository<Dumpster, Long> {
	List<Dumpster> findByPostalCode(String postalCode);
}
