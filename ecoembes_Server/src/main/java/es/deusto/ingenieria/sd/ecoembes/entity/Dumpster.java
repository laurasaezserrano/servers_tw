package es.deusto.ingenieria.sd.ecoembes.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Dumpster {
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String location;     
	private String postalCode;
	private int capacity;         
	private int containers;      
	
	@Enumerated(EnumType.STRING)
	private FillLevel fillLevel;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<DumpsterUsage> usageHistory;
	
	public Dumpster() {
		this.usageHistory = new ArrayList<>();
		this.fillLevel = FillLevel.GREEN;
	}

	public Dumpster(String location, String postalCode, int capacity) {
		this();
		this.location = location;
		this.postalCode = postalCode;
		this.capacity = capacity;
	}
	
	public Dumpster(long id, String location, String postalCode, int capacity) {
		this();
		this.id = id;
		this.location = location;
		this.postalCode = postalCode;
		this.capacity = capacity;
	}
	
	public long getId() { return id; }
	public void setId(long id) { this.id = id; }
	
	public String getLocation() { return location; }
	public void setLocation(String location) { this.location = location; }
	
	public String getPostalCode() { return postalCode; }
	public void setPostalCode(String postalCode) { this.postalCode = postalCode; }

	public int getCapacity() { return capacity; }
	public void setCapacity(int capacity) { this.capacity = capacity; }

	public int getContainers() { return containers; }
	public void setContainers(int containers) { this.containers = containers; }

	public FillLevel getFillLevel() { return fillLevel; }
	public void setFillLevel(FillLevel fillLevel) { this.fillLevel = fillLevel; }

	public List<DumpsterUsage> getUsageHistory() { return usageHistory; }
	public void setUsageHistory(List<DumpsterUsage> usageHistory) {
		this.usageHistory = usageHistory != null ? usageHistory : new ArrayList<>();
	}
	
	public void update(LocalDate date, int containers, FillLevel fillLevel) {
		this.containers = containers;
		this.fillLevel = fillLevel;
		this.usageHistory.add(new DumpsterUsage(date, containers, fillLevel));
	}

	public List<DumpsterUsage> getUsageBetween(LocalDate start, LocalDate end) {
		return usageHistory.stream()
				.filter(u -> !u.getDate().isBefore(start) && !u.getDate().isAfter(end))
				.toList();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		return id == ((Dumpster) obj).id;
	}

	@Override
	public int hashCode() { return Objects.hash(id); }

	public int getNumContainers() { return containers; }

	public FillLevel getFillLevelByDate(LocalDate date) {
		for (DumpsterUsage usage : usageHistory) {
			if (usage.getDate().equals(date)) {
				return usage.getFillLevel();
			}
		}
		return FillLevel.GREEN;
	}
}