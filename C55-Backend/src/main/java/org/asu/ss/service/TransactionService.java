package org.asu.ss.service;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.asu.ss.dao.AccountDAO;
import org.asu.ss.dao.CreditCardDAO;
import org.asu.ss.dao.TransactionDAO;
import org.asu.ss.model.Account;
import org.asu.ss.model.BackendResponse;
import org.asu.ss.model.CreditCard;
import org.asu.ss.model.ExternalUser;
import org.asu.ss.model.TempTransaction;
import org.asu.ss.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class TransactionService {
	
	public static final String PROCESSED = "processed";
	public static final String PENDING = "pending";
	public static final String CRITICAL = "critical";
	public static final String NORMAL = "normal";
	public static final String APPROVED = "approved";
	public static final String FAILED = "failed";
	public static final String DECLINED = "declined";
	public static final String NOT_APPLICABLE = "not_applicable";
	public static final String INITIATED = "initiated";
	
	public static final String SEFLDEBIT_INITIATED = "sd_initiated";
	public static final String SEFLDEBIT_PROCESSED = "sd_processed";
	public static final String SEFLDEBIT_REJECTED = "sd_rejected";

	public static final String SEFLCREDIT_INITIATED = "sc_initiated";
	public static final String SEFLCREDIT_PROCESSED = "sc_processed";
	public static final String SEFLCREDIT_REJECTED = "sc_rejected";
	
	public static final String MOBILE = "Mobile";
	public static final String EMAIL = "Email";
	
	public static final String OTP_SUCCESS = "Validaton Successful";
	public static final String OTP_FAILED = "Wrong OTP Entered";
	
	public static final String PURCHASED = "purchased";

	@Autowired
	private TransactionDAO transactionDAO;
	
	@Autowired
	private AccountDAO accountDAO;
	
	@Autowired
	private ExtService extService;
	
	@Autowired
	private CreditCardDAO creditCardDAO;
	
	public void setTransactionDAO(TransactionDAO transactionDAO){
		this.transactionDAO = transactionDAO;
	}
	
	public void setAccountDAO(AccountDAO accountDAO){
		this.accountDAO = accountDAO;
	}
	
	public void setExtService(ExtService extService)
	{
		this.extService = extService;
	}
	
	public void setCreditCardDAO(CreditCardDAO creditCardDAO)
	{
		this.creditCardDAO = creditCardDAO;
	}
	
	private double transferLimit = 5000;
	
	
	public BackendResponse handleOTPTransfer(Transaction transaction)
	{
		BackendResponse backendResponse = new BackendResponse();
		
		//U should receive cust_id, otp_id, t_id from UI
		//get transaction from DB using t_id
		//Check if it is still in otp_status:pending
		//if it is pending, call verify OTP method(Srini's).
		//check otp response. if it is valid, check if it critical or not. 
		//if it is normal, then credit money to to_acc, and update t_status and otp_status to processed and verified respectively
		//if it is critical, then check t_status. 
		//if t_status is processed(manager already approved critical transaction), then credit money to to_acc, and update otp_status to verified.
		//if t_status is pending, then don't credit. Jus update otp_status to verified.
		
		/*
		 * public String validateOTPforTransactions(TempTransaction temptransaction, String inputOTP)	-	Service to be called
			Request:
			{
  				"cust_id": 199999999999,
  				"otp_value": "458468",
  				"id" : 4,
  				"item" : "Email"
			}

			{
  				"cust_id": 199999999999,
  				"otp_value": "458468",
  				"id" : 4,
  				"item" : "Mobile"
			}

			Response:
			Validation Successful // 'll change it to validation later ;) (We ll change tomorrow infront of Pankaj :P)
			or
			Wrong OTP entered
			or
			OTP Value Expired - Request for new OTP
		 */
		Transaction transactionFromDB = this.transactionDAO.getTransaction(transaction);
		Account to_account = this.accountDAO.findAccountById(transactionFromDB.getTo_acc());
		if (transactionFromDB.getOtp_status().equals(TransactionService.PENDING))
		{
			TempTransaction tempTransaction = new TempTransaction();
			tempTransaction.setCust_id(transaction.getT_custid());
			tempTransaction.setOtp_value(transaction.getOtp_value());
			tempTransaction.setId(transaction.getOtp_id());
			tempTransaction.setItem(TransactionService.MOBILE);
			String otpStatus = extService.validateOTPforTransactions(tempTransaction, tempTransaction.getOtp_value());
			if (otpStatus.equals(TransactionService.OTP_SUCCESS))
			{
				if(transactionFromDB.getSeverity().equals(TransactionService.NORMAL))
				{
					to_account.setAcc_balance((to_account.getAcc_balance() + transactionFromDB.getT_amount()));
					accountDAO.updateAccount(to_account);
					transactionFromDB.setT_status(TransactionService.PROCESSED);
					transactionFromDB.setOtp_status(TransactionService.APPROVED);
					transactionDAO.updateOTPTransaction(transactionFromDB);
				}
				else if(transactionFromDB.getSeverity().equals(TransactionService.CRITICAL))
				{
					if(transactionFromDB.getT_status().equals(TransactionService.PENDING))
					{
						transactionFromDB.setOtp_status(TransactionService.APPROVED);
						transactionDAO.updateOTPTransaction(transactionFromDB);
					}
					else if(transactionFromDB.getT_status().equals(TransactionService.PROCESSED))
					{
						to_account.setAcc_balance((to_account.getAcc_balance() + transactionFromDB.getT_amount()));
						accountDAO.updateAccount(to_account);
						transactionFromDB.setOtp_status(TransactionService.APPROVED);
						transactionDAO.updateOTPTransaction(transactionFromDB);
					}
				}
				else
				{
					//what about N/A case? Transaction initiated by internal user? Do we need to handle it here?
				}
			}
			else
			{
				//OPT validation failed! Credit money back to source account!
				Account from_acc = this.accountDAO.findAccountById(transactionFromDB.getFrom_acc());
				from_acc.setAcc_balance((from_acc.getAcc_balance() + transactionFromDB.getT_amount()));
				accountDAO.updateAccount(from_acc);
				transactionFromDB.setT_status(TransactionService.PROCESSED);
				transactionFromDB.setOtp_status(TransactionService.FAILED);
				transactionDAO.updateOTPTransaction(transactionFromDB);
			}
			backendResponse.setStatus(BackendResponse.SUCCESS);
		}
		else
		{
			backendResponse.setStatus((BackendResponse.FAILURE));
			backendResponse.setError("OTP Status is not pending!!!");
		}
		return backendResponse;
	}
	
	
	public BackendResponse handleTransfer(Transaction transaction)
	{
		BackendResponse backendResponse = new BackendResponse();
		
		Account from_account, to_account;
		
		//Get the account details of from_account.
		from_account = this.accountDAO.findAccountById(transaction.getFrom_acc());
		to_account = this.accountDAO.findAccountById(transaction.getTo_acc());
		
		//check if t_amount is >= balance of from_account
		if(from_account.getAcc_balance() < transaction.getT_amount())
		{
			//returned JSON should have error message!
			backendResponse.setStatus(BackendResponse.FAILURE);
			backendResponse.setError("Insufficient funds! Ensure you have required balance to perform this transaction");
			return backendResponse;
		}
		//Check if t_amount is greater than general transfer limit.
		
		/*
		 * 	OTP generate:
			http://localhost:8090/C55-Backend/externaluserControllerr/updatetrans	-	controller
			public String updateOTPTransactionsTable(TempTransaction temptransaction, String FieldtobeUpdated)	-	Service to be called
			Request:
			{
  				"cust_id": 199999999999,
  				"email": "puranamsrinivas26@gmail.com",
  				"item": "Email"
			}
			{
  				"cust_id": 199999999999,
  				"mobile": "6232230201",
  				"mobile_carrier":"AT&T",
  				"item": "Mobile"
			}

			Response:
			Mailed the OTP to puranamsrinivas26@gmail.com::4 or Mailed the OTP to 6232230201::4 
			where 4 is the otp_id, which has to be stored as I need this data for OTP validation
		 */
		
		//OPT Stuff
		try
		{
			TempTransaction tempTransaction = new TempTransaction();
			//Mobile or Email? Who decides?
			ExternalUser e_user = this.transactionDAO.getUserByCustomerID(transaction.getT_custid());
			tempTransaction.setCust_id(transaction.getT_custid());
			tempTransaction.setMobile_carrier(e_user.getMobile_carrier());
			tempTransaction.setMobile(e_user.getMobile());
			tempTransaction.setItem(TransactionService.MOBILE);
			String otpResponse = extService.updateOTPTransactionsTable(tempTransaction, TransactionService.MOBILE);
			int position = otpResponse.indexOf("::");
			position+=2;
			String otp_id_s = otpResponse.substring(position);
			long otp_id = Long.parseLong(otp_id_s);
			transaction.setOtp_id(otp_id);
			System.out.println("Transaction. OTP Details: " + 
			tempTransaction.getCust_id() + " " +
			tempTransaction.getMobile_carrier() + " " +
			tempTransaction.getMobile() + " " + 
			transaction.getOtp_id());
		}
		catch(Exception e)
		{
			e.printStackTrace();
			backendResponse.setStatus(BackendResponse.FAILURE);
			backendResponse.setError("Failed to generate OPT. Please try the transaction again. Don't worry, your money is safe!");
			return backendResponse;
		}
		
		if(transaction.getT_amount() > this.transferLimit)
		{
			//If  it is greater than the limit, then add {transaction:pending} to transaction, set t_status to critical, 
			//debit amount from source user, store details in transaction table.
			from_account.setAcc_balance((from_account.getAcc_balance() - transaction.getT_amount()));
			accountDAO.updateAccount(from_account);
			transaction.setSeverity(TransactionService.CRITICAL);
			transaction.setT_status(TransactionService.PENDING);
			transaction.setOtp_status(PENDING);
			Date date = new Date();
			transaction.setT_timestamp(date);
			//should we jus use sql generated ID? This id is not getting saved to db!
			transaction.setT_id((long)ThreadLocalRandom.current().nextInt(100, 10000 + 1));
			transactionDAO.saveTransaction(transaction);
			backendResponse.setStatus(BackendResponse.SUCCESS);
			backendResponse.setData(transaction);
		}
		else
		{
			//If it it less than or equal to the limit, then debit that amount from source user, credit the amount in beneficiary user's account, mark the transaction as processed.
			
			//Wait until user enters otp! don't credit. Move this code to tranfer/otp
			//to_account.setAcc_balance((to_account.getAcc_balance() + transaction.getT_amount()));
			//accountDAO.updateAccount(to_account);
			
			
			from_account.setAcc_balance((from_account.getAcc_balance() - transaction.getT_amount()));
			accountDAO.updateAccount(from_account);
			backendResponse.setStatus(BackendResponse.SUCCESS);
			backendResponse.setData(transaction);
			transaction.setT_status(TransactionService.PENDING);
			transaction.setSeverity(TransactionService.NORMAL);
			transaction.setOtp_status(TransactionService.PENDING);
			Date date = new Date();
			transaction.setT_timestamp(date);
			//should we jus use sql generated ID? This id is not getting saved to db!
			transaction.setT_id((long)ThreadLocalRandom.current().nextInt(100, 10000 + 1));
			transactionDAO.saveTransaction(transaction);
		}
		
		return backendResponse;
		
	}


	public BackendResponse handleApproveCriticalTransactions(Transaction transaction)
	{
		//get transaction from transaction_db first. 
		//check if severity is critical and status is pending.
		//get to_acc from that transaction and match it with incoming transaction.
		//check otp_status. TODO
		//if otp_status is approved, then credit amount to to_acc, set t_status to processed.
		//if otp_status is pending, then just set t_status to processed. Don't credit money yet!
		BackendResponse backendResponse = new BackendResponse();
		
		Transaction transactionVerified = transactionDAO.getCriticalTransaction(transaction);
		Account to_account = this.accountDAO.findAccountById(transaction.getTo_acc());
		
		System.out.println("org.asu.ss.service.TransactionService:handleApproveCriticalTransactions. From DB "+ 
		transactionVerified.getFrom_acc() + " " +  
		transactionVerified.getTo_acc() + " " +
		transactionVerified.getT_id());
		
		if(transactionVerified != null)
		{
			if(transactionVerified.getOtp_status().equals(TransactionService.PENDING))
			{
				transactionVerified.setT_status(TransactionService.PROCESSED);
				transactionDAO.updateTransaction(transactionVerified);
				backendResponse.setStatus(BackendResponse.SUCCESS);
			}
			else if(transactionVerified.getOtp_status().equals(TransactionService.APPROVED))
			{
				to_account.setAcc_balance((to_account.getAcc_balance() + transaction.getT_amount()));
				accountDAO.updateAccount(to_account);
				transactionVerified.setT_status(TransactionService.PROCESSED);
				transactionDAO.updateTransaction(transactionVerified);
				backendResponse.setStatus(BackendResponse.SUCCESS);
			}
			else
			{
				//Any other possible values. Not Applicable!? Check! Check for normal transactions also!
			}
			
		}
		else
		{
			backendResponse.setStatus(BackendResponse.FAILURE);
			backendResponse.setError("Transaction not found!");
		}
		
		return backendResponse;
	}
	
	public BackendResponse handleDeclineTransactions(Transaction transaction)
	{
		//get transaction from transaction_db first. 
		//check if severity is critical and status is pending. (in dao)
		//get from_acc from that transaction and match it with incoming transaction.
		//credit t_amount back to from_acc.
		//set status to declined
		BackendResponse backendResponse = new BackendResponse();
		
		Transaction transactionVerified = transactionDAO.getCriticalTransaction(transaction);
		Account from_acc = this.accountDAO.findAccountById(transaction.getFrom_acc());
		
		System.out.println("org.asu.ss.service.TransactionService:handleDeclineCriticalTransactions. From DB "+ 
		transactionVerified.getFrom_acc() + " " +  
		transactionVerified.getTo_acc() + " " +
		transactionVerified.getT_id() + " " +
		transactionVerified.getT_amount() + 
		" From Account: " + from_acc.getAcc_balance());
		
		from_acc.setAcc_balance((from_acc.getAcc_balance() + transactionVerified.getT_amount())); //put the MONEY back!
		accountDAO.updateAccount(from_acc);
		transactionVerified.setT_status(TransactionService.DECLINED);
		transactionDAO.updateTransaction(transactionVerified);
		backendResponse.setStatus(BackendResponse.SUCCESS);
		
		return backendResponse;
	}
	
	public BackendResponse handleInitiateTransfer(Transaction transaction)
	{
		//Employee or manager initiated this transfer. So no OTP! and no severity. Jus wait for external user's approval
		BackendResponse backendResponse = new BackendResponse();
		
		Account from_account;
		
		//Get the account details of from_account.
		from_account = this.accountDAO.findAccountById(transaction.getFrom_acc());
		//to_account = this.accountDAO.findAccountById(transaction.getTo_acc());
		
		//check if t_amount is >= balance of from_account
		if(from_account.getAcc_balance() < transaction.getT_amount())
		{
			//returned JSON should have error message!
			backendResponse.setStatus(BackendResponse.FAILURE);
			backendResponse.setError("Insufficient funds! Ensure you have required balance to perform this transaction");
			return backendResponse;
		}
		
		
		from_account.setAcc_balance((from_account.getAcc_balance() - transaction.getT_amount()));
		accountDAO.updateAccount(from_account);
		transaction.setSeverity(TransactionService.NOT_APPLICABLE);
		transaction.setT_status(TransactionService.INITIATED);
		transaction.setOtp_status(TransactionService.NOT_APPLICABLE);
		transaction.setRemarks("Initiated");
		Date date = new Date();
		transaction.setT_timestamp(date);
		//should we jus use sql generated ID? This id is not getting saved to db! Same goes for account ID as well!
		transaction.setT_id((long)ThreadLocalRandom.current().nextInt(100, 10000 + 1));
		transactionDAO.saveTransaction(transaction);
		backendResponse.setStatus(BackendResponse.SUCCESS);
		
		return backendResponse;
	}
	
	public BackendResponse handleApproveTransfer(Transaction transaction)
	{
		//customer will approve a transfer initiated by employee! Get the transaction from transaction_db,
		//check t_status. it should still be in "initiated" state. if not, someone else meanwhile has approved. you will end up crediting money twice!!
		//check if customer still has sufficient funds!! Wait! Not required. Because you already debited cash when transfer was initiated.
		//credit amount to to_acc
		//set t_status to approved
		BackendResponse backendResponse = new BackendResponse();
		Transaction transactionFromDB = transactionDAO.getTransaction(transaction);
		Account to_account = this.accountDAO.findAccountById(transaction.getTo_acc());
		
		if(transactionFromDB.getT_status().equals(TransactionService.INITIATED))
		{
			to_account.setAcc_balance(to_account.getAcc_balance() + transactionFromDB.getT_amount());
			accountDAO.updateAccount(to_account);
			transactionFromDB.setT_status(TransactionService.APPROVED);
			transactionDAO.updateTransaction(transactionFromDB);
			backendResponse.setStatus(BackendResponse.SUCCESS);
		}
		else
		{
			backendResponse.setStatus(BackendResponse.FAILURE);
			backendResponse.setError("Dude! How many time will you decline/approve it! Its approved/declined already.");
		}
		
		return backendResponse;
	}
	
	
	public BackendResponse handleDeclineTransfer(Transaction transaction)
	{
		//customer will decline a transfer initiated by employee! Get the transaction from transaction_db,
		//check t_status. it should still be in "initiated" state. if not, someone else meanwhile has approved or declined it. you will end up crediting money twice!!
		//credit amount to to_acc
		//set t_status to declined
		BackendResponse backendResponse = new BackendResponse();
		Transaction transactionFromDB = transactionDAO.getTransaction(transaction);
		Account from_account = this.accountDAO.findAccountById(transaction.getFrom_acc());
		
		if(transactionFromDB.getT_status().equals(TransactionService.INITIATED))
		{
			from_account.setAcc_balance(from_account.getAcc_balance() + transactionFromDB.getT_amount());
			accountDAO.updateAccount(from_account);
			transactionFromDB.setT_status(TransactionService.DECLINED);
			transactionDAO.updateTransaction(transactionFromDB);
			backendResponse.setStatus(BackendResponse.SUCCESS);
		}
		else
		{
			backendResponse.setStatus(BackendResponse.FAILURE);
			backendResponse.setError("Dude! How many time will you decline/approve it! Its approved/declined already.");
		}
		
		return backendResponse;
	}
	
	/**
	 * PARDON ME FOR THE CODE DUPLICATION IN THE BELOW FUNCTIONS !! Not in a mood to write elegant code
	 */
	public BackendResponse handleSelfDebitInitiation(Transaction transaction){
	BackendResponse backendResponse = new BackendResponse();
		
		Account from_account;
		
		//Get the account details of from_account.
		from_account = this.accountDAO.findAccountById(transaction.getFrom_acc());

		//check if t_amount is >= balance of from_account
		if(from_account.getAcc_balance() < transaction.getT_amount())
		{
			//returned JSON should have error message!
			backendResponse.setStatus(BackendResponse.FAILURE);
			backendResponse.setError("Insufficient funds! Ensure you have required balance to perform this transaction");
			return backendResponse;
		}
		
		from_account.setAcc_balance((from_account.getAcc_balance() - transaction.getT_amount()));
		accountDAO.updateAccount(from_account);
		//String severity = (transaction.getT_amount() > 5000) ? TransactionService.CRITICAL : TransactionService.NORMAL;
		transaction.setSeverity(TransactionService.NORMAL);
		transaction.setMobile("0");
		transaction.setEmail("abcd");
		transaction.setT_status(TransactionService.SEFLDEBIT_INITIATED);
		transaction.setOtp_status(TransactionService.NOT_APPLICABLE);
		transaction.setRemarks("Self debit initiated!");
		Date date = new Date();
		transaction.setT_timestamp(date);
		//should we jus use sql generated ID? This id is not getting saved to db! Same goes for account ID as well!
		transaction.setT_id((long)ThreadLocalRandom.current().nextInt(100, 10000 + 1));
		transactionDAO.saveTransaction(transaction);
		backendResponse.setStatus(BackendResponse.SUCCESS);
		
		return backendResponse;		
	}
	
	public BackendResponse handleApproveSelfDebit(Transaction transaction)
	{
		//Employee will approve a self debit initiated by customer! 
		BackendResponse backendResponse = new BackendResponse();
		Transaction transactionFromDB = transactionDAO.getTransaction(transaction);
		
		if(transactionFromDB.getT_status().equals(TransactionService.SEFLDEBIT_INITIATED))
		{
			transactionFromDB.setT_status(TransactionService.SEFLDEBIT_PROCESSED);
			transactionDAO.updateTransaction(transactionFromDB);
			backendResponse.setStatus(BackendResponse.SUCCESS);
		}
		else
		{
			backendResponse.setStatus(BackendResponse.FAILURE);
			backendResponse.setError("Trying to approve an already approved SelfDebit request");
		}
		
		return backendResponse;
	}
	
	public BackendResponse handleRejectSelfDebit(Transaction transaction)
	{
		//Employee rejecting a self debit initiated by customer! 
		BackendResponse backendResponse = new BackendResponse();
		Transaction transactionFromDB = transactionDAO.getTransaction(transaction);
		
		if(transactionFromDB.getT_status().equals(TransactionService.SEFLDEBIT_INITIATED))
		{
			transactionFromDB.setT_status(TransactionService.SEFLDEBIT_REJECTED);
			transactionDAO.updateTransaction(transactionFromDB);
			Account from_account = this.accountDAO.findAccountById(transaction.getFrom_acc());
			from_account.setAcc_balance(from_account.getAcc_balance() + transactionFromDB.getT_amount());
			accountDAO.updateAccount(from_account);
			
			backendResponse.setStatus(BackendResponse.SUCCESS);
		}
		else
		{
			backendResponse.setStatus(BackendResponse.FAILURE);
			backendResponse.setError("Trying to reject an already rejected SelfDebit request");
		}
		
		return backendResponse;
	}


	public BackendResponse handleSelfCreditInitiation(Transaction transaction){
	BackendResponse backendResponse = new BackendResponse();
		
		//String severity = (transaction.getT_amount() > 5000) ? TransactionService.CRITICAL : TransactionService.NORMAL;
		transaction.setSeverity(TransactionService.NORMAL);
		transaction.setT_status(TransactionService.SEFLCREDIT_INITIATED);
		transaction.setOtp_status(TransactionService.NOT_APPLICABLE);
		transaction.setRemarks("Self credit initiated!");
		transaction.setMobile("0");
		transaction.setEmail("abcd");
		Date date = new Date();
		transaction.setT_timestamp(date);
		//should we jus use sql generated ID? This id is not getting saved to db! Same goes for account ID as well!
		transaction.setT_id((long)ThreadLocalRandom.current().nextInt(100, 10000 + 1));
		transactionDAO.saveTransaction(transaction);
		backendResponse.setStatus(BackendResponse.SUCCESS);
		
		return backendResponse;		
	}
	
	public BackendResponse handleApproveSelfCredit(Transaction transaction)
	{
		//Employee will approve a self credit initiated by customer! 
		BackendResponse backendResponse = new BackendResponse();
		Transaction transactionFromDB = transactionDAO.getTransaction(transaction);
		
		if(transactionFromDB.getT_status().equals(TransactionService.SEFLCREDIT_INITIATED))
		{
			transactionFromDB.setT_status(TransactionService.SEFLCREDIT_PROCESSED);
			transactionDAO.updateTransaction(transactionFromDB);
			Account to_account = this.accountDAO.findAccountById(transaction.getTo_acc());
			to_account.setAcc_balance(to_account.getAcc_balance() + transactionFromDB.getT_amount());
			accountDAO.updateAccount(to_account);

			backendResponse.setStatus(BackendResponse.SUCCESS);
		}
		else
		{
			backendResponse.setStatus(BackendResponse.FAILURE);
			backendResponse.setError("Trying to approve an already approved SelfCredit request");
		}
		
		return backendResponse;
	}
	
	public BackendResponse handleRejectSelfCredit(Transaction transaction)
	{
		//Employee rejecting a self debit initiated by customer! 
		BackendResponse backendResponse = new BackendResponse();
		Transaction transactionFromDB = transactionDAO.getTransaction(transaction);
		
		if(transactionFromDB.getT_status().equals(TransactionService.SEFLCREDIT_INITIATED))
		{
			transactionFromDB.setT_status(TransactionService.SEFLCREDIT_REJECTED);
			transactionDAO.updateTransaction(transactionFromDB);
			
			backendResponse.setStatus(BackendResponse.SUCCESS);
		}
		else
		{
			backendResponse.setStatus(BackendResponse.FAILURE);
			backendResponse.setError("Trying to reject an already rejected SelfCredit request");
		}
		
		return backendResponse;
	}

	
	
	public BackendResponse handleQuickTransfer(Transaction transaction)
	{
		BackendResponse backendResponse = new BackendResponse();
		long to_acc=0;
		
		if (!transaction.getEmail().isEmpty())
		{
			//payment through email. go to e_users, get acc no(to_acc) for this email.
			to_acc = transactionDAO.getFromAccountByEmail(transaction.getEmail());
		}
		else if(!transaction.getMobile().isEmpty())
		{
			//payment through mobile. go to e_users, get acc no(to_acc) for this mobile number.
			to_acc = transactionDAO.getFromAccountByMobile(transaction.getMobile());
		}
		
		transaction.setTo_acc(to_acc);
		
		backendResponse = this.handleTransfer(transaction);
		
		//create a transaction object and call handleFundTransfer()
		
		return backendResponse;
	}
	
	public BackendResponse handleListTransactions(Transaction transaction)
	{
		BackendResponse backendResponse = new BackendResponse();
		List<Transaction> transaction_list = this.transactionDAO.listTransactions(transaction); 
		//ObjectMapper trans_mapper = new ObjectMapper();
		backendResponse.setData(transaction_list);
		backendResponse.setStatus(BackendResponse.SUCCESS);
		return backendResponse;
	}
	
	public BackendResponse handleCustomerPurchase(Transaction transaction)
	{
		BackendResponse backendResponse = new BackendResponse();
		CreditCard creditCard = this.creditCardDAO.getDetails(transaction.getT_custid());
		if((creditCard.getAmount_used()+transaction.getT_amount())<=creditCard.getCredit_limit())
		{
			//under limit.
			creditCard.setAmount_spent(creditCard.getAmount_spent()+transaction.getT_amount());
			creditCard.setAmount_used(creditCard.getAmount_used()+transaction.getT_amount());
			this.creditCardDAO.updateCreditCard(creditCard);
			transaction.setFrom_acc(-1);
			transaction.setTo_acc(-1);
			transaction.setSeverity(NORMAL);
			transaction.setT_status(PURCHASED);
			transaction.setOtp_status(NOT_APPLICABLE);
			Date date = new Date();
			transaction.setT_timestamp(date);
			//should we jus use sql generated ID? This id is not getting saved to db!
			transaction.setT_id((long)ThreadLocalRandom.current().nextInt(100, 10000 + 1));
			transactionDAO.saveTransaction(transaction);
			backendResponse.setStatus(BackendResponse.SUCCESS);
			backendResponse.setData(transaction);
		}
		else
		{
			backendResponse.setStatus(BackendResponse.FAILURE);
			backendResponse.setError("Purchase not allowed. Reached credit card limit!");
		}
		return backendResponse;
	}
}
