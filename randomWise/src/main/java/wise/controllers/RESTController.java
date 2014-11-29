package wise.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import wise.models.Account;
import wise.models.UserRequest;
import wise.models.VerificationRes;
import wise.repositories.AccountRepository;

@RestController
public class RESTController {

	   @Autowired
	   @Qualifier("accountRepository")
	   AccountRepository accountRepo;
	   
	   @RequestMapping(method = RequestMethod.POST, value="/verification")
	   public ResponseEntity<VerificationRes> verificationRequest(@RequestBody UserRequest request){
		   Account account = accountRepo.findByPhone(request.getNumber());
		   UUID id = UUID.randomUUID();
		   account.setVeryficationCode(id.toString());
		   VerificationRes vr = new VerificationRes();
		   vr.setKey(id.toString());
		   return new ResponseEntity<VerificationRes>(vr, HttpStatus.OK);
	   }
}
