package es.deusto.ingenieria.sd.plasSB.service;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.stereotype.Service;

import es.deusto.ingenieria.sd.plasSB.dao.CapacityRepository;
import es.deusto.ingenieria.sd.plasSB.dao.NotificationRepository;
import es.deusto.ingenieria.sd.plasSB.dto.CapacityDTO;
import es.deusto.ingenieria.sd.plasSB.dto.NotificationDTO;
import es.deusto.ingenieria.sd.plasSB.entity.Notification;

@Service
public class PlasSBService {
	
	private final CapacityRepository capacityRepository;
	private final NotificationRepository notificationRepository;
	
	public PlasSBService(CapacityRepository capacityRepository, NotificationRepository notificationRepository) {
		this.capacityRepository = capacityRepository;
		this.notificationRepository = notificationRepository;
	}

	public Optional<CapacityDTO> getCapacity(LocalDate date) {
		return capacityRepository.findByDate(date)
				.map(capacity -> new CapacityDTO(capacity.getDate(), capacity.getCapacity()));
	}

	public NotificationDTO saveNotification(NotificationDTO dto) {
		Notification notification = new Notification(dto.getDate(), dto.getNumDumpsters(), dto.getTotalContainers());
		notification = notificationRepository.save(notification);
		return new NotificationDTO(notification.getDate(), notification.getNumDumpsters(), notification.getTotalContainers());
	}
	

}
