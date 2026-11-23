package es.deusto.ingenieria.sd.contsocket.data;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import es.deusto.ingenieria.sd.contsocket.model.Notification;

public class CapacityStore {

    private final Map<LocalDate, Double> capacities = new ConcurrentHashMap<>();
    private final List<Notification> notifications = new CopyOnWriteArrayList<>();

    // The plant publishes its capacity 10 days in advance: today and the following days
    public CapacityStore(double tonsPerDay, int daysInAdvance) {
        for (int i = 0; i < daysInAdvance; i++) {
            capacities.put(LocalDate.now().plusDays(i), tonsPerDay);
        }
    }

    public Optional<Double> getCapacity(LocalDate date) {
        return Optional.ofNullable(capacities.get(date));
    }

    public void addNotification(Notification notification) {
        notifications.add(notification);
    }

    public List<Notification> getNotifications() {
        return List.copyOf(notifications);
    }
}