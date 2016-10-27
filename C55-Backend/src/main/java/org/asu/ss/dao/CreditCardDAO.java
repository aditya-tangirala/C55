package org.asu.ss.dao;


import java.util.*;

import org.apache.log4j.Logger;
import org.asu.ss.model.*;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CreditCardDAO {

	final static Logger log = Logger.getLogger(CreditCardDAO.class);

	@Autowired
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}

	public int isValidPayment(CreditCardTransaction cctrans) {

		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		CreditCard cc= null; // Is returning Null fine ?
		Account acc= null;
		try{
			Query query= session.createQuery("from CreditCard where cust_id = :custid");
			query.setParameter("custid",cctrans.getCust_id());
			cc = (CreditCard) query.uniqueResult();
			query= session.createQuery("from Account where acc_no = :accn");
			query.setParameter("accn",cc.getAcc_no());
			acc = (Account) query.uniqueResult();
			Double newbalance= acc.getAcc_balance() - cctrans.getT_amt();
			transaction.commit();
			session.close();
			if(newbalance > 0){
				return 1;
			}
			else{
				return 0;
			}

		}catch(Exception e){
			if(!transaction.wasCommitted()){
				transaction.commit();
			}
			if(session.isOpen()){
			session.close();
			}
			return 0;
		}


	}

	public CreditCardAmountDue amountDue(CreditCardTransaction cctrans)
		{
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		CreditCard cc= null; // Is returning Null fine ?
		CreditCardAmountDue ccad=new CreditCardAmountDue();
		try{
			Query query= session.createQuery("from CreditCard where cust_id = :custid");
			query.setParameter("custid",cctrans.getCust_id());
			cc = (CreditCard) query.uniqueResult();
			Double bal1=cc.getAmount_used()-cc.getAmount_spent();
			Double int1=(bal1*(1/6.0)*cc.getInterest_rate()/100);
			Double int2=(cc.getAmount_spent()*(1/12.0)*cc.getInterest_rate()/100);
			Double fine = ((0.5/100)*bal1);
			Double due = cc.getAmount_used()+int1+int2+fine;
			ccad.setAmtdue(Math.ceil(due));
			transaction.commit();
			session.close();

			return ccad;

		}catch(Exception e){
			if(!transaction.wasCommitted()){
				transaction.commit();
			}
			if(session.isOpen()){
			session.close();
			}
			return null;
		}
		}

	public int makePayment(CreditCardTransaction cctrans) {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		CreditCard cc= null; // Is returning Null fine ?
		Account acc=null;
		try{
			Query query= session.createQuery("from CreditCard where cust_id = :custid");
			query.setParameter("custid",cctrans.getCust_id());
			cc = (CreditCard) query.uniqueResult();

			query= session.createQuery("from Account where acc_no = :accn");
			query.setParameter("accn",cc.getAcc_no());
			acc = (Account) query.uniqueResult();

			Double bal1=cc.getAmount_used()-cc.getAmount_spent();
			Double int1=(bal1*(1/6.0)*cc.getInterest_rate()/100);
			Double int2=(cc.getAmount_spent()*(1/12.0)*cc.getInterest_rate()/100);
			Double fine = ((0.5/100)*bal1);
			Double due = cc.getAmount_used()+int1+int2+fine;
			if(cctrans.getT_amt()!=Math.ceil(due))
				{
				return 0;
				}
			Double new_bal=0.0;
			query= session.createQuery("UPDATE CreditCard set amount_spent=:amtspent , amount_aggregte=:amt_agg where cust_id = :custid");
			query.setParameter("amtspent", new_bal);
			query.setParameter("amt_agg", new_bal);
			query.setParameter("custid",cctrans.getCust_id());
			query.executeUpdate();

			Double newbalance= acc.getAcc_balance() - cctrans.getT_amt();

			query= session.createQuery("UPDATE Account set acc_balance=:newbalance where acc_no = :accn");
			query.setParameter("newbalance", newbalance);
			query.setParameter("accn",cc.getAcc_no());
			query.executeUpdate();
			transaction.commit();
			session.close();


		}catch(Exception e){
			if(!transaction.wasCommitted()){
				transaction.commit();
			}
			if(session.isOpen()){
			session.close();
			}
			return 0;
		}

		return 1;

	}

	public AccountList getCustomerAccounts(CustomerDetail custid) {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		AccountList accl= new AccountList();
		try{
			Query query= session.createQuery("select acc_nos from ExternalUser where cust_id = :cust");
			query.setParameter("cust",custid.getCust_id());
			String accnolist = (String) query.uniqueResult();
			if(accnolist == null){
				throw new Exception();
			}
			accl.setAcc_no(accnolist);
			transaction.commit();
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

		return accl;
	}

	public CreditCard createCreditCard(CreditCardCreateDetails ccdet) {
		log.info("Entering CreditCardDAO.createCreditCard with values "+ccdet.toString());
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
			log.info("CreditCardController.createCreditCard before storing to table "+cc.toString());
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

	public CreditCardBalance viewBalance(CreditCardBalance ccbal) {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		CreditCardViewBalance ccvbal=null;
		try{
			Query query= session.createQuery("from CreditCardViewBalance where cust_id = :custid");
			query.setParameter("custid",ccbal.getCust_id());
			ccvbal = (CreditCardViewBalance) query.uniqueResult();
			if(ccvbal == null){
				throw new Exception();
			}
			double balance = ccvbal.getCredit_limit()-ccvbal.getAmount_used();
			ccbal.setBalance(balance);
			transaction.commit();
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

		return ccbal;
	}


	public int updateLimit(CreditCardUpdateLimit ccdet) {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		CreditCard cc= null; // Is returning Null fine ?
		try{
			Query query= session.createQuery("from CreditCard where card_no = :ccno");
			query.setParameter("ccno",ccdet.getCcno());
			cc = (CreditCard) query.uniqueResult();
			if(cc.getAmount_used()>ccdet.getNewlimit()){
				transaction.commit();
				session.close();
				return 0;
			}
			query= session.createQuery("UPDATE CreditCard set credit_limit=:cclimit where card_no=:ccno");
			query.setParameter("cclimit", ccdet.getNewlimit());
			query.setParameter("ccno",ccdet.getCcno());
			query.executeUpdate();

			transaction.commit();
			session.close();


		}catch(Exception e){
			if(!transaction.wasCommitted()){
				transaction.commit();
			}
			if(session.isOpen()){
			session.close();
			}
			return 0;
		}

		return 1;


	}

	public CreditCard getDetails(long cust_id) {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		CreditCard cc= null; // Is returning Null fine ?
		Account acc=null;
		try{
			Query query= session.createQuery("from CreditCard where cust_id = :custid");
			query.setParameter("custid",cust_id);
			cc = (CreditCard) query.uniqueResult();
			transaction.commit();
			session.close();


		}catch(Exception e){
			if(!transaction.wasCommitted()){
				transaction.commit();
			}
			if(session.isOpen()){
			session.close();
			}
		}

		return cc;
	}
	public List<org.asu.ss.model.Transaction> retrieveTransactionRequest(long custId) {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		List<org.asu.ss.model.Transaction> reqlist = new ArrayList<org.asu.ss.model.Transaction>();
		try{
			Query query = session.createQuery("from ExternalUser where cust_id=:custid");
			query.setParameter("custid", custId);
			ExternalUser euser= (ExternalUser) query.uniqueResult();
			query= session.createQuery("from Transaction where t_status=:purchase and remarks=:remark");
			query.setParameter("purchase", "purchase");
			query.setParameter("remark", euser.getOrg_name());
			reqlist=query.list();
			return reqlist;

		}catch(Exception e){
			log.error("Exit CreditCardController.retrieveTransactionRequests failed ");

			if(session.isOpen()){
			session.close();
			}
		}
		return reqlist;
	}

	public boolean approveTransactionRequest(RequestApprove reqapp) {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		log.info("Enter CreditCardController.approveTransactionRequests with values: "+reqapp.toString());
		try{
			Query query= session.createQuery("DELETE Transaction where t_custid = :custid");
			query.setParameter("custid", reqapp.getCustid());
			query.executeUpdate();
			transaction.commit();
			session.close();

		}catch(Exception e){
			log.error("Exit CreditCardController.approveTransactionRequests failed ");
			try{
				transaction.rollback();
			}catch(Exception e1)
			{
				log.error("CreditCardController.approveTransactionRequests rollback failed");
			}
			if(session.isOpen()){
			session.close();
			}
			return false;
		}
		return true;
	}

	public void updateCreditCard(org.asu.ss.model.CreditCard creditCard)
	{
		Session session = sessionFactory.openSession();
		Transaction sysTransaction = session.beginTransaction();
		org.asu.ss.model.CreditCard creditCardFromDB;

		try {
			creditCardFromDB = (org.asu.ss.model.CreditCard)session.get(org.asu.ss.model.CreditCard.class, creditCard.getCust_id());
			creditCardFromDB.setAmount_spent(creditCard.getAmount_spent());
			creditCardFromDB.setAmount_used(creditCard.getAmount_used());
			session.update(creditCardFromDB);
			sysTransaction.commit();
		} catch (Exception e) {
			sysTransaction.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}

	}

}
