package wise.models;

import javax.persistence.Entity;

@Entity
public class UserRequest {
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
	String number;
	String key;
}
