package es.deusto.ingenieria.sd.ecoembes.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Transient; 

@Entity
public class RecyclingPlant {
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String name;
	private String location;
	
	@Transient
	private Map<LocalDate, Float> capacityByDate = new HashMap<>();
	
	@Transient
	private List<Assignment> assignments = new ArrayList<>();
	
	private LocalDate date;

	//CONSTRUCTORS
	public RecyclingPlant() {
		
	}
	
	public RecyclingPlant(long id, String name, String location, float capacity, LocalDate date) {
		this.id = id;
		this.name = name;
		this.location = location;
		this.setCapacity(date, capacity);
		this.date = date;
	}
	
	public RecyclingPlant(String name, String location, float capacity, LocalDate date) {
		this.name = name;
		this.location = location;
		this.setCapacity(date, capacity);
		this.date = date;
	}

	public RecyclingPlant(String name, String location) {
		this.name = name;
		this.location = location;
	}

	public RecyclingPlant(int i, String name, String location) {
		this.id = i;
		this.name = name;
		this.location = location;		
	}

	//GETTERS AND SETTERS
	// ID
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
		
	//Name
	public String getName() { 
		return name; 
	}
	
	public void setName(String name) { 
		this.name = name; 
	}

	//Location
	public String getLocation() { 
		return location; 
	}
	
	public void setLocation(String location) { 
		this.location = location; 
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		return id == ((RecyclingPlant) obj).id;
	}

	@Override
	public int hashCode() { return Objects.hash(id); }

	public float getCapacityByDate(LocalDate date) {
		return capacityByDate.getOrDefault(date, 0f);
	}
 
	public void setCapacity(LocalDate date, float capacity) {
		this.capacityByDate.put(date, capacity);
	}
	
	public void notifyAssignment(Assignment assignment) {
        this.assignments.add(assignment);
        System.out.println("[PLANT NOTIFICATION] Plant '" + name + "' received assignment #" 
            + assignment.getId() + " with " + assignment.getTotalDumpsters() + " dumpsters.");
    }

    public List<Assignment> getAssignments() {
        return assignments;
    }
    
    public LocalDate getDate() {
    	return date;
    }
    
    public void setDate(LocalDate date) {
		this.date = date;
	}
}