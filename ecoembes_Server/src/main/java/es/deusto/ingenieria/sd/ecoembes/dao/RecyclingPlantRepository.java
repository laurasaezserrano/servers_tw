package es.deusto.ingenieria.sd.ecoembes.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import es.deusto.ingenieria.sd.ecoembes.entity.RecyclingPlant;

public interface RecyclingPlantRepository extends JpaRepository<RecyclingPlant, Long> {
	
}

