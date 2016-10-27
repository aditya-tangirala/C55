package org.asu.ss.dao;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.asu.ss.model.InternalUser;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AdminDAO {

	@Autowired
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}

	public boolean saveEmployeeRecord(InternalUser employee) {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();

		if(employee != null){
			try{
				session.save(employee);
				transaction.commit();
				return true;
			}catch(Exception e){
				transaction.rollback();
				session.close();
				return false;
			}
		}
		return false;
	}

	private InternalUser findEmployeeRecordById(long id ) {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();

		InternalUser user = null;
		try{
			user = (InternalUser) session.get(InternalUser.class, id);
			transaction.commit();
			session.close();

		}catch(Exception e){
			transaction.commit();
			session.close();
		}
		return user;
	}

	public boolean updateEmployeeRecord(InternalUser employee) {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		InternalUser empDb;

		if(employee != null){
			try{
				empDb=(InternalUser) session.get(InternalUser.class, employee.getE_id());
				empDb.setAccess_level(employee.getAccess_level());
				empDb.setF_name(employee.getF_name());
				empDb.setL_name(employee.getL_name());
				empDb.setPassword(employee.getPassword());
				session.update(empDb);
				transaction.commit();
				return true;
			}catch(Exception e){
				transaction.rollback();
				session.close();
				return false;
			}
		}
		return false;
	}
	public boolean updateEmployeePIIRecord(InternalUser employee) {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		InternalUser empDb;

		if(employee != null){
			try{
				empDb=(InternalUser) session.get(InternalUser.class, employee.getE_id());
				empDb.setEmail(employee.getEmail());
				empDb.setMobile(employee.getMobile());
				empDb.setSsn(employee.getSsn());
				session.update(empDb);
				transaction.commit();
				return true;
			}catch(Exception e){
				transaction.rollback();
				session.close();
				return false;
			}
		}
		return false;
	}

	public boolean deleteEmployeeRecord(InternalUser employee) {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		InternalUser internalUser = findEmployeeRecordById(employee.getE_id());
		if(internalUser != null){
			try{
				session.delete(internalUser);
				transaction.commit();
				return true;
			}catch(Exception e){
				transaction.rollback();
				session.close();
				return false;
			}
		}
		return false;
	}

	public InternalUser getEmployeeCompleteRecord(InternalUser employee) {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();

		InternalUser user = null;
		try{
			user = (InternalUser) session.get(InternalUser.class, employee.getE_id());
			transaction.commit();
			session.close();
		}catch(Exception e){
			transaction.commit();
			session.close();
		}
		return user;
	}

	public List<InternalUser> getAllEmployees() {
		Session session = sessionFactory.openSession();
		List<InternalUser> users = null;
		try{
			Query query = session.createQuery("from InternalUser");
			users = query.list();
			session.close();
		}catch(Exception e){
			session.close();
		}
		return users;
	}

	public InternalUser getEmployeeGeneralInfo(InternalUser employee) {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();

		InternalUser user = null;
		try{
			user = (InternalUser) session.get(InternalUser.class, employee.getE_id());
			transaction.commit();
			session.close();

			user.setEmail("");
			user.setMobile(0);
		}catch(Exception e){
			transaction.commit();
			session.close();
		}
		return user;
	}

	public String readLogs(String logDate) {
		System.out.println(logDate);
		String path=System.getProperty("catalina.home");
		System.out.println(path);
		String fileName=path+"/logs/C55Backend"+logDate+".log";

		String st = null;
		try {
			SecuritySS ss=new SecuritySS();
			ss.pki(path);
			st = readFile(fileName, Charset.defaultCharset());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//readFile(path, StandardCharsets.UTF_8);
	//	System.out.println(st);
		return st;
	}

	 String readFile(String path, Charset encoding)
			  throws IOException
			{
			  byte[] encoded = Files.readAllBytes(Paths.get(path));
			  return new String(encoded, encoding);
			}


}
