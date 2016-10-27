package org.asu.ss.dao;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.asu.ss.controller.AccountController;
import org.asu.ss.model.Account;
import org.asu.ss.model.AccountList;
import org.asu.ss.model.CreditCard;
import org.asu.ss.model.CreditCardCreateDetails;
import org.asu.ss.model.TransactionWindow;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AccountDAO {
	final static Logger log = Logger.getLogger(AccountController.class);

	@Autowired
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}

	public Account saveAccount(Account account) {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		System.out.println("HERE:" + account.getAcc_no() + account.getType() + account.getAcc_balance()
				+ account.getCreation_date());
		try {
			long accno = (long) session.save(account);
			transaction.commit();	
			Account newAccount = (Account) findAccountById(accno);
			return newAccount;
		} catch (Exception e) {
			transaction.rollback();
			return null;
		} finally {
			session.close();
		}
	}
	
	public long updateAccount(Account account) {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		long acc_no = 0;
		Account acc;

		System.out.println("HERE:" + account.getAcc_no() + account.getType() + account.getAcc_balance()
				+ account.getCreation_date());
		try {
			acc = (Account)session.get(Account.class, account.getAcc_no());
			if (acc.getAcc_no() == account.getAcc_no())
			{
				acc.setAcc_balance(account.getAcc_balance());
				session.update(acc);
				transaction.commit();
			}
		} catch (Exception e) {
			transaction.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}

		return acc_no;
	}

	public Account findAccountById(long accNo) {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();

		Account account = null;
		try {
			account = (Account) session.get(Account.class, accNo);
			transaction.commit();
			session.close();

		} catch (Exception e) {
			e.printStackTrace();
			transaction.commit();
			session.close();
		}
		return account;
	}

	public List<org.asu.ss.model.Transaction> getTransactionsWithinWindow(TransactionWindow transactionWindow) {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();

		String start_date= transactionWindow.getStartDate();
		String end_date= transactionWindow.getEndDate();
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
		
		List<org.asu.ss.model.Transaction> transactions = null;
		try {
			Query query = session.createQuery("from Transaction where t_timestamp between :startDate and :endDate and (from_acc= :accNo or to_acc= :accNo)");
			query.setParameter("startDate", format.parse(start_date));
			query.setParameter("endDate", format.parse(end_date));
			query.setParameter("accNo", transactionWindow.getAcc_no());
			System.out.println(transactionWindow.getAcc_no());
			transactions = query.list();			
			transaction.commit();
			session.close();

		} catch (Exception e) {
			e.printStackTrace();
			transaction.commit();
			session.close();
		}
		return transactions;
	}

	public boolean deleteAccount(Account account) {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		Account acc = findAccountById(account.getAcc_no());
		if(acc != null){
			try{
				session.delete(acc);
				transaction.commit();
				session.close();
				return true;
			}catch(Exception e){
				transaction.rollback();
				session.close();
				return false;
			}
		}
		return false;
	}
	
	public CreditCard createCreditCard(CreditCardCreateDetails ccdet) {
		log.info("Entering AccountDAO.createCreditCard with values "+ccdet.toString());
		CreditCard cc = new CreditCard();
		int cvv = (int) (Math.random()*100);
		
		LocalDate ld = new LocalDate();
		cc.setCust_id(ccdet.getCust_id());
		cc.setCvv(cvv);
		cc.setIssue_date(ld.toDate());
		cc.setExpiry_date(ld.plusYears(3).toDate());
		cc.setAmount_used(0);//amount aggregate
		cc.setAmount_spent(0);//amount_used
		cc.setCredit_limit(30000);//credit_limit
		cc.setInterest_rate(14.5f);//interest_rate
		cc.setAcc_no(ccdet.getAcc_no());//
		cc.setCardtype("master");
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		AccountList accl= new AccountList();
		String fname,lname;
		Long res;
		try{
			while(true){
				long carddigits = (long) (Math.random() * 100000000000000L);
				long ccno = 5300000000000000L + carddigits;
				Query query= session.createQuery("select acc_no from CreditCard where card_no = :cno");
				query.setParameter("cno",ccno);
				String accn= (String) query.uniqueResult();
				if(accn==null){
					cc.setCard_no(ccno);
					break;
				}
			}
			//Get first name
			Query query= session.createQuery("select f_name from ExternalUser where cust_id = :cust");
			query.setParameter("cust",ccdet.getCust_id());
			fname =(String) query.uniqueResult();
			//Get last name
			query= session.createQuery("select l_name from ExternalUser where cust_id = :cust");
			query.setParameter("cust",ccdet.getCust_id());
			lname =(String) query.uniqueResult();
			cc.setCard_name(fname+" "+lname);
			
			query= session.createQuery("select count(*) from CreditCard where cust_id = :cust");
			query.setParameter("cust",ccdet.getCust_id());
			Long cust_id =(Long) query.uniqueResult();
			
			if(cust_id>0)
			{
				throw new Exception();
			}
			res=(Long)session.save(cc);
			log.info("AccountDAO.createCreditCard before storing to table "+cc.toString());
			if(res!=0){
			transaction.commit();
			}
			else{
				return null;
			}
			session.close();
		}catch(Exception e){
			if(!transaction.wasCommitted()){
				transaction.commit();
			}
			if(session.isOpen()){
			session.close();
			}
			return null;
		}
		
		
		return cc;
	}


}
