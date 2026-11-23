package es.deusto.ingenieria.sd.plasSB.dto;

import java.time.LocalDate;

public class NotificationDTO {
	private LocalDate date;
    private int numDumpsters;
    private int totalContainers;
    
    public NotificationDTO(LocalDate date, int numDumpsters, int totalContainers) {
    	this.date = date;
    	this.numDumpsters = numDumpsters;
    	this.totalContainers = totalContainers;
    }
    
    public LocalDate getDate() {
    	return date;
    }
    
    public int getNumDumpsters() {
    	return numDumpsters;
    }
    
    public int getTotalContainers() {
		return totalContainers;
	}
    
    public void setDate(LocalDate date) {
		this.date = date;
	}
	
	public void setNumDumpsters(int numDumpsters) {
		this.numDumpsters = numDumpsters;
	}
	
	public void setTotalContainers(int totalContainers) {
		this.totalContainers = totalContainers;
	}
	
	public String toString() {
		return "NotificationDTO [date=" + date + ", numDumpsters=" + numDumpsters + ", totalContainers=" + totalContainers + "]";
	}

}
