package org.asu.ss.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.asu.ss.model.BackendResponse;
import org.asu.ss.model.Transaction;
import org.asu.ss.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


//funds transfer - own account/other accounts, accept payment, reject payment
//approve critical transaction, reject critical transaction


@RestController
public class TransactionController {
	
	@Autowired
	private TransactionService transactionService;
	
	public void setTransactionService(TransactionService transactionService)
	{
		this.transactionService = transactionService;
	}
	
	//External User - fund transfer - own account
	//External User - fund transfer - other account
	//External User - quick pay - using email
	//External User - quick pay - using mobile
	@RequestMapping(value = "Customer/transfer/fund", method=RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BackendResponse> transferFunds(@RequestBody Transaction transaction, HttpServletRequest request)
	{
		HttpSession session = request.getSession(false);	
		session.setAttribute("tranSuccess","Success");
		session.setAttribute("item", "Mobile");
		
		//long user_id = (long) session.getAttribute("custId");
		transaction.setT_custid((long)session.getAttribute("custId"));
		try
		{	
			System.out.println("Inside customer/transfer/fund->transferFunds. Values: " +
					"From Account: " + transaction.getFrom_acc() + 
					"To Account: " + transaction.getTo_acc() +
					"T Amount: " + transaction.getT_amount() +
					"Customer ID: " + transaction.getT_custid());
		}
		catch(Exception e)
		{
			System.out.println("Error in Logging. Inside customer/transfer/fund->transferFunds.");
		}
		//Need transaction amount, remarks, from account, to account
		//Now based on value of the amount, categorize it as critical or normal
		//create a transaction id, and get the current time stamp.
		//If critical, do not credit amount to beneficiary. just debit. add a flag to transaction-approved, decline, pending.
		BackendResponse backendResponse = new BackendResponse();
		try
		{
			backendResponse = transactionService.handleTransfer(transaction);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.out.println("TransactionController.transferFunds() exception");
		}
		if (backendResponse.getStatus().equals(BackendResponse.FAILURE))
		{
			session.setAttribute("tranSuccess","Failure");
		}
		System.out.println("TransactionController.transferFunds() "+backendResponse.getStatus());
		session.setAttribute("Otp_Id", transaction.getOtp_id());
		session.setAttribute("TransID", transaction.getT_id());
		session.setAttribute("T_amt", transaction.getT_amount());
		return new ResponseEntity<BackendResponse>(backendResponse, HttpStatus.OK);
	}
	
	/*@RequestMapping(value="/transfer/otp", method=RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BackendResponse> otpTransfer(@RequestBody Transaction transaction)
	{
		try
		{
			System.out.println("Inside /transfer/otp>otpTransfer. Values: " +
					"Transaction  ID: " + transaction.getT_id() + 
					"OPT ID: " + transaction.getOtp_id() +
					"OTP Value" + transaction.getOtp_value() +
					"T Amount: " + transaction.getT_amount() +
					"Customer ID: " + transaction.getT_custid());
		}
		catch(Exception e)
		{
			System.out.println("Error in Logging. Inside transfer/otp>otpTransfer.");
		}
		BackendResponse backendResponse = new BackendResponse();
		try
		{
			backendResponse = transactionService.handleOTPTransfer(transaction);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return new ResponseEntity<BackendResponse>(backendResponse, HttpStatus.OK);
	}
	*/
	
	@RequestMapping(value={"/transfer/approve/critical","/Manager/transfer/approve/critical"}, method=RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BackendResponse> approveCriticalTransactions(@RequestBody Transaction transaction, HttpServletRequest request)
	{
		BackendResponse backendResponse = new BackendResponse();
		try
		{
			backendResponse = transactionService.handleApproveCriticalTransactions(transaction);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return new ResponseEntity<BackendResponse>(backendResponse, HttpStatus.OK);
	}
	
	
	@RequestMapping(value={"/Employee/transfer/initiate","/Manager/transfer/initiate"}, method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BackendResponse> initiateFundTransfer(@RequestBody Transaction transaction)
	{
		BackendResponse backendResponse = new BackendResponse();
		try
		{
			backendResponse = transactionService.handleInitiateTransfer(transaction);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			backendResponse.setStatus(BackendResponse.FAILURE);
			backendResponse.setError("OOPS! AN ERROR HAS OCCURRED. PLEASE TRY AGAIN.");
		}
		return new ResponseEntity<BackendResponse>(backendResponse, HttpStatus.OK);
	}
	
	@RequestMapping(value={"Customer/transfer/approve/internal","/Employee/transfer/approve/internal"}, method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BackendResponse> approveFundTransfer(@RequestBody Transaction transaction)
	{
		BackendResponse backendResponse = new BackendResponse();
		try
		{
			backendResponse = transactionService.handleApproveTransfer(transaction);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			backendResponse.setStatus(BackendResponse.FAILURE);
			backendResponse.setError("OOPS! AN ERROR HAS OCCURRED. PLEASE TRY AGAIN.");
		}
		return new ResponseEntity<BackendResponse>(backendResponse, HttpStatus.OK);
	}
	
	@RequestMapping(value={"Customer/transfer/decline/internal","/Employee/transfer/decline/internal"}, method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BackendResponse> declineFundTransfer(@RequestBody Transaction transaction)
	{
		BackendResponse backendResponse = new BackendResponse();
		try
		{
			backendResponse = transactionService.handleDeclineTransfer(transaction);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			backendResponse.setStatus(BackendResponse.FAILURE);
			backendResponse.setError("OOPS! AN ERROR HAS OCCURRED. PLEASE TRY AGAIN.");
		}
		return new ResponseEntity<BackendResponse>(backendResponse, HttpStatus.OK);
	}
	
	@RequestMapping(value={"/account/debit","/Customer/account/debit","/Merchant/account/debit"}, method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BackendResponse> initiateSelfDebit(@RequestBody Transaction transaction)
	{
		BackendResponse backendResponse = new BackendResponse();
		try
		{
			backendResponse = transactionService.handleSelfDebitInitiation(transaction);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			backendResponse.setStatus(BackendResponse.FAILURE);
			backendResponse.setError("OOPS! AN ERROR HAS OCCURRED. PLEASE TRY AGAIN.");
		}
		return new ResponseEntity<BackendResponse>(backendResponse, HttpStatus.OK);
	}
	
	/*
	 {
		"from_acc": 5876,
		"mobile": "7406",
		"t_amount": 444,
		"remarks": "FuckOFF"
	}
	
	{
 		"status": "Success",
 		"error": null,
 		"data": null
	}

	 */
	@RequestMapping(value={"/transfer/quick","/Customer/transfer/quick","/Merchant/transfer/quick"}, method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BackendResponse> quickFundTransfer(@RequestBody Transaction transaction, HttpServletRequest request)
	{
		//Long l = new Long("587654321112");
		//long a1 = l.longValue();
		//System.out.println(a1);
		//long a1 = 587654321113L;
		HttpSession session = request.getSession(false);	
		session.setAttribute("tranSuccess","Success");
		session.setAttribute("item", "Mobile");
		transaction.setT_custid((long)session.getAttribute("custId"));
		
		System.out.println(transaction.getFrom_acc());
		System.out.println("Mobile"+transaction.getMobile());
		System.out.println(transaction.getT_amount());
		System.out.println(transaction.getRemarks());
		System.out.println("Email: " + transaction.getEmail());
		BackendResponse backendResponse = new BackendResponse();
		try
		{
			backendResponse = transactionService.handleQuickTransfer(transaction);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			backendResponse.setStatus(BackendResponse.FAILURE);
			backendResponse.setError("OOPS! AN ERROR HAS OCCURRED. PLEASE TRY AGAIN.");
		}
		if (backendResponse.getStatus().equals(BackendResponse.FAILURE))
		{
			session.setAttribute("tranSuccess","Failure");
		}
		session.setAttribute("Otp_Id", transaction.getOtp_id());
		session.setAttribute("TransID", transaction.getT_id());
		session.setAttribute("T_amt", transaction.getT_amount());
		return new ResponseEntity<BackendResponse>(backendResponse, HttpStatus.OK);
	}	
	@RequestMapping(value={"/account/credit","/Customer/account/credit","/Merchant/account/debit"}, method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BackendResponse> initiateSelfCredit(@RequestBody Transaction transaction)
	{
		BackendResponse backendResponse = new BackendResponse();
		try
		{
			backendResponse = transactionService.handleSelfCreditInitiation(transaction);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			backendResponse.setStatus(BackendResponse.FAILURE);
			backendResponse.setError("OOPS! AN ERROR HAS OCCURRED. PLEASE TRY AGAIN.");
		}
		return new ResponseEntity<BackendResponse>(backendResponse, HttpStatus.OK);
	}
	
	@RequestMapping(value={"/account/approve/debit","/Employee/account/approve/debit"}, method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BackendResponse> aprroveSelfDebit(@RequestBody Transaction transaction)
	{
		BackendResponse backendResponse = new BackendResponse();
		try
		{
			backendResponse = transactionService.handleApproveSelfDebit(transaction);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			backendResponse.setStatus(BackendResponse.FAILURE);
			backendResponse.setError("OOPS! AN ERROR HAS OCCURRED. PLEASE TRY AGAIN.");
		}
		return new ResponseEntity<BackendResponse>(backendResponse, HttpStatus.OK);
	}
	
	@RequestMapping(value={"/account/approve/credit","/Employee/account/approve/credit"}, method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BackendResponse> aprroveSelfCredit(@RequestBody Transaction transaction)
	{
		BackendResponse backendResponse = new BackendResponse();
		try
		{
			backendResponse = transactionService.handleApproveSelfCredit(transaction);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			backendResponse.setStatus(BackendResponse.FAILURE);
			backendResponse.setError("OOPS! AN ERROR HAS OCCURRED. PLEASE TRY AGAIN.");
		}
		return new ResponseEntity<BackendResponse>(backendResponse, HttpStatus.OK);
	}
	
	@RequestMapping(value={"/account/reject/debit","/Employee/account/reject/debit"}, method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BackendResponse> rejectSelfDebit(@RequestBody Transaction transaction)
	{
		BackendResponse backendResponse = new BackendResponse();
		try
		{
			backendResponse = transactionService.handleRejectSelfDebit(transaction);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			backendResponse.setStatus(BackendResponse.FAILURE);
			backendResponse.setError("OOPS! AN ERROR HAS OCCURRED. PLEASE TRY AGAIN.");
		}
		return new ResponseEntity<BackendResponse>(backendResponse, HttpStatus.OK);
	}
	
	@RequestMapping(value={"/account/reject/credit","/Employee/account/reject/credit"}, method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BackendResponse> rejectSelfCredit(@RequestBody Transaction transaction)
	{
		BackendResponse backendResponse = new BackendResponse();
		try
		{
			backendResponse = transactionService.handleRejectSelfCredit(transaction);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			backendResponse.setStatus(BackendResponse.FAILURE);
			backendResponse.setError("OOPS! AN ERROR HAS OCCURRED. PLEASE TRY AGAIN.");
		}
		return new ResponseEntity<BackendResponse>(backendResponse, HttpStatus.OK);
	}
	
	//list transactions based on status/otp_status/severity
	//PROCESSED
	//PENDING
	//CRITICAL
	//NORMAL
	//APPROVED
	//NOT_APPLICABLE
	//INITIATED
	//SEFLDEBIT_INITIATED
	//SEFLDEBIT_PROCESSED
	//SEFLDEBIT_REJECTED
	//SEFLCREDIT_INITIATED
	//SEFLCREDIT_PROCESSED
	//SEFLCREDIT_REJECTED
	@RequestMapping(value={"/list/transactions","/Customer/list/transactions","/Employee/list/transactions","/Manager/list/transactions","/Merchant/list/transactions"}, method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BackendResponse> listTransactions(@RequestBody Transaction transaction, HttpServletRequest request)
	{
		System.out.println("Severity:  " + transaction.getSeverity());
		System.out.println("Status: " + transaction.getT_status());
		HttpSession session = request.getSession(false);		
		long user_id = (long) session.getAttribute("custId");
		transaction.setT_custid(user_id);
		//transaction.setTCustID(999);
		BackendResponse backendResponse = new BackendResponse();
		try
		{
			backendResponse = transactionService.handleListTransactions(transaction);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			backendResponse.setStatus(BackendResponse.FAILURE);
			backendResponse.setError("OOPS! AN ERROR HAS OCCURRED. PLEASE TRY AGAIN.");
		}
		return new ResponseEntity<BackendResponse>(backendResponse, HttpStatus.OK);
	}
	
	@RequestMapping(value={"/transfer/decline/critical","/Manager/transfer/decline/critical"}, method=RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BackendResponse> declineCriticalTransactions(@RequestBody Transaction transaction)
	{
		BackendResponse backendResponse = new BackendResponse();
		try
		{
			backendResponse = transactionService.handleDeclineTransactions(transaction);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			backendResponse.setStatus(BackendResponse.FAILURE);
			backendResponse.setError("OOPS! AN ERROR HAS OCCURRED. PLEASE TRY AGAIN.");
		}
		return new ResponseEntity<BackendResponse>(backendResponse, HttpStatus.OK);
	}
	
	@RequestMapping(value={"/transfer/pay","/Customer/transfer/pay"}, method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BackendResponse> payMerchant(@RequestBody Transaction transaction, HttpServletRequest request)
	{
		/*
		 * {
		 * "remarks":"<merchant_name>",
		 * "t_amount":<amount_value>",
		 * 
		 * }
		 */
		HttpSession session = request.getSession(false);		
		long user_id = (long) session.getAttribute("custId");
		BackendResponse backendResponse = new BackendResponse();
		try
		{
			backendResponse = transactionService.handleCustomerPurchase(transaction);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			backendResponse.setStatus(BackendResponse.FAILURE);
			backendResponse.setError("OOPS! AN ERROR HAS OCCURRED. PLEASE TRY AGAIN.");
		}
		return new ResponseEntity<BackendResponse>(backendResponse, HttpStatus.OK);
	}
	
}
