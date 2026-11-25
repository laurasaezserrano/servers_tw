package es.deusto.ingenieria.sd.ecoembes.gateway;

public class RecyclingPlantGatewayFactory {

	public static IRecyclingPlantGateway getRecyclingPlantGateway(long plantId) {
		if (plantId == 1) {
			return new PlasSBServiceGateway();
		} else if (plantId == 2) {
			return new ContSocketServiceGateway();
		}
		throw new IllegalArgumentException("Unknown recycling plant ID: " + plantId);
	}
}