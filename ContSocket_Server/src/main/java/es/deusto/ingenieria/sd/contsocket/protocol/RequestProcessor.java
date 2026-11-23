package es.deusto.ingenieria.sd.contsocket.protocol;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

import es.deusto.ingenieria.sd.contsocket.data.CapacityStore;
import es.deusto.ingenieria.sd.contsocket.model.Notification;

public class RequestProcessor {

    private static final String SEPARATOR = ";";

    private final CapacityStore store;

    public RequestProcessor(CapacityStore store) {
        this.store = store;
    }

    /** Processes one request line and always returns a response line (never throws). */
    public String process(String request) {
        if (request == null || request.isBlank()) {
            return "ERROR;BAD_REQUEST";
        }

        String[] parts = request.trim().split(SEPARATOR);

        try {
            switch (parts[0]) {
                case "GET_CAPACITY":
                    return getCapacity(parts);
                case "NOTIFY":
                    return notifyAssignment(parts);
                case "LIST_NOTIFICATIONS":
                    return listNotifications();
                default:
                    return "ERROR;UNKNOWN_COMMAND";
            }
        } catch (DateTimeParseException | NumberFormatException e) {
            return "ERROR;BAD_REQUEST";
        }
    }

    private String getCapacity(String[] parts) {
        if (parts.length != 2) {
            return "ERROR;BAD_REQUEST";
        }
        LocalDate date = LocalDate.parse(parts[1]);
        return store.getCapacity(date)
                .map(tons -> "OK;" + tons)
                .orElse("ERROR;NO_DATA");
    }

    private String notifyAssignment(String[] parts) {
        if (parts.length != 4) {
            return "ERROR;BAD_REQUEST";
        }
        LocalDate date = LocalDate.parse(parts[1]);
        int dumpsters = Integer.parseInt(parts[2]);
        int containers = Integer.parseInt(parts[3]);

        if (dumpsters < 0 || containers < 0) {
            return "ERROR;BAD_REQUEST";
        }
        store.addNotification(new Notification(date, dumpsters, containers));
        return "OK";
    }

    private String listNotifications() {
        List<Notification> list = store.getNotifications();
        StringBuilder response = new StringBuilder("OK;" + list.size());
        for (Notification n : list) {
            response.append(SEPARATOR)
                    .append(n.date()).append(',')
                    .append(n.totalDumpsters()).append(',')
                    .append(n.totalContainers());
        }
        return response.toString();
    }
}