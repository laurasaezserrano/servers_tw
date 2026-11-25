package es.deusto.ingenieria.sd.ecoembes.gateway;

import java.time.LocalDate;

public interface IRecyclingPlantGateway {
	float getCapacity(LocalDate date);
	
	void notifyAssignment(int totalDumpsters, int totalContainers, LocalDate date);
	

}
