package es.deusto.ingenieria.sd.plasSB.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Capacity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
    private LocalDate date;	
    private double capacity;
    
    public Capacity() {
	}
    
    public Capacity(LocalDate date, double capacity) {
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

	public void setCapacity(double capacity) {
		this.capacity = capacity;
	}
    

}
