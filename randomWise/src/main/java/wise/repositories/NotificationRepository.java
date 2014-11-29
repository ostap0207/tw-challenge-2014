package wise.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import wise.models.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long>{
	@Query("SELECT n FROM Notification n WHERE n.receiver=:phone AND n.accepted=false")
	public List<Notification> findByPhone(@Param("phone") String phone);
}
