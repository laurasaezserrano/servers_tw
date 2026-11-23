package es.deusto.ingenieria.sd.plasSB.entity;

import java.time.LocalDate;

public class Capacity {
    private Long id;
    private LocalDate date;
    private double capacity;
    
    public Capacity() {
	}
    
    public Capacity(Long id, LocalDate date, double capacity) {
		this.id = id;
		this.date = date;
		this.capacity = capacity;
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

	public double getCapacity() {
		return capacity;
	}

	public void setCapacityTons(double capacity) {
		this.capacity = capacity;
	}
    

}
