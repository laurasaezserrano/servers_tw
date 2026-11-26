package es.deusto.ingenieria.sd.ecoembes.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

@Entity
public class Assignment {
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private LocalDate date;

	@ManyToOne
	private RecyclingPlant plant;
	
	@ManyToOne
	private Employee employee;
	
	@ManyToMany
	private List<Dumpster> dumpsters;
	
	public Assignment() {
		this.dumpsters = new ArrayList<>();
	}
	
	public Assignment(LocalDate date, RecyclingPlant plant, Employee employee, List<Dumpster> dumpsters) {
		this.date = date;
		this.plant = plant;
		this.employee = employee;
		this.dumpsters = dumpsters != null ? dumpsters : new ArrayList<>();
	}

	public Assignment(long id, LocalDate date, RecyclingPlant plant, Employee employee, List<Dumpster> dumpsters) {
		this.id = id;
		this.date = date;
		this.plant = plant;
		this.employee = employee;
		this.dumpsters = dumpsters != null ? dumpsters : new ArrayList<>();
	}
	
	public long getId() { return id; }
	public void setId(long id) { this.id = id; }
	
	public LocalDate getDate() { return date; }
	public void setDate(LocalDate date) { this.date = date; }
	
	public RecyclingPlant getPlant() { return plant; }
	public void setPlant(RecyclingPlant plant) { this.plant = plant; }

	public Employee getEmployee() { return employee; }
	public void setEmployee(Employee employee) { this.employee = employee; }

	public List<Dumpster> getDumpsters() { return dumpsters; }
	public void setDumpsters(List<Dumpster> dumpsters) {
		this.dumpsters = dumpsters != null ? dumpsters : new ArrayList<>();
	}
	
	public int getTotalDumpsters() { return dumpsters.size(); }

	public int getTotalContainers() {
		return dumpsters.stream().mapToInt(Dumpster::getContainers).sum();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		return id == ((Assignment) obj).id;
	}

	@Override
	public int hashCode() { return Objects.hash(id); }
}