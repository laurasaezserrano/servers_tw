package es.deusto.ingenieria.sd.ecoembes.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import es.deusto.ingenieria.sd.ecoembes.dao.RecyclingPlantRepository;
import es.deusto.ingenieria.sd.ecoembes.dto.RecyclingPlantDTO;
import es.deusto.ingenieria.sd.ecoembes.entity.RecyclingPlant;
import es.deusto.ingenieria.sd.ecoembes.gateway.IRecyclingPlantGateway;
import es.deusto.ingenieria.sd.ecoembes.gateway.RecyclingPlantGatewayFactory;

@Service
public class RecyclingPlantService {

	private final RecyclingPlantRepository plantRepository;
	private final RecyclingPlantGatewayFactory gatewayFactory;

	public RecyclingPlantService(RecyclingPlantRepository plantRepository, RecyclingPlantGatewayFactory gatewayFactory) {
		this.plantRepository = plantRepository;
		this.gatewayFactory = gatewayFactory;
	}

	public List<RecyclingPlantDTO> getAllPlants() {
		List<RecyclingPlantDTO> result = new ArrayList<>();
		List<RecyclingPlant> plants = plantRepository.findAll();
		
		LocalDate today = LocalDate.now();
		for (RecyclingPlant plant : plants) {
			IRecyclingPlantGateway gateway = gatewayFactory.getRecyclingPlantGateway(plant.getId());
			float capacity = gateway != null ? gateway.getCapacity(today) : 0f;
			
			result.add(new RecyclingPlantDTO(plant.getId(), plant.getName(), plant.getLocation(), capacity, today));
		}
		return result;
	}

	public float getPlantCapacity(long plantId, LocalDate date) {
		Optional<RecyclingPlant> plantOpt = plantRepository.findById(plantId);
		if (plantOpt.isEmpty()) {
			return -1; // Plant not found
		}
		
		IRecyclingPlantGateway gateway = gatewayFactory.getRecyclingPlantGateway(plantId);
		return gateway != null ? gateway.getCapacity(date) : -1;
	}

	public RecyclingPlant findPlant(long id) {
		Optional<RecyclingPlant> plantOpt = plantRepository.findById(id);
		return plantOpt.orElse(null);
	}
}