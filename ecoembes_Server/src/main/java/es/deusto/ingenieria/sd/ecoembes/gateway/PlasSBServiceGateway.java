package es.deusto.ingenieria.sd.ecoembes.gateway;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.client.RestTemplate;

public class PlasSBServiceGateway implements IRecyclingPlantGateway {
	
	private static final String BASE_URL = "http://localhost:8081/api/plassb";
	private final RestTemplate restTemplate = new RestTemplate();
	
	@Override
	public float getCapacity(LocalDate date) {
		try {
			String url = BASE_URL + "/capacity?date=" + date.toString();
			Float capacity = restTemplate.getForObject(url, Float.class);
			return capacity != null ? capacity : 0f;
		} catch (Exception e) {
			System.err.println("Error getting capacity from PlasSB: " + e.getMessage());
			return 0f;
		}
	}

	@Override
	public void notifyAssignment(int totalDumpsters, int totalContainers, LocalDate date) {
		try {
			String url = BASE_URL + "/assignments";
			Map<String, Object> body = new HashMap<>();
			body.put("totalDumpsters", totalDumpsters);
			body.put("totalContainers", totalContainers);
			body.put("date", date.toString());

			restTemplate.postForEntity(url, body, Void.class);
		} catch (Exception e) {
			System.err.println("Error notifying assignment to PlasSB: " + e.getMessage());
		}
		
	}
	

}
