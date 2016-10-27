package org.asu.ss.dao;

import java.util.List;

import org.asu.ss.model.ExternalUser;
import org.asu.ss.model.OTP;
import org.asu.ss.model.TempExternalUser;
import org.asu.ss.model.TempTransaction;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ExtDAO {
	@Autowired
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}

	// Add external user
	public ExternalUser saveExternalUser(ExternalUser externaluser) {
		externaluser.setSsn((long)(externaluser.getSsn()));
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		System.out.println("Reached Backend");
		if (externaluser != null) {
			try {
				long cust_id = (Long)session.save(externaluser);
				transaction.commit();
				ExternalUser extreturn = findUserById(cust_id);
				return extreturn;
			} catch (Exception e) {
				transaction.rollback();
				session.close();
				return null;
			}
		}
		return null;
	}
	
	public String declineRequest(TempExternalUser tempexternaluser) {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();

		TempExternalUser tempdetails = null; // Is returning Null fine ?
		try {
			System.out.println("Reached fetchtempdetailsforOTPValidation");
			Query query = session.createQuery("from TempExternalUser where cust_id = :c and id = :i ");
			query.setParameter("i", tempexternaluser.getId());
			query.setParameter("c", tempexternaluser.getCust_id());
			tempdetails = (TempExternalUser) query.uniqueResult();
			
			String hql = "UPDATE "+ TempExternalUser.class.getName()+" set flag = 1 "  + 
		             "WHERE id = :otp_id and cust_id = :cust";
			Query query1 = session.createQuery(hql);
			query1.setParameter("cust", tempexternaluser.getCust_id());
			query1.setParameter("otp_id", tempexternaluser.getId());
			int result = query1.executeUpdate();
			transaction.commit();
		} catch (Exception e) {
			System.out.println("Here it is" + e);
			transaction.commit();
		} finally {
			session.close();
		}
		return "Decline Successful";
	}

	// To delete external user
	public boolean deleteExternalUser(ExternalUser user) {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		ExternalUser extUser = findUserById(user.getCust_id());
		if (extUser != null) {
			try {
				session.delete(extUser);
				transaction.commit();
				return true;
			} catch (Exception e) {
				transaction.rollback();
				session.close();
				return false;
			}
		}
		return false;
	}

	// For Profile Update
	public TempExternalUser tempExternalUser(TempExternalUser tempexternaluser) {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		TempExternalUser tempUser = null;
		long id = 0;

		if (tempexternaluser != null) {
			try {
				id = (long) session.save(tempexternaluser);
				System.out.println(id);
				if (id != 0)
					transaction.commit();
				tempUser = (TempExternalUser) session.get(TempExternalUser.class, id);
				System.out.println("Hey");

			} catch (Exception e) {
				System.out.println("Hello:" + e);
				transaction.rollback();
			} finally {
				session.close();
			}
		}
		return tempUser;
	}

	public void tempWriteOTPtoDB(OTP otpToDB) {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();

		if (otpToDB != null) {
			try {
				System.out.println("Hey");
				session.save(otpToDB);
				transaction.commit();
			} catch (Exception e) {
				transaction.rollback();
				System.out.println("Email" + e);
			} finally {
				session.close();
			}
		}
	}

	public OTP fetchdetailsforOTPValidation(long c_id, long id) {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();

		OTP otpObject = null; // Is returning Null fine ?
		try {
			System.out.println("Reached fetchdetailsforOTPValidation");
			Query query = session.createQuery("from OTP where c_id = :c and id = :i ");
			query.setParameter("i", id);
			query.setParameter("c", c_id);
			otpObject = (OTP) query.uniqueResult();
			// otpObject = (OTP) session.get(OTP.class, c_id,id);
			transaction.commit();
		} catch (Exception e) {
			System.out.println("Here is the Exception" + e);
			transaction.commit();
		} finally {
			session.close();
		}
		return otpObject;

	}

	public void permanentUpdatetoDb(ExternalUser permanentupdateprofile, String updatedItem) {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		if (permanentupdateprofile != null) {
			try {
				System.out.println("permanentUpdatetoDb" + permanentupdateprofile.getCust_id());
				Query query = session
						.createQuery("FROM " + ExternalUser.class.getName() + " ee where ee.cust_id = :t_custid");
				query.setParameter("t_custid", permanentupdateprofile.getCust_id());
				ExternalUser updateExternalUserDB = (ExternalUser) query.uniqueResult();
				System.out.println("Reached 1 " + updateExternalUserDB.getCust_id() + updateExternalUserDB.getF_name());
				if (updatedItem.equals("Mobile")) {
					updateExternalUserDB.setMobile(permanentupdateprofile.getMobile());
					updateExternalUserDB.setMobile_carrier(permanentupdateprofile.getMobile_carrier());
				} else if (updatedItem.equals("Email")) {
					System.out.println(permanentupdateprofile.getEmail() + " is the email");
					updateExternalUserDB.setEmail(permanentupdateprofile.getEmail());
				} else if (updatedItem.equals("Fname")) {
					System.out.println(permanentupdateprofile.getF_name() + " is the FirstName");
					updateExternalUserDB.setF_name(permanentupdateprofile.getF_name());
				} else if (updatedItem.equals("Lname")) {
					System.out.println(permanentupdateprofile.getL_name() + " is the LastName");
					updateExternalUserDB.setL_name(permanentupdateprofile.getL_name());
				} else if (updatedItem.equals("Address")) {
					System.out.println(permanentupdateprofile.getAddress() + " is the Address");
					updateExternalUserDB.setAddress(permanentupdateprofile.getAddress());
				} else if (updatedItem.equals("Password")) {
					updateExternalUserDB.setLogin_counter(0);
					System.out.println(permanentupdateprofile.getPassword() + " is the Password");
					updateExternalUserDB.setPassword(permanentupdateprofile.getPassword());
				}
				session.update(updateExternalUserDB);
				transaction.commit();
			} catch (Exception e) {
				System.out.println("Exception is " + e);
				e.printStackTrace();
				transaction.rollback();
			} finally {
				session.close();
			}
		}
	}

	public TempExternalUser fetchtempdetailsforOTPValidation(long cust_id, long id) {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();

		TempExternalUser tempdetails = null; // Is returning Null fine ?
		try {
			System.out.println("Reached fetchtempdetailsforOTPValidation");
			Query query = session.createQuery("from TempExternalUser where cust_id = :c and id = :i ");
			query.setParameter("i", id);
			query.setParameter("c", cust_id);
			tempdetails = (TempExternalUser) query.uniqueResult();
			
			String hql = "UPDATE "+ TempExternalUser.class.getName()+" set flag = 1 "  + 
		             "WHERE id = :otp_id and cust_id = :cust";
			Query query1 = session.createQuery(hql);
			query1.setParameter("cust", cust_id);
			query1.setParameter("otp_id", id);
			int result = query1.executeUpdate();
			transaction.commit();
		} catch (Exception e) {
			System.out.println("Here it is" + e);
			transaction.commit();
		} finally {
			session.close();
		}
		return tempdetails;
	}

	public TempTransaction temptransupdate(TempTransaction temptransaction) {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		TempTransaction temptrans = null;
		long id = 0;

		if (temptransaction != null) {
			try {
				id = (long) session.save(temptransaction);
				System.out.println(id);
				if (id != 0)
					transaction.commit();
				temptrans = (TempTransaction) session.get(TempTransaction.class, id);
				System.out.println("Hey TempTransaction");

			} catch (Exception e) {
				System.out.println("Hello:" + e);
				transaction.rollback();
			} finally {
				session.close();
			}
		}
		return temptrans;
	}

	public TempTransaction fetchtemptransdetailsforOTPValidation(long customerID, long otpID) {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();

		TempTransaction temptransdetails = null; // Is returning Null fine ?
		try {
			System.out.println("Reached fetchtemptransdetailsforOTPValidation");
			Query query = session.createQuery("from TempTransaction where cust_id = :c and id = :i ");
			query.setParameter("i", otpID);
			query.setParameter("c", customerID);
			temptransdetails = (TempTransaction) query.uniqueResult();
			transaction.commit();
		} catch (Exception e) {
			System.out.println("Here it is" + e);
			transaction.commit();
		} finally {
			session.close();
		}
		return temptransdetails;
	}

	public ExternalUser findUserById(long cust_id) {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();

		ExternalUser externaluser = null;
		try {
			externaluser = (ExternalUser) session.get(ExternalUser.class, cust_id);
			transaction.commit();
			session.close();

		} catch (Exception e) {
			transaction.rollback();
			session.close();
		}
		return externaluser;
	}

		public TempExternalUser findTupleById(long id) {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		List<org.asu.ss.model.TempExternalUser> trans_list = null;

		TempExternalUser temp = null;
		try {
				System.out.println("Reached mE"+id);
				Query query = session.createQuery("FROM " + TempExternalUser.class.getName() + " ee where ee.cust_id = :t_cust_id and ee.flag ="+"0");
				query.setParameter("t_cust_id", id);
				// List<TempExternalUser> employees= (List<TempExternalUser>)query.list();
				//temp_list = (List<TempExternalUser>) query.list();
				trans_list = query.list();
				
				System.out.println("List of Temporary users:   " + trans_list);
			transaction.commit();
			session.close();

		} catch (Exception e) {
			e.printStackTrace();
			transaction.commit();
			session.close();
		}
		if(trans_list.size()!= 0)
			temp = trans_list.get(0);
		System.out.println(temp+ "HEY YA");
		return temp;
	}
	
	public boolean updateAccountNo(long cust_id, long accNo) {
		ExternalUser extUser = findUserById(cust_id);
		if(extUser.getAcc_no1() == 0){
			extUser.setAcc_no1(accNo);
		}else if(extUser.getAcc_no2() == 0){
			extUser.setAcc_no2(accNo);
		}else if(extUser.getAcc_no3() == 0){
			extUser.setAcc_no3(accNo);
		}else if(extUser.getAcc_no4() == 0){
			extUser.setAcc_no4(accNo);
		}else if(extUser.getAcc_no5() == 0){
			extUser.setAcc_no5(accNo);
		}else{
			return false;
		}
		Session session = sessionFactory.openSession();
		Transaction sysTransaction = session.beginTransaction();
		try {
			session.update(extUser);
			sysTransaction.commit();
		} catch (Exception e) {
			sysTransaction.rollback();
			e.printStackTrace();
			return false;
		} finally {
			session.close();
		}
		return true;
	}

}
