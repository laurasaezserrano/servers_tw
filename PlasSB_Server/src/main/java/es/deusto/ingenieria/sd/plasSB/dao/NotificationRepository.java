package es.deusto.ingenieria.sd.plasSB.dao;

import es.deusto.ingenieria.sd.plasSB.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

	Notification save(Notification notification);
	
	
	
}
