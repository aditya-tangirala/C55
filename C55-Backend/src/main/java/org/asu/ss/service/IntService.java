package org.asu.ss.service;

import java.util.List;

import org.asu.ss.dao.IntDAO;
import org.asu.ss.model.PasswordReset;
import org.springframework.beans.factory.annotation.Autowired;

public class IntService {
	
	@Autowired
	private IntDAO intDAO;
	
	
	public void setIntDAO(IntDAO intDAO) {
		this.intDAO = intDAO;
	}

	public boolean pwdReset(PasswordReset pwdres) {
		// TODO Auto-generated method stub
		return intDAO.pwdReset(pwdres);
	}

	public List<PasswordReset> retrieveRequest() {
		return intDAO.retrieveRequest();
	}

	public boolean approveRequest(PasswordReset pwdres) {
		return intDAO.approveRequest(pwdres);
	}

	
}
