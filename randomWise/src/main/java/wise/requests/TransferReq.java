package wise.requests;

public class TransferReq extends UserRequest{
	String reseiverNumber;
	Float amount;
	String currency;
	String pin;
	public String getReseiverNumber() {
		return reseiverNumber;
	}
	public void setReseiverNumber(String reseiverNumber) {
		this.reseiverNumber = reseiverNumber;
	}
	public Float getAmount() {
		return amount;
	}
	public void setAmount(Float amount) {
		this.amount = amount;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getPin() {
		return pin;
	}
	public void setPin(String pin) {
		this.pin = pin;
	}
}
