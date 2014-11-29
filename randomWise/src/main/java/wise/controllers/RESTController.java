package wise.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import wise.models.Account;
import wise.repositories.AccountRepository;
import wise.requests.AccountReq;
import wise.requests.NumbersReq;
import wise.requests.PinRequest;
import wise.requests.UserRequest;
import wise.requests.VerificationReq;
import wise.responses.NumbersRes;
import wise.responses.VerificationRes;

@RestController
public class RESTController {

	@Autowired
	AccountRepository accountRepo;

	@RequestMapping(method = RequestMethod.POST, value = "/verification")
	public ResponseEntity<VerificationRes> verificationRequest(
			@RequestBody VerificationReq request) {
		UUID id = UUID.randomUUID();
		Account account = new Account();
		account.setPhone(request.getNumber());
		account.setVeryficationCode(id.toString());
		VerificationRes vr = new VerificationRes();
		vr.setKey(id.toString());
		accountRepo.saveAndFlush(account);
		return new ResponseEntity<VerificationRes>(vr, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/pin")
	public void setPin(@RequestBody PinRequest request) {
		Account account = accountRepo.findByPhone(request.getNumber());
		if (account.getKey().equals(request.getKey()))
			account.setPIN(request.getPIN());
		accountRepo.saveAndFlush(account);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/account")
	public void setAccountData(@RequestBody AccountReq request) {
		Account account = accountRepo.findByPhone(request.getNumber());
		if (account.getKey().equals(request.getKey())) {
			account.setAccount(request.getAccount());
			account.setName(request.getName());
		}
		accountRepo.saveAndFlush(account);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/numbers")
	public ResponseEntity<NumbersRes> getNumbers(@RequestBody NumbersReq request) {
		List<String> existingNumbers = new ArrayList<String>();
		List<String> requestedNumbers = request.getNumbers();
		for (String phone : requestedNumbers) {
			Account acc = accountRepo.findByPhone(phone);
			try {
				existingNumbers.add(acc.getPhone());
			} catch (Exception ex) {

			}
		}
		NumbersRes result = new NumbersRes();
		result.setNumbers(existingNumbers);
		return new ResponseEntity<NumbersRes>(result, HttpStatus.OK);
	}
}
