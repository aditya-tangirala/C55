package org.asu.ss.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.asu.ss.model.AccountList;
import org.asu.ss.model.CreditCard;
import org.asu.ss.model.CreditCardAmountDue;
import org.asu.ss.model.CreditCardBalance;
import org.asu.ss.model.CreditCardCreateDetails;
import org.asu.ss.model.CreditCardTransaction;
import org.asu.ss.model.CreditCardUpdateLimit;
import org.asu.ss.model.CustomerDetail;
import org.asu.ss.model.RequestApprove;
import org.asu.ss.model.Transaction;
import org.asu.ss.service.CreditCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CreditCardController {

	final static Logger log = Logger.getLogger(AccountController.class);

	@Autowired
	private CreditCardService creditCardService;

	public void setCreditCardService(CreditCardService creditCardService) {
		this.creditCardService = creditCardService;
	}

	@RequestMapping(value = "/creditcard/amountdue", method = RequestMethod.POST)
	public ResponseEntity<CreditCardAmountDue> amountDue(@RequestBody CreditCardTransaction ccdet) {
		log.info("Entered CreditCardController.amountDue with value: "+ccdet.getCust_id());
		CreditCardAmountDue ccad;
		ccad = creditCardService.amountDue(ccdet);
		if(ccad == null){
			log.error("Exit CreditCard.amountDue failed");
			return new ResponseEntity<CreditCardAmountDue>(HttpStatus.NOT_FOUND);
		}
		log.info("Exit CreditCard.amountDue successful with value: "+ccdet.toString());
		return new ResponseEntity<CreditCardAmountDue>(ccad,HttpStatus.OK);

	}

	@RequestMapping(value = "Customer/creditcard/transaction", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> transactionPayment(@RequestBody CreditCardTransaction cctrans,HttpServletRequest request ) {
		log.info("Entered CreditCardController.transactionPayment with value: "+cctrans.toString());
		int ccsuccess;
		HttpSession session = request.getSession(false);
		cctrans.setCust_id((long)session.getAttribute("custId"));
		ccsuccess = creditCardService.transactionPayment(cctrans);
		if (ccsuccess == 0) {
			log.error("Exit CreditCardController.transactionPayment failed: transaction not successful");
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
		log.info("Exit CreditCardController.transactionPayment successful");
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	@RequestMapping(value = "/creditcard/accountlist", method = RequestMethod.POST)
	public ResponseEntity<AccountList> customerAccountList(@RequestBody CustomerDetail custdet) {
		log.info("Entering CreditCardController.customerAccountList with value: "+custdet.toString());
		AccountList accl;
		accl = creditCardService.getCustomerAccounts(custdet);
		if(accl == null){
			log.error("Exiting CreditCardController.customerAccountList");
			return new ResponseEntity<AccountList>(HttpStatus.NOT_FOUND);
		}
		log.info("Exiting CreditCardController.customerAccountList");
		return new ResponseEntity<AccountList>(accl,HttpStatus.OK);

	}

	public CreditCard createCreditCard(CreditCardCreateDetails ccdet) {
		log.info("Entering CreditCardController.createCreditCard with values "+ccdet.toString());
		CreditCard ccard = new CreditCard();
		creditCardService =new CreditCardService();
		log.info("Calling CreditCardService.createCreditCard with values "+ccdet.toString());
		ccard = creditCardService.createCreditCard(ccdet);
		if(ccard == null){
			log.error("Exiting CreditCardController.createCreditCard");
			return null;
		}
		log.info("Exiting CreditCardController.createCreditCard");
		return ccard;

	}

	@RequestMapping(value = "/creditcard/viewBalance", method = RequestMethod.POST)
	public ResponseEntity<CreditCardBalance> viewBalance(@RequestBody CreditCardBalance ccdet) {
		log.info("Enter CreditCardController.viewBalance with values: "+ccdet.toString());
		CreditCardBalance ccbal;
		ccbal = creditCardService.viewBalance(ccdet);
		if(ccbal == null){
			log.error("Exiting CreditCardController.viewBalance failed");
			return new ResponseEntity<CreditCardBalance>(HttpStatus.NOT_FOUND);
		}
		log.info("Exiting CreditCardController.viewBalance successful"+ccbal.toString());
		return new ResponseEntity<CreditCardBalance>(ccbal,HttpStatus.OK);

	}
	@RequestMapping(value = "/creditcard/updateLimit", method = RequestMethod.POST)
	public ResponseEntity<Void> updateLimit(@RequestBody CreditCardUpdateLimit ccdet) {
		log.info("Enter CreditCardController.updateLimit with values: "+ccdet.toString());
		int success;
		success = creditCardService.updateLimit(ccdet);
		if(success == 0){
			log.error("Exiting CreditCardController.updateLimit failed ");
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
		log.info("Exiting CreditCardController.updateLimit successful");
		return new ResponseEntity<Void>(HttpStatus.OK);

	}
	@RequestMapping(value = "/merchant/transreq", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Transaction>> retrieveTransactionRequests(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		long custid=(long)session.getAttribute("custId");
		log.info("Enter CreditCardController.retrieveTransactionRequests with values: "+custid);
		List<Transaction> translist;
		translist = creditCardService.retrieveTransactionRequests(custid);
		if(translist.isEmpty() == true){
			log.error("Exiting CreditCardController.retrieveTransactionRequests failed ");
			return new ResponseEntity<List<Transaction>>(HttpStatus.OK);
		}
		log.info("Exiting CreditCardController.retrieveTransactionRequests successful");
		return new ResponseEntity<List<Transaction>>(translist,HttpStatus.OK);

	}

	@RequestMapping(value = "/merchant/appreq", method = RequestMethod.POST)
	public ResponseEntity<Void> approveTransactionRequests(@RequestBody RequestApprove reqapp) {
		log.info("Enter CreditCardController.retrieveTransactionRequests with values: "+reqapp.toString());
		boolean success;
		success=creditCardService.approveTransactionRequests(reqapp);
		if(success == false){
			log.error("Exiting CreditCardController.retrieveTransactionRequests failed ");
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
		log.info("Exiting CreditCardController.retrieveTransactionRequests successful");
		return new ResponseEntity<Void>(HttpStatus.OK);

	}



	@RequestMapping(value = "Customer/creditcard/getDetails", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CreditCard> getDetails(Model model, HttpServletRequest request) {
		log.info("Entering CreditCardController.getDetails with value "+model.toString());
		HttpSession session =  request.getSession(false);
		long cust_id = -1L;
		if (session != null){
			cust_id = (long) session.getAttribute("custId");
		}
		CreditCard cc;
		cc = creditCardService.getDetails(cust_id);
		if (cc == null) {
			log.error("Exit CreditCardController.getDetails failed");
			return new ResponseEntity<CreditCard>(HttpStatus.NOT_FOUND);
		}
		Double bal1=cc.getAmount_used()-cc.getAmount_spent();
		Double int1=(bal1*(1/6.0)*cc.getInterest_rate()/100);
		Double int2=(cc.getAmount_spent()*(1/12.0)*cc.getInterest_rate()/100);
		Double fine = ((0.5/100)*bal1);
		Double due = cc.getAmount_used()+int1+int2+fine;
		cc.setAmount_used(Math.ceil(due));
		String ccStr = String.valueOf(cc.getCard_no());
		cc.setCard_no(Long.valueOf(ccStr.substring(ccStr.length()-4,ccStr.length())));
		log.info("Exit CreditCardController.getDetails successful");
		return new ResponseEntity<CreditCard>(cc, HttpStatus.OK);

	}
}
