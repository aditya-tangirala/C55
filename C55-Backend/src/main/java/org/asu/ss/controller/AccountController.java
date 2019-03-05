package org.asu.ss.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.asu.ss.model.Account;
import org.asu.ss.model.AccountWrapper;
import org.asu.ss.model.BackendResponse;
import org.asu.ss.model.CreditCard;
import org.asu.ss.model.CreditCardCreateDetails;
import org.asu.ss.model.ExternalUser;
import org.asu.ss.model.Transaction;
import org.asu.ss.model.TransactionWindow;
import org.asu.ss.service.AccountService;
import org.asu.ss.service.ExtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AccountController {
	
	final static Logger log = Logger.getLogger(AccountController.class);
	
	@Autowired
	private AccountService accountService;

	@Autowired
	private ExtService extService;
	
	public void setExtService(ExtService extService) {
		this.extService = extService;
	}

	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}

	// TODO : Make sure appropriate entry in e_users table is updated with this
	// account no
	@RequestMapping(value = {"Employee/account/create","Manager/account/create"}, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BackendResponse> addAccount(@RequestBody AccountWrapper accountWrapper) {
		log.info("Enter AccountController.addAccount() with value: "+accountWrapper.toString());
		BackendResponse response = new BackendResponse();
		
		Account account = new Account();
		account.setAcc_balance(accountWrapper.getAcc_balance());
		account.setType(accountWrapper.getType());
		Date date = new Date();
		account.setCreation_date(date);

		Account newAcc = accountService.saveAccount(account);
		if (newAcc == null) {
			response.setStatus(BackendResponse.FAILURE);
			response.setError("Account Creation failed !!");
			log.error("AccountController.addAccount() return failed: Account creation failed");
			return new ResponseEntity<BackendResponse>(response, HttpStatus.OK);
		}
		else{
			boolean status = extService.updateAccountNo(accountWrapper.getCust_id(), newAcc.getAcc_no());
			if(!status){
				accountService.deleteAccount(newAcc);
				response.setStatus(BackendResponse.FAILURE);
				response.setError("Couldn't update account info in the user table!! Deletint the account.");
				log.error("AccountController.addAccount() return failed: "+response.toString());
				return new ResponseEntity<BackendResponse>(response, HttpStatus.OK);
			}
			else{
				CreditCardCreateDetails ccdet=new CreditCardCreateDetails();
				ccdet.setAcc_no(newAcc.getAcc_no());
				ccdet.setCust_id(accountWrapper.getCust_id());
				CreditCard cc=accountService.createCreditCard(ccdet);
				if(cc==null)
				{
					response.setStatus(BackendResponse.FAILURE);
					response.setError("Cannot create CreditCard");
					log.error("AccountController.addAccount() return failed: "+response.toString());
					return new ResponseEntity<BackendResponse>(response, HttpStatus.OK);	
				}
				else
				{
					response.setStatus(BackendResponse.SUCCESS);
					log.info("AccountController.addAccount() return successfully with value: "+response.toString()+" "+cc.toString());
					return new ResponseEntity<BackendResponse>(response, HttpStatus.OK);
				}
			}
		}
		//response.setStatus(BackendResponse.SUCCESS);
		// return new ResponseEntity<BackendResponse>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "Employee/account/delete", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BackendResponse> deleteAccount(@RequestBody Account account) {
		log.info("AccountController.deleteAccount entered with value: "+account.toString());
		BackendResponse response = new BackendResponse();
		boolean status = accountService.deleteAccount(account);
		if (!status) {
			response.setStatus(BackendResponse.FAILURE);
			response.setError("Account deletion failed !!");
			log.error("AccountController.deleteAccount exit failed with value: "+response.toString());
			return new ResponseEntity<BackendResponse>(response, HttpStatus.OK);
		}
		response.setStatus(BackendResponse.SUCCESS);
		log.info("AccountController.deleteAccount exit succeded with value: "+response.toString());
		return new ResponseEntity<BackendResponse>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "Manager/account/delete", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BackendResponse> deleteAccountM(@RequestBody Account account) {
		log.info("Account.deleteAccountM entering with value: "+account.toString());
		BackendResponse response = new BackendResponse();
		boolean status = accountService.deleteAccount(account);
		if (!status) {
			response.setStatus(BackendResponse.FAILURE);
			response.setError("Account deletion failed !!");
			log.error("Account.deleteAccountM exit failed with value: "+response.toString());
			return new ResponseEntity<BackendResponse>(response, HttpStatus.OK);
		}
		response.setStatus(BackendResponse.SUCCESS);
		log.info("Account.deleteAccountM succeded failed with value: "+response.toString());
		return new ResponseEntity<BackendResponse>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/account/view", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BackendResponse> getAccount(@RequestBody Account account) {
		log.info("AccountController.getAccount entered with value: "+account.toString());
		BackendResponse backendResponse = new BackendResponse();
		Account retAccount = accountService.findAccountById(account.getAcc_no());
		if (retAccount == null) {
			backendResponse.setStatus("Failure");
			backendResponse.setError("Account not found!!");
			log.error("AccountController.getAccount: account retrieval failed wih value: "+backendResponse.toString());
			return new ResponseEntity<BackendResponse>(backendResponse, HttpStatus.NOT_FOUND);
		}
		backendResponse.setStatus("Success");
		backendResponse.setData(retAccount);
		log.info("AccountController.getAccount: account retrieval succeeded wih value: "+backendResponse.toString());
		return new ResponseEntity<BackendResponse>(backendResponse, HttpStatus.OK);
	}

	@RequestMapping(value = {"/account/download", "/Customer/account/download"}, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BackendResponse> getAccountStatement(@RequestBody TransactionWindow transactionWindow) {
		log.info("AccountController.getAccountStatement entred with value: "+transactionWindow.toString());
		BackendResponse backendResponse = new BackendResponse();
		List<Transaction> transactions = accountService.getTransactionsWithinWindow(transactionWindow);
		if (transactions == null) {
			backendResponse.setStatus("Failure");
			backendResponse.setError("Data Not found!!");
			log.error("AccountController.getTransactionsWithinWindow return failed: Data not found "+backendResponse.toString());
			return new ResponseEntity<BackendResponse>(backendResponse, HttpStatus.NOT_FOUND);
		}
		backendResponse.setStatus("Success");
		backendResponse.setData(transactions);
		log.info("AccountController.getTransactionsWithinWindow return success: "+backendResponse.toString());
		return new ResponseEntity<BackendResponse>(backendResponse, HttpStatus.OK);
	}

	@RequestMapping(value = {"Customer/account/list","Merchant/account/list"}, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Account>> getAccountsList(Model model, HttpServletRequest request) {
		log.info("AccountController.getAccountList entered with value: "+model.toString());
		HttpSession session = request.getSession(false);
		List<Account> accounts = new ArrayList<Account>();
		
		long user_id = (long) session.getAttribute("custId");
		ExternalUser user = extService.findUserById(user_id);
		
		try {
			if(user.getAcc_no1()!=0)
				accounts.add(accountService.findAccountById(user.getAcc_no1()));
			if(user.getAcc_no2()!=0)
				accounts.add(accountService.findAccountById(user.getAcc_no2()));
			if(user.getAcc_no3()!=0)
				accounts.add(accountService.findAccountById(user.getAcc_no3()));
			if(user.getAcc_no4()!=0)
				accounts.add(accountService.findAccountById(user.getAcc_no4()));
			if(user.getAcc_no5()!=0)
				accounts.add(accountService.findAccountById(user.getAcc_no5()));
				
		} catch (Exception e) {
			e.printStackTrace();
			log.error("AccountController.getAccountList return failed ");
			return new ResponseEntity<List<Account>>(HttpStatus.NOT_FOUND);
		}
		log.info("AccountController.getAccountList return successful"+accounts.toString());
		return new ResponseEntity<List<Account>>(accounts, HttpStatus.OK);
	}

}
