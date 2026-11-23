package es.deusto.ingenieria.sd.plasSB.entity;

import java.time.LocalDate;

public class Capacity {
    private Long id;
    private LocalDate date;
    private double capacityTons;
    
    public Capacity(Long id, LocalDate date, double capacityTons) {
		this.id = id;
		this.date = date;
		this.capacityTons = capacityTons;
	}
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public double getCapacityTons() {
		return capacityTons;
	}

	public void setCapacityTons(double capacityTons) {
		this.capacityTons = capacityTons;
	}
    

}
