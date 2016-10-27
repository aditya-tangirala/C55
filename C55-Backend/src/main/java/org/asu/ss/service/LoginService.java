package org.asu.ss.service;

import org.asu.ss.dao.LoginDAO;
import org.asu.ss.model.Admin;
import org.asu.ss.model.ExternalUser;
import org.asu.ss.model.InternalUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class LoginService {

	@Autowired
	private LoginDAO loginDAO;

	public void setLoginDAO(LoginDAO loginDAO) {
		this.loginDAO = loginDAO;
	}

	public ExternalUser validateExtUser(String username, String password, String user_type) {
		return loginDAO.getExtUser(username, password, user_type);
	}

	public InternalUser validateIntUser(String username, String password, String custType) {
		return loginDAO.getIntUser(username, password, custType);
	}

	public Admin validateAdmin(String username, String password) {
		return loginDAO.getAdmin(username, password);
	}

}
