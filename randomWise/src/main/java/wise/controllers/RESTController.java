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
import wise.requests.*;
import wise.responses.*;

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
		account.setKey(id.toString());
		account.setVeryfied(true);
		VerificationRes vr = new VerificationRes();
		vr.setKey(id.toString());
		accountRepo.saveAndFlush(account);
		return new ResponseEntity<VerificationRes>(vr, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/pin")
	public ResponseEntity<TransferRes> setPin(@RequestBody PinRequest request) {
		TransferRes result = new TransferRes();
		result.setSucceded(false);
		try {
			Account account = accountRepo.findByPhone(request.getNumber());
			if (account.getKey().equals(request.getKey()))
				account.setPIN(request.getPIN());
			accountRepo.saveAndFlush(account);
			result.setSucceded(true);
		} catch (Exception ex) {
		}
		return new ResponseEntity<TransferRes>(result, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/account")
	public ResponseEntity<TransferRes> setAccountData(
			@RequestBody AccountReq request) {
		TransferRes result = new TransferRes();
		result.setSucceded(false);
		try {
			Account account = accountRepo.findByPhone(request.getNumber());
			if (account.getKey().equals(request.getKey())) {
				account.setAccount(request.getAccount());
				account.setName(request.getName());
			}
			accountRepo.saveAndFlush(account);
		} catch (Exception ex) {
		}
		return new ResponseEntity<TransferRes>(result, HttpStatus.OK);
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

	@RequestMapping(method = RequestMethod.POST, value = "/transfer")
	public ResponseEntity<TransferRes> makeTransfer(
			@RequestBody TransferReq request) {
		TransferRes result = new TransferRes();
		result.setSucceded(false);
		Account acc = accountRepo.findByPhone(request.getNumber());
		try {
			if (request.getKey().equals(acc.getKey())
					&& request.getPin().equals(acc.getPIN())
					&& acc.isVeryfied()) {
				result.setSucceded(true);
			}
		} catch (Exception ex) {
		}
		return new ResponseEntity<TransferRes>(result, HttpStatus.OK);
	}
}
