package org.asu.ss.dao;

import org.asu.ss.model.Admin;
import org.asu.ss.model.ExternalUser;
import org.asu.ss.model.InternalUser;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class LoginDAO {

	@Autowired
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}
	
	public void updateExtUser(org.asu.ss.model.Transaction transaction)
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

	
	public ExternalUser getExtUser(String username, String password, String user_type) {
		Session session = sessionFactory.openSession();
		Transaction sysTransaction = session.beginTransaction();
		
		if(user_type.equals("Merchant")) 
			user_type="organization";
		else
			user_type="person";
		ExternalUser extUser = null; // Is returning Null fine ?
		try {
			Query query = session.createQuery("from ExternalUser where username = :username and password = :password and user_type = :user_type");
			query.setParameter("username", username);
			query.setParameter("password", password);
			query.setParameter("user_type", user_type);
			
			extUser = (ExternalUser) query.uniqueResult();
			if(null != extUser && extUser.getLogin_counter() <= 10 ){
				extUser.setLogin_counter(0);
				session.update(extUser);
				sysTransaction.commit();
			}else{
				query = session.createQuery("from ExternalUser where username = :username and user_type= : user_type");
				query.setParameter("username", username);
				query.setParameter("user_type", user_type);
				extUser = (ExternalUser) query.uniqueResult();
				
				if(null != extUser){
					int counter_value = extUser.getLogin_counter();
					counter_value++;
					extUser.setLogin_counter(counter_value);
					session.update(extUser);
					sysTransaction.commit();
				}
				return extUser;
			}
			
			session.close();

		} catch (Exception e) {
			sysTransaction.rollback();
			session.close();
		}
		return extUser;
	}
	
	

	public InternalUser getIntUser(String username, String password, String custType) {
		Session session = sessionFactory.openSession();

		if(custType.equals("Manager"))
			custType="manager";
		else
			custType="regular";
		InternalUser intUser = null; // Is returning Null fine ?
		try {
			Query query = session.createQuery("from InternalUser where email = :username and password = :password and access_level = :access_level");
			query.setParameter("username", username);
			query.setParameter("password", password);
			query.setParameter("access_level", custType);
			intUser = (InternalUser) query.uniqueResult();
			session.close();

		} catch (Exception e) {
			session.close();
		}
		return intUser;
	}

	public Admin getAdmin(String username, String password) {
		Session session = sessionFactory.openSession();

		Admin admin = null; // Is returning Null fine ?
		try {
			Query query = session.createQuery("from Admin where email = :username and password = :password");
			query.setParameter("username", username);
			query.setParameter("password", password);
			
			admin = (Admin) query.uniqueResult();
			session.close();

		} catch (Exception e) {
			session.close();
		}
		return admin;
	}

}
