package wise.models;

import javax.persistence.Entity;

@Entity
public class VerificationRes {
	String key;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
}