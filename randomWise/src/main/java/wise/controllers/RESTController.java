package wise.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import wise.models.Account;
import wise.models.VerificationRes;
import wise.repositories.AccountRepository;
import wise.requests.AccountReq;
import wise.requests.PinRequest;
import wise.requests.UserRequest;
import wise.requests.VerificationReq;

@RestController
public class RESTController {

	   @Autowired
	   AccountRepository accountRepo;
	   
	   @RequestMapping(method = RequestMethod.POST, value="/verification")
	   public ResponseEntity<VerificationRes> verificationRequest(@RequestBody VerificationReq request){
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
	   public void setPin(@RequestBody PinRequest request){
		   Account account = accountRepo.findByPhone(request.getNumber());
		   if (account.getKey().equals(request.getKey())) account.setPIN(request.getPIN());
		   accountRepo.saveAndFlush(account);
	   }
	   
	   @RequestMapping(method = RequestMethod.POST, value = "/account")
	   public void setAccountData(@RequestBody AccountReq request){
		   Account account = accountRepo.findByPhone(request.getNumber());
		   if (account.getKey().equals(request.getKey())){
			   account.setAccount(request.getAccount());
			   account.setName(request.getName());
		   }
		   accountRepo.saveAndFlush(account);
	   }
}
