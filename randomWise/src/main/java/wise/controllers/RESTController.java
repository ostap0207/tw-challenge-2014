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
import wise.models.Notification;
import wise.models.Transaction;
import wise.repositories.AccountRepository;
import wise.repositories.NotificationRepository;
import wise.requests.*;
import wise.responses.*;

@RestController
public class RESTController {

	@Autowired
	AccountRepository accountRepo;
	@Autowired
	NotificationRepository notificationRepo;

	@RequestMapping(method = RequestMethod.GET, value = "/all")
	public ResponseEntity<List<Account>> getAll() {
		return new ResponseEntity<List<Account>>(accountRepo.findAll(),
				HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/verification")
	public ResponseEntity<VerificationRes> verificationRequest(
			@RequestBody VerificationReq request) {
		UUID id = UUID.randomUUID();
		Account account = new Account();
		try {
			account = accountRepo.findByPhone(request.getNumber());
			account.getKey();
		} catch (Exception ex) {
			account = new Account();
		}
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
			result.setSucceded(true);
		} catch (Exception ex) {
		}
		return new ResponseEntity<TransferRes>(result, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/numbers")
	public ResponseEntity<NumbersRes> getNumbers(@RequestBody NumbersReq request) {
		List<String> existingNumbers = new ArrayList<String>();
		Account user = accountRepo.findByPhone(request.getNumber());
		if (user.getKey().equals(request.getKey())) {
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
		else {
			NumbersRes result = new NumbersRes();
			return new ResponseEntity<NumbersRes>(result, HttpStatus.OK);
		}
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
		if (result.isSucceded()){
			Notification notification = new Notification();
			notification.setCurrency(request.getCurrency());
			notification.setSum(request.getAmount());
			notification.setSender(request.getNumber());
			notification.setReceiver(request.getReseiverNumber());
			notification.setName(acc.getName());
			notificationRepo.saveAndFlush(notification);
		}
		return new ResponseEntity<TransferRes>(result, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/notifications")
	public ResponseEntity<NotificationRes> getNotifications(@RequestBody NotificationReq request){
		Account user = accountRepo.findByPhone(request.getNumber());
		try{
			if (user.getKey().equals(request.getKey())){
				List<Notification> result = notificationRepo.findByPhone(request.getReceiver());
				NotificationRes res = new NotificationRes();
				res.setNotifications(result);
				return new ResponseEntity<NotificationRes>(res, HttpStatus.OK);
			}
		}catch (Exception ex){
			
		}
		return new ResponseEntity<NotificationRes>(new NotificationRes(), HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/accept")
	public ResponseEntity<TransferRes> acceptTransfer(@RequestBody AcceptNotReq request){
		Account user = accountRepo.findByPhone(request.getNumber());
		TransferRes result = new TransferRes();
		result.setSucceded(false);
		try{
			if (user.getKey().equals(request.getKey())){
				Notification notification = notificationRepo.findOne(request.getNotificationId());
				notification.setAccepted(true);
				querryTransferWise(notification);
				result.setSucceded(true);
				return new ResponseEntity<TransferRes>(result, HttpStatus.OK); 
			}
		}catch (Exception ex){
			
		}
		return new ResponseEntity<TransferRes>(result, HttpStatus.OK);
	}
	
	private void querryTransferWise(Notification notification){
		String senderName = notification.getName();
		String receiverName = accountRepo.findByPhone(notification.getReceiver()).getName();
		String senderAcc = accountRepo.findByPhone(notification.getSender()).getAccount();
		String receiverAcc = accountRepo.findByPhone(notification.getReceiver()).getAccount();
		Transaction transaction = new Transaction(senderName, senderAcc, receiverName, receiverAcc, notification.getSum(), notification.getCurrency());
	}
}
