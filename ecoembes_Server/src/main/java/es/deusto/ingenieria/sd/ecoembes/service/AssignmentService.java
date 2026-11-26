package es.deusto.ingenieria.sd.ecoembes.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import es.deusto.ingenieria.sd.ecoembes.dao.AssignmentRepository;
import es.deusto.ingenieria.sd.ecoembes.dao.DumpsterRepository;
import es.deusto.ingenieria.sd.ecoembes.dao.RecyclingPlantRepository;
import es.deusto.ingenieria.sd.ecoembes.entity.Assignment;
import es.deusto.ingenieria.sd.ecoembes.entity.Dumpster;
import es.deusto.ingenieria.sd.ecoembes.entity.Employee;
import es.deusto.ingenieria.sd.ecoembes.entity.RecyclingPlant;
import es.deusto.ingenieria.sd.ecoembes.gateway.IRecyclingPlantGateway;
import es.deusto.ingenieria.sd.ecoembes.gateway.RecyclingPlantGatewayFactory;

@Service
public class AssignmentService {

	// Simulation:Suppose that each weighs 0.1 tons (Claude idea for the simulation)
	private static final float TONS_PER_CONTAINER = 0.1f;

	private final DumpsterRepository dumpsterRepository;
	private final RecyclingPlantRepository plantRepository;
	private final AssignmentRepository assignmentRepository;
	private final RecyclingPlantGatewayFactory gatewayFactory;

	public AssignmentService(DumpsterRepository dumpsterRepository, 
			RecyclingPlantRepository plantRepository, 
			AssignmentRepository assignmentRepository,
			RecyclingPlantGatewayFactory gatewayFactory) {
		this.dumpsterRepository = dumpsterRepository;
		this.plantRepository = plantRepository;
		this.assignmentRepository = assignmentRepository;
		this.gatewayFactory = gatewayFactory;
	}

	public boolean assignDumpstersToPlant(long plantId, List<Long> dumpsterIds, Employee employee) {
		// CORREGIDO: Búsqueda de la planta mediante el repositorio JPA
		Optional<RecyclingPlant> plantOpt = plantRepository.findById(plantId);
	    if (plantOpt.isEmpty() || dumpsterIds == null || dumpsterIds.isEmpty()) {
	        return false;
	    }
	    RecyclingPlant plant = plantOpt.get();

	    // CORREGIDO: Búsqueda de los contenedores utilizando el repositorio JPA
	    List<Dumpster> selected = new ArrayList<>();
	    for (Long id : dumpsterIds) {
	        Optional<Dumpster> dumpsterOpt = dumpsterRepository.findById(id);
	        if (dumpsterOpt.isEmpty()) {
	            return false;
	        }
	        selected.add(dumpsterOpt.get());
	    }

	    LocalDate today = LocalDate.now();
	    
	    IRecyclingPlantGateway gateway = gatewayFactory.getRecyclingPlantGateway(plantId);
	    float capacity = gateway.getCapacity(today);
	    
	    Assignment assignment = new Assignment(today, plant, employee, selected);
	    float neededTons = assignment.getTotalContainers() * TONS_PER_CONTAINER;

	    if (neededTons > capacity) {
	        return false;
	    }

	    gateway.notifyAssignment(selected.size(), assignment.getTotalContainers(), today);
	    
	    assignmentRepository.save(assignment);

	    return true;
	}
}
