package wise.requests;

import java.util.List;

public class NumbersReq extends UserRequest{
	List<String> numbers;

	public List<String> getNumbers() {
		return numbers;
	}

	public void setNumbers(List<String> numbers) {
		this.numbers = numbers;
	}
	
}
