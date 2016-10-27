package org.asu.ss.service;
import java.util.List;
import org.apache.log4j.Logger;
import org.asu.ss.controller.AccountController;
import org.asu.ss.dao.CreditCardDAO;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CreditCardService {

	final static Logger log = Logger.getLogger(AccountController.class);

	@Autowired
	private CreditCardDAO creditCardDAO;

	public void setCreditCardDAO(CreditCardDAO creditCardDAO) {
		this.creditCardDAO = creditCardDAO;
	}

	public int transactionPayment(CreditCardTransaction cctrans)
	{
			if(creditCardDAO.isValidPayment(cctrans)==1){
				return creditCardDAO.makePayment(cctrans);
		}
		return 0;
	}

	public AccountList getCustomerAccounts(CustomerDetail custid)
	{
		return creditCardDAO.getCustomerAccounts(custid);
	}

	public CreditCard createCreditCard(CreditCardCreateDetails ccdet) {
		log.info("Entering CreditCardService.createCreditCard()");
		return creditCardDAO.createCreditCard(ccdet);
	}

	public CreditCardBalance viewBalance(CreditCardBalance ccbal) {
		return creditCardDAO.viewBalance(ccbal);
	}

	public int updateLimit(CreditCardUpdateLimit ccdet) {
		return creditCardDAO.updateLimit(ccdet);
	}

	public CreditCardAmountDue amountDue(CreditCardTransaction cctrans)
		{
		return creditCardDAO.amountDue(cctrans);
		}

	public CreditCard getDetails(long cust_id) {
		return creditCardDAO.getDetails(cust_id);
	}
	public List<Transaction> retrieveTransactionRequests(long custId) {
		log.info("Entering CreditCardService.retrieveTransactionRequests()");
		return creditCardDAO.retrieveTransactionRequest(custId);
	}

	public boolean approveTransactionRequests(RequestApprove reqapp) {
		log.info("Entering CreditCardService.approveTransactionRequests()");
		return creditCardDAO.approveTransactionRequest(reqapp);
	}


}
