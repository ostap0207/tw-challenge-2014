package wise.responses;

import java.util.List;

import wise.models.Notification;

public class NotificationRes {
	List<Notification> notifications;

	public List<Notification> getNotifications() {
		return notifications;
	}

	public void setNotifications(List<Notification> notifications) {
		this.notifications = notifications;
	}
}
