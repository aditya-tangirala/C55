package org.asu.ss.dao;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import org.asu.ss.controller.SendMail;
import org.asu.ss.model.CreditCard;
import org.asu.ss.model.InternalUser;
import org.asu.ss.model.PasswordReset;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

@Repository
public class IntDAO {

	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public boolean pwdReset(PasswordReset pwdres) {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		Long res;
		try
		{	
			Long ct=0L;
			Long ct2=0L;
			Query query= session.createQuery("select count(*) from PasswordReset where e_id = :eid");
			query.setParameter("eid",pwdres.getEid());
			ct =(Long) query.uniqueResult();
			
			query= session.createQuery("select count(*) from InternalUser where e_id = :eid");
			query.setParameter("eid",pwdres.getEid());
			ct2 =(Long) query.uniqueResult();
			
			if(ct>0 || pwdres.isFlag()==true || ct2==0)
			{
				return false;
			}
			res=(Long)session.save(pwdres);
			if(res!=0){
				transaction.commit();
				session.close();
			}
		}catch(Exception e)
		{
			if(!transaction.wasCommitted()){
				transaction.rollback();
			}
			if(session.isOpen()){
			session.close();
			}
			return false;
		}
		return true;
	}

	public List<PasswordReset> retrieveRequest() {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		List<PasswordReset> reqlist = new ArrayList<PasswordReset>();
		try{
			reqlist= (List<PasswordReset>) session.createSQLQuery("select * from ResetRequest where flag = 0").list();	
		}catch(Exception e){
			e.printStackTrace();
		}
		finally
		{
			session.close();
		}
		return reqlist;
	}

	public boolean approveRequest(PasswordReset pwdres) {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		Long res;
		try
			{	
			Query query;
			query= session.createQuery("select count(*) from PasswordReset where e_id = :eid");
			query.setParameter("eid",pwdres.getEid());
			Long ct2 =(Long) query.uniqueResult();
			System.out.println("Ct"+ct2+"Flag"+pwdres.isFlag()+"Eid"+pwdres.getEid());
			if(ct2==0){
				return false;
			}
			if(pwdres.isFlag()==false)
				{
				query= session.createQuery("delete PasswordReset where e_id = :eid");
				query.setParameter("eid",pwdres.getEid());
				int result = query.executeUpdate();
				if(result==0){
					return false;
				}
				}
			else
				{
				InternalUser iu=null;
				query= session.createQuery("FROM InternalUser where e_id = :eid");
				query.setParameter("eid",pwdres.getEid());
				iu=(InternalUser) query.uniqueResult();
				long temppassword = (long) (Math.random() * 100000000L);
				String pwd = Long.toString(temppassword) + "$%&)";
				System.out.println("IntDAO.approveRequest() before : "+pwd);
				String pwd1 = new String(hash(pwd));
				System.out.println("IntDAO.approveRequest() after : "+pwd1);
				query = session.createQuery("UPDATE InternalUser set password= :pwd where e_id = :eid");
				query.setParameter("pwd", pwd1);
				query.setParameter("eid", pwdres.getEid());
				if(query.executeUpdate()==0)
					{
					return false;
					}
				SendMail sm= new SendMail();
				System.out.println("Email: "+iu.getEmail()+"Password: "+temppassword);
				sm.sendMail(iu.getEmail(), pwd);
				query= session.createQuery("delete PasswordReset where e_id = :eid");
				query.setParameter("eid",pwdres.getEid());
				int result = query.executeUpdate();
				if(result==0){
					return false;
					}
				}
			if(!transaction.wasCommitted()){
				transaction.commit();
				}
			if(session.isOpen()){
				session.close();
				}
		}catch(Exception e)
		{
			e.printStackTrace();
			if(!transaction.wasCommitted()){
				transaction.rollback();
			}
			if(session.isOpen()){
			session.close();
			}
			return false;
		}
		finally{
			if(session.isOpen()){
				session.close();
				}
		}
		return true;
	}
	
	public byte[] hash(String password) throws NoSuchAlgorithmException {
	    MessageDigest sha256 = MessageDigest.getInstance("SHA-256");        
	    byte[] passBytes = password.getBytes();
	    byte[] passHash = sha256.digest(passBytes);
	    return passHash;
	}

	
}
