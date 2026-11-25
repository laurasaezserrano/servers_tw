package es.deusto.ingenieria.sd.ecoembes.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

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

	private DumpsterService dumpsterService;
	private RecyclingPlantService plantService;

	private List<Assignment> assignments = new ArrayList<>();
	private long nextId = 1;

	public AssignmentService(DumpsterService dumpsterService, RecyclingPlantService plantService) {
		this.dumpsterService = dumpsterService;
		this.plantService = plantService;
	}

	public boolean assignDumpstersToPlant(long plantId, List<Long> dumpsterIds, Employee employee) {
		RecyclingPlant plant = plantService.findPlant(plantId);
	    if (plant == null || dumpsterIds == null || dumpsterIds.isEmpty()) {
	        return false;
	    }

	    List<Dumpster> selected = new ArrayList<>();
	    for (Long id : dumpsterIds) {
	        Dumpster dumpster = dumpsterService.findDumpster(id);
	        if (dumpster == null) {
	            return false;
	        }
	        selected.add(dumpster);
	    }

	    LocalDate today = LocalDate.now();
	    IRecyclingPlantGateway gateway = RecyclingPlantGatewayFactory.getRecyclingPlantGateway(plantId);
	    float capacity = gateway.getCapacity(today);
	    Assignment assignment = new Assignment(nextId, today, plant, employee, selected);
	    float neededTons = assignment.getTotalContainers() * TONS_PER_CONTAINER;

	    // Check if plant has enough capacity
	    if (neededTons > capacity) {
	        return false;
	    }

	 // Notify external plant server and record assignment
	    gateway.notifyAssignment(selected.size(), assignment.getTotalContainers(), today);
	    assignments.add(assignment);
	    nextId++;

	    return true;
	}
}
