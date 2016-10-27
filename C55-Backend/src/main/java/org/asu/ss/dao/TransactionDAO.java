package org.asu.ss.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.asu.ss.model.Account;
import org.asu.ss.model.ExternalUser;
import org.asu.ss.model.OTP;
import org.asu.ss.model.TempExternalUser;
import org.asu.ss.service.TransactionService;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TransactionDAO {
	
	
	private org.asu.ss.model.Transaction transaction;
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}
	
	public void saveTransaction(org.asu.ss.model.Transaction transaction)
	{
		Session session = sessionFactory.openSession();
		Transaction sys_transaction = session.beginTransaction();
		
		try
		{
			//validate transaction fields
			//this.validateTransaction(transaction);
			
			//commit transaction
			session.save(transaction);
			sys_transaction.commit();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			sys_transaction.rollback();
		}
		finally
		{
			session.close();
		}
	}
	
	public org.asu.ss.model.Transaction getTransaction(org.asu.ss.model.Transaction transaction)
	{
		org.asu.ss.model.Transaction transactionFromDB = null;
		Session session = sessionFactory.openSession();
		Transaction sysTransaction = session.beginTransaction();
		
		try
		{
			transactionFromDB = (org.asu.ss.model.Transaction) session.get(org.asu.ss.model.Transaction.class, transaction.getT_id());
			System.out.println("org.asu.ss.dao.TransactionDAO:getTransaction "+ 
					transactionFromDB.getFrom_acc() + " " +  
					transactionFromDB.getTo_acc() + " " + 
					transactionFromDB.getT_id() + " " +
					transactionFromDB.getSeverity() + " " +
					transactionFromDB.getT_status());
			sysTransaction.commit();
			session.close();
		}
		catch (Exception e) {
			e.printStackTrace();
			sysTransaction.commit();
			session.close();
		}
		return transactionFromDB;
	}
	
	public org.asu.ss.model.Transaction getCriticalTransaction(org.asu.ss.model.Transaction transaction)
	{
		org.asu.ss.model.Transaction transactionFromDB;
		Session session = sessionFactory.openSession();
		Transaction sysTransaction = session.beginTransaction();
		
		System.out.println("org.asu.ss.dao.TransactionDAO:getCriticalTransaction "+ 
		transaction.getFrom_acc() + " " +  
		transaction.getTo_acc() + " " + 
		transaction.getT_id() + " " +
		transaction.getSeverity() + " " +
		transaction.getT_status());
		
		try {
			transactionFromDB = (org.asu.ss.model.Transaction) session.get(org.asu.ss.model.Transaction.class, transaction.getT_id());
			System.out.println("org.asu.ss.dao.TransactionDAO:getCriticalTransaction. From DB "+ 
					transactionFromDB.getFrom_acc() + " " +  
					transactionFromDB.getTo_acc() + " " + 
					transactionFromDB.getT_id() + " " +
					transactionFromDB.getSeverity() + " " +
					transactionFromDB.getT_status());
			if (transactionFromDB.getT_id() == transaction.getT_id() && 
					transactionFromDB.getSeverity().equals(TransactionService.CRITICAL) && 
					transactionFromDB.getT_status().equals(TransactionService.PENDING) && 
					transactionFromDB.getTo_acc() == transaction.getTo_acc())
			{
				System.out.println("org.asu.ss.dao.TransactionDAO:getCriticalTransaction. From DB before return "+ transactionFromDB.getFrom_acc() + " " +  
						transactionFromDB.getTo_acc() + " " + transactionFromDB.getT_id());
				return transactionFromDB;
			}
			sysTransaction.commit();
			session.close();

		} catch (Exception e) {
			e.printStackTrace();
			sysTransaction.commit();
			session.close();
		}
		return null;
	}
	
	public void updateTransaction(org.asu.ss.model.Transaction transaction)
	{
		Session session = sessionFactory.openSession();
		Transaction sysTransaction = session.beginTransaction();
		org.asu.ss.model.Transaction transactionFromDB;

		try {
			transactionFromDB = (org.asu.ss.model.Transaction)session.get(org.asu.ss.model.Transaction.class, transaction.getT_id());
			transactionFromDB.setT_status(transaction.getT_status());
			session.update(transactionFromDB);
			sysTransaction.commit();
		} catch (Exception e) {
			sysTransaction.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}

	}
	
	
	public void updateOTPTransaction(org.asu.ss.model.Transaction transaction)
	{
		Session session = sessionFactory.openSession();
		Transaction sysTransaction = session.beginTransaction();
		org.asu.ss.model.Transaction transactionFromDB;

		try {
			transactionFromDB = (org.asu.ss.model.Transaction)session.get(org.asu.ss.model.Transaction.class, transaction.getT_id());
			transactionFromDB.setT_status(transaction.getT_status());
			transactionFromDB.setOtp_status(transaction.getOtp_status());
			session.update(transactionFromDB);
			sysTransaction.commit();
		} catch (Exception e) {
			sysTransaction.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}

	}
	
	
	public ExternalUser getUserByCustomerID(long cust_id)
	{
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();

		ExternalUser eUser = null;
		try {
			Query query = session.createQuery("FROM " + ExternalUser.class.getName() + " ee where ee.cust_id = :t_custid");
			query.setParameter("t_custid", cust_id);
			List<org.asu.ss.model.ExternalUser> list = query.list();
			if(list!=null)
				eUser = list.get(0);
			System.out.println("List:   " + list);
			//eUser = (ExternalUser) session.get(ExternalUser.class, email);
			transaction.commit();
			session.close();

		} catch (Exception e) {
			e.printStackTrace();
			transaction.commit();
			session.close();
		}
		return eUser;
	}
	
	public long getFromAccountByEmail(String email)
	{
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();

		ExternalUser eUser = null;
		try {
			Query query = session.createQuery("FROM " + ExternalUser.class.getName() + " ee where ee.email = :t_email");
			query.setParameter("t_email", email);
			List list = query.list();
			eUser = (ExternalUser) list.get(0);
			System.out.println("List:   " + list);
			//eUser = (ExternalUser) session.get(ExternalUser.class, email);
			transaction.commit();
			session.close();

		} catch (Exception e) {
			e.printStackTrace();
			transaction.commit();
			session.close();
		}
		return eUser.getAcc_no1();
	}
	
	public long getFromAccountByMobile(String mobile)
	{
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();

		ExternalUser eUser = null;
		try {
			Query query = session.createQuery("FROM " + ExternalUser.class.getName() + " ee where ee.mobile = :t_mobile");
			query.setParameter("t_mobile", mobile);
			eUser = (ExternalUser) query.uniqueResult();
			//eUser = (ExternalUser) list.get(0);
			//System.out.println("List:   " + list);
			//eUser = (ExternalUser) session.get(ExternalUser.class, mobile);
			transaction.commit();
			session.close();

		} catch (Exception e) {
			e.printStackTrace();
			transaction.commit();
			session.close();
		}
		return eUser.getAcc_no1();
	}
	
	public List<org.asu.ss.model.Transaction> listTransactions(org.asu.ss.model.Transaction transaction)
	{
		List<org.asu.ss.model.Transaction> trans_list = null;
		List<org.asu.ss.model.ExternalUser> eusers = null;
		Session session = sessionFactory.openSession();
		Transaction sysTransaction = session.beginTransaction();
		
		try
		{
			if(transaction.getSeverity().equals(TransactionService.CRITICAL) && 
					transaction.getT_status().equals(TransactionService.PENDING))
			{
				//List all pending critical transactions for manager approval.
				Query query = session.createQuery("FROM " + org.asu.ss.model.Transaction.class.getName() + " tr where tr.severity = :tr_severity and tr.t_status= :tr_status");
				query.setParameter("tr_severity", transaction.getSeverity());
				query.setParameter("tr_status", transaction.getT_status());
				trans_list = query.list();
			}
			else if(transaction.getT_status().equals(TransactionService.SEFLCREDIT_INITIATED + 
					"&&" + 
					TransactionService.SEFLDEBIT_INITIATED))
			{
				//List all debit/credit initiated transactions. 
				Query query = session.createQuery("FROM " + org.asu.ss.model.Transaction.class.getName() + " tr where tr.t_status IN (:tr_status_list)");
				List<String> tr_status_list = new ArrayList<String>();
				tr_status_list.add(TransactionService.SEFLCREDIT_INITIATED);
				tr_status_list.add(TransactionService.SEFLDEBIT_INITIATED);
				query.setParameterList("tr_status_list", tr_status_list);
				trans_list = query.list();
				System.out.println("trans_list  " + trans_list);
			}
			else if(transaction.getT_status().equals(TransactionService.INITIATED))
			{
				//List all transactions initiated by manager for customer approval
				//Do not list all of them! Get cust_id from session. get all 5 accounts from cust_id
				//Now get the list of transactions from transaction db where  from_account in (5 accounts) and status is Initiated   
				Query eusersQuery = session.createQuery("FROM " + org.asu.ss.model.ExternalUser.class.getName() + " ee where ee.cust_id= :cust_id");
				eusersQuery.setParameter("cust_id", transaction.getT_custid());
				eusers = eusersQuery.list();
				ExternalUser e_user = (ExternalUser)eusers.get(0);
				List<Long> accounts = new ArrayList<Long>();
				if (e_user.getAcc_no1()!=0)
				{
					accounts.add(e_user.getAcc_no1());
				}
				if (e_user.getAcc_no2()!=0)
				{
					accounts.add(e_user.getAcc_no2());
				}
				if (e_user.getAcc_no3()!=0)
				{
					accounts.add(e_user.getAcc_no3());
				}
				if (e_user.getAcc_no4()!=0)
				{
					accounts.add(e_user.getAcc_no4());
				}
				if (e_user.getAcc_no5()!=0)
				{
					accounts.add(e_user.getAcc_no5());
				}
				Query query = session.createQuery("FROM " + org.asu.ss.model.Transaction.class.getName() + " tr where tr.t_status= :tr_status and tr.from_acc IN (:accounts_list)");
				query.setParameter("tr_status", transaction.getT_status());
				query.setParameterList("accounts_list", accounts);
				trans_list = query.list();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			sysTransaction.commit();
			session.close();
		}
		return trans_list;
	}
	
	
	public void validateTransaction(org.asu.ss.model.Transaction transaction)
	{
		//validate all fields
		//raise exception if any
	}

}
