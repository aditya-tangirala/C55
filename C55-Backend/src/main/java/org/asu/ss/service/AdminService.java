package org.asu.ss.service;

import java.util.List;

import org.asu.ss.dao.AdminDAO;
import org.asu.ss.model.InternalUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class AdminService {

	@Autowired
	private AdminDAO adminDAO;
	
	public void setAdminDAO(AdminDAO adminDAO) {
		this.adminDAO = adminDAO;
	}

	public boolean deleteEmployee(InternalUser employee) {
		return this.adminDAO.deleteEmployeeRecord(employee);
	}

	public boolean updateEmployeeRecord(InternalUser employee) {
		return this.adminDAO.updateEmployeeRecord(employee);
		
	}
	public boolean updateEmployeePIIRecord(InternalUser employee) {
		return this.adminDAO.updateEmployeePIIRecord(employee);
		
	}
	
	public boolean saveEmployeeRecord(InternalUser employee) {
		return this.adminDAO.saveEmployeeRecord(employee);
		
	}

	public InternalUser getEmployeeGeneralInfo(InternalUser employee) {
		return this.adminDAO.getEmployeeGeneralInfo(employee);
	}

	public InternalUser getEmployeePIIInfo(InternalUser employee) {
		return this.adminDAO.getEmployeeCompleteRecord(employee);
	}

	public List<InternalUser> getAllEmployees() {
		return this.adminDAO.getAllEmployees();
	}
	
	public String readLogs(String logs)
	{
		
		return this.adminDAO.readLogs( logs);
	}

}
