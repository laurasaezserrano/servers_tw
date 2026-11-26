package es.deusto.ingenieria.sd.ecoembes.gateway;

import org.springframework.stereotype.Component;

@Component
public class RecyclingPlantGatewayFactory {

	private final PlasSBServiceGateway plasSBGateway;
	private final ContSocketServiceGateway contSocketGateway;

	public RecyclingPlantGatewayFactory(PlasSBServiceGateway plasSBGateway, ContSocketServiceGateway contSocketGateway) {
		this.plasSBGateway = plasSBGateway;
		this.contSocketGateway = contSocketGateway;
	}

	public IRecyclingPlantGateway getRecyclingPlantGateway(long plantId) {
		if (plantId == 1) {
			return plasSBGateway;
		} else if (plantId == 2) {
			return contSocketGateway;
		}
		throw new IllegalArgumentException("Unknown recycling plant ID: " + plantId);
	}
}