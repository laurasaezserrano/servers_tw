package es.deusto.ingenieria.sd.ecoembes.gateway;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class PlasSBServiceGateway implements IRecyclingPlantGateway {
	
	@Value("${plassb.base-url}")
	private String baseUrl;
	
	private final RestTemplate restTemplate = new RestTemplate();
	
	@Override
	public float getCapacity(LocalDate date) {
		try {
			String url = baseUrl + "/capacity?date=" + date.toString();
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
			String url = baseUrl + "/assignments";
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