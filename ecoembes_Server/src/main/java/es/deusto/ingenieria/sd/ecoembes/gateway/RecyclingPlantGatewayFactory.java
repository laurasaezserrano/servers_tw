package es.deusto.ingenieria.sd.ecoembes.gateway;

public class RecyclingPlantGatewayFactory {
	
	public static IRecyclingPlantGateway getRecyclingPlantGateway() {
		if (System.getenv("RECYCLING_PLANT_GATEWAY") != null) {
			String gateway = System.getenv("RECYCLING_PLANT_GATEWAY");
			if (gateway.equals("PlasSB")) {
				return new PlasSBServiceGateway();
			} else if (gateway.equals("ContSocket")) {
				return new ContSocketServiceGateway();
			}
		}
		return null;
	}

}
