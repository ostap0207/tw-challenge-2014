package wise.models;

public class Transaction {
	class User{
		String name;
		String account;
	}
	
	User sender;
	User receiver;
	Float amount;
	String currency;
	
	public Transaction(String name1,String account1,String name2,String account2,Float amount,String currency){
		sender = new User();
		receiver = new User();
		sender.name = name1;
		sender.account = account1;
		receiver.name = name2;
		receiver.account = account2;
		this.amount = amount;
		this.currency = currency;
	}
}
