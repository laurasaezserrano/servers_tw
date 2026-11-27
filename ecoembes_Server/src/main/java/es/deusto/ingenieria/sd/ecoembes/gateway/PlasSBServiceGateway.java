package es.deusto.ingenieria.sd.ecoembes.gateway;

import java.time.LocalDate;
import java.util.Map;

import org.springframework.web.client.RestTemplate;

public class PlasSBServiceGateway implements IRecyclingPlantGateway {

	private final String baseUrl;
	private final RestTemplate restTemplate = new RestTemplate();

	public PlasSBServiceGateway(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	@Override
	public float getCapacity(LocalDate date) {
		try {
			Map<?, ?> response = restTemplate.getForObject(baseUrl + "/capacity?date=" + date, Map.class);
			if (response == null || response.get("capacity") == null) {
				throw new IllegalStateException("Empty response from PlasSB");
			}
			return ((Number) response.get("capacity")).floatValue();
		} catch (Exception e) {
			throw new IllegalStateException("PlasSB not available: " + e.getMessage(), e);
		}
	}

	@Override
	public void notifyAssignment(int totalDumpsters, int totalContainers, LocalDate date) {
		try {
			Map<String, Object> body = Map.of(
					"date", date.toString(),
					"numDumpsters", totalDumpsters,
					"totalContainers", totalContainers);
			restTemplate.postForEntity(baseUrl + "/notifications", body, Void.class);
		} catch (Exception e) {
			throw new IllegalStateException("PlasSB not available: " + e.getMessage(), e);
		}
	}
}