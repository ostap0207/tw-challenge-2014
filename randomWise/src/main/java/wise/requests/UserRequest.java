package wise.requests;

import javax.persistence.Entity;

public class UserRequest {
	String number;
	String key;
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
}
