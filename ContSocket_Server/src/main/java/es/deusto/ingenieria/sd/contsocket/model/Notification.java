package es.deusto.ingenieria.sd.contsocket.model;

import java.time.LocalDate;

public record Notification(LocalDate date, int totalDumpsters, int totalContainers) { }