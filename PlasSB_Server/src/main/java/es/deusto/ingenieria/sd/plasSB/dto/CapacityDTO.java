package es.deusto.ingenieria.sd.plasSB.dto;

import java.time.LocalDate;

public class CapacityDTO {
	private LocalDate date;
    private double capacity; 
    
    public CapacityDTO() {
    	
    }
    
    public CapacityDTO(LocalDate date, double capacity) {
		this.date = date;
		this.capacity = capacity;
	}
    
    public LocalDate getDate() {
		return date;
	}
    	
    public double getCapacity() {
		return capacity;
	}
    
    public void setDate(LocalDate date) {
  		this.date = date;
    }
    
    public void setCapacity(double capacity) {
    	this.capacity = capacity;
    }
    
    public String toString() {
		return "CapacityDTO [date=" + date + ", capacity=" + capacity + "]";
	}
}
