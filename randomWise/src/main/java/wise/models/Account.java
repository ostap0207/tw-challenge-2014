package wise.models;

import javax.persistence.Entity;

@Entity
public class Account {
	String name;
	String account;
	String phone;
	String PIN;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getPIN() {
		return PIN;
	}
	public void setPIN(String pIN) {
		PIN = pIN;
	}
	public String getVeryficationCode() {
		return VeryficationCode;
	}
	public void setVeryficationCode(String veryficationCode) {
		VeryficationCode = veryficationCode;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public boolean isVeryfied() {
		return veryfied;
	}
	public void setVeryfied(boolean veryfied) {
		this.veryfied = veryfied;
	}
	String VeryficationCode;
	String key;
	boolean veryfied;
}
