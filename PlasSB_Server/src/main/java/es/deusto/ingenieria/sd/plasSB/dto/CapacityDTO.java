package es.deusto.ingenieria.sd.plasSB.dto;

import java.time.LocalDate;

public class CapacityDTO {
	private LocalDate date;
    private double capacityTons; 
    
    public CapacityDTO(LocalDate date, double capacityTons) {
		this.date = date;
		this.capacityTons = capacityTons;
	}
    
    public LocalDate getDate() {
		return date;
	}
    	
    public double getCapacityTons() {
		return capacityTons;
	}
    
    public void setDate(LocalDate date) {
  		this.date = date;
    }
    
    public void setCapacityTons(double capacityTons) {
    	this.capacityTons = capacityTons;
    }
    
    public String toString() {
		return "CapacityDTO [date=" + date + ", capacityTons=" + capacityTons + "]";
	}
}
