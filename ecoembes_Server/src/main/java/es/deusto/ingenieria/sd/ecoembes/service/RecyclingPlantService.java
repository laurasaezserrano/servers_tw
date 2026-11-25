package es.deusto.ingenieria.sd.ecoembes.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import es.deusto.ingenieria.sd.ecoembes.dto.RecyclingPlantDTO;
import es.deusto.ingenieria.sd.ecoembes.entity.RecyclingPlant;
import es.deusto.ingenieria.sd.ecoembes.gateway.IRecyclingPlantGateway;
import es.deusto.ingenieria.sd.ecoembes.gateway.RecyclingPlantGatewayFactory;

@Service
public class RecyclingPlantService {

	private List<RecyclingPlant> plants = new ArrayList<>();

	public RecyclingPlantService() {
		RecyclingPlant plasSB = new RecyclingPlant(1, "PlasSB Ltd.", "Bilbao");
		RecyclingPlant contSocket = new RecyclingPlant(2, "ContSocket Ltd.", "Vitoria");

		plasSB.setDate(LocalDate.now());
		contSocket.setDate(LocalDate.now());

		plants.add(plasSB);
		plants.add(contSocket);
	}

	public List<RecyclingPlantDTO> getAllPlants() {
		List<RecyclingPlantDTO> result = new ArrayList<>();
		for (RecyclingPlant plant : plants) {
			result.add(new RecyclingPlantDTO(plant.getId(), plant.getName(), plant.getLocation(), plant.getCapacityByDate(plant.getDate()), LocalDate.now()));
		}
		return result;
	}

	public float getPlantCapacity(long plantId, LocalDate date) {
		RecyclingPlant plant = findPlant(plantId);
		if (plant == null) {
			return -1; // Plant not found
		}
		
		IRecyclingPlantGateway gateway = RecyclingPlantGatewayFactory.getRecyclingPlantGateway(plantId);
		return gateway.getCapacity(date);
	}

	public RecyclingPlant findPlant(long id) {
		for (RecyclingPlant plant : plants) {
			if (plant.getId() == id) {
				return plant;
			}
		}
		return null;
	}
}
