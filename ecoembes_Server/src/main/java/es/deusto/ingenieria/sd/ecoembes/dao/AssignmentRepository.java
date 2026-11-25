package es.deusto.ingenieria.sd.ecoembes.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import es.deusto.ingenieria.sd.ecoembes.entity.Assignment;

public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
	
}