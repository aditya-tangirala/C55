package org.asu.ss.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.asu.ss.controller.AccountController;
import org.asu.ss.dao.AccountDAO;
import org.asu.ss.model.Account;
import org.asu.ss.model.CreditCard;
import org.asu.ss.model.CreditCardCreateDetails;
import org.asu.ss.model.Transaction;
import org.asu.ss.model.TransactionWindow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class AccountService {
	
	final static Logger log = Logger.getLogger(AccountController.class);
	
	@Autowired
	private AccountDAO accountDAO;

	public void setAccountDAO(AccountDAO accountDAO){
		this.accountDAO = accountDAO;
	}
	
	public List<Transaction> getTransactionsWithinWindow(TransactionWindow transactionWindow){
		return this.accountDAO.getTransactionsWithinWindow(transactionWindow);
	}
	
	public Account findAccountById(long id){
		return this.accountDAO.findAccountById(id);
	}
	
	public Account saveAccount(Account account){
		return this.accountDAO.saveAccount(account);
	}

	public boolean deleteAccount(Account account) {
		return this.accountDAO.deleteAccount(account);
	}
	public CreditCard createCreditCard(CreditCardCreateDetails ccdet) {
		log.info("Entering AccountService.createCreditCard()");
		return accountDAO.createCreditCard(ccdet);
	}
}
