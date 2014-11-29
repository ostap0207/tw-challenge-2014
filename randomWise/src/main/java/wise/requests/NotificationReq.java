package wise.requests;

public class NotificationReq extends UserRequest{
	String receiver;

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
}
