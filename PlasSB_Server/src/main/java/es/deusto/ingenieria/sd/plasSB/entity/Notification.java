package es.deusto.ingenieria.sd.plasSB.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Notification {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private LocalDate date;
	private int numDumpsters;
	private int totalContainers;
	
	public Notification() {
	}
	
	public Notification(Long id, LocalDate date, int numDumpsters, int totalContainers) {
		this.id = id;
		this.date = date;
		this.numDumpsters = numDumpsters;
		this.totalContainers = totalContainers;
	}
	
	public Notification(LocalDate date, int numDumpsters, int totalContainers) {
		this.date = date;
		this.numDumpsters = numDumpsters;
		this.totalContainers = totalContainers;
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

	public int getNumDumpsters() {
		return numDumpsters;
	}

	public void setNumDumpsters(int numDumpsters) {
		this.numDumpsters = numDumpsters;
	}

	public int getTotalContainers() {
		return totalContainers;
	}

	public void setTotalContainers(int totalContainers) {
		this.totalContainers = totalContainers;
	}
	
}
