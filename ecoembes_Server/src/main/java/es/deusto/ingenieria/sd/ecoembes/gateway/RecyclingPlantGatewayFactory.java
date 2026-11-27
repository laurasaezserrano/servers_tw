package es.deusto.ingenieria.sd.ecoembes.gateway;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RecyclingPlantGatewayFactory {

	private final String plasSbBaseUrl;
	private final String contSocketHost;
	private final int contSocketPort;

	public RecyclingPlantGatewayFactory(
			@Value("${plant.plassb.base-url}") String plasSbBaseUrl,
			@Value("${plant.contsocket.host}") String contSocketHost,
			@Value("${plant.contsocket.port}") int contSocketPort) {
		this.plasSbBaseUrl = plasSbBaseUrl;
		this.contSocketHost = contSocketHost;
		this.contSocketPort = contSocketPort;
	}

	public IRecyclingPlantGateway getRecyclingPlantGateway(long plantId) {
		if (plantId == 1) {
			return new PlasSBServiceGateway(plasSbBaseUrl);
		} else if (plantId == 2) {
			return new ContSocketServiceGateway(contSocketHost, contSocketPort);
		}
		throw new IllegalArgumentException("Unknown recycling plant ID: " + plantId);
	}
}