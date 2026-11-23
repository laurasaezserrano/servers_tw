package es.deusto.ingenieria.sd.plasSB.entity;

public class Notification {
	private Long id;
	private String date;
	private int numDumpsters;
	private int totalContainers;
	
	public Notification(Long id, String date, int numDumpsters, int totalContainers) {
		this.id = id;
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

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
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
