package es.deusto.ingenieria.sd.ecoembes.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class DumpsterUsage {
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private LocalDate date;
	private int containers;
	private FillLevel fillLevel;

	//CONSTRUCTORS
	public DumpsterUsage() { }

	public DumpsterUsage(LocalDate date, int containers, FillLevel fillLevel) {
		this.date = date;
		this.containers = containers;
		this.fillLevel = fillLevel;
	}
	
	public DumpsterUsage(long id, LocalDate date, int containers, FillLevel fillLevel) {
		this.id = id;
		this.date = date;
		this.containers = containers;
		this.fillLevel = fillLevel;
	}

	//GETTERS AND SETTERS
	//Date
	public LocalDate getDate() { 
		return date; 
		}
	
	public void setDate(LocalDate date) { 
		this.date = date; 
		}
	
	//Containers
	public int getContainers() { 
		return containers;
		}
	
	public void setContainers(int containers) { 
		this.containers = containers; 
		}

	//FillLevel
	public FillLevel getFillLevel() { 
		return fillLevel; 
		}
	
	public void setFillLevel(FillLevel fillLevel) { 
		this.fillLevel = fillLevel; 
		}
}
