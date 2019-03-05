package org.asu.ss.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.asu.ss.model.BackendResponse;
import org.asu.ss.model.InternalUser;
//import org.asu.ss.model.LogsModel;
import org.asu.ss.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.node.TextNode;

@RestController
public class AdminController {
	
	final static Logger log = Logger.getLogger(AccountController.class);

	@Autowired
	private AdminService adminService;

	public void setAdminService(AdminService adminService) {
		this.adminService = adminService;
	}

	// TODO : Handle validations
	@RequestMapping(value = "/employee/create", method = RequestMethod.POST)
	public ResponseEntity<BackendResponse> addEmployeeRecord(@RequestBody InternalUser employee) {
		log.info("Entered AdminController.addEmployeeRecord with value: "+employee.toString());
		BackendResponse response = new BackendResponse();
		boolean status = adminService.saveEmployeeRecord(employee);
		if(!status){
			System.out.println("AdminController.addEmployeeRecord() false");
			response.setStatus(BackendResponse.FAILURE);
			response.setError("Employee Creation failed !!");
			log.error("Exit AdminController.addEmployeeRecord failed: Employee not created "+response.toString());
			return new ResponseEntity<BackendResponse>(response, HttpStatus.OK);
		}
		System.out.println("AdminController.addEmployeeRecord() true");
		response.setStatus(BackendResponse.SUCCESS);
		log.info("Exit AdminController.addEmployeeRecord success: "+response.toString());
		return new ResponseEntity<BackendResponse>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/employee/delete", method = RequestMethod.POST)
	public ResponseEntity<BackendResponse> deleteEmployee(@RequestBody InternalUser employee) {
		log.info("Entered AdminController.deleteEmployee with value: "+employee.toString());
		BackendResponse response = new BackendResponse();
		boolean status = adminService.deleteEmployee(employee);
		if(!status){
			response.setStatus(BackendResponse.FAILURE);
			response.setError("Employee Deletion failed !!");
			log.error("Exit AdminController.deleteEmployee failed with value: "+response.toString());
			return new ResponseEntity<BackendResponse>(response, HttpStatus.OK);
		}
		response.setStatus(BackendResponse.SUCCESS);
		log.info("Exit AdminController.deleteEmployee succeded with value: "+response.toString());
		return new ResponseEntity<BackendResponse>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/employee/generalview", method = RequestMethod.POST)
	public ResponseEntity<BackendResponse> getEmployeeGeneralDetails(@RequestBody InternalUser employee) {
		log.info("Entered AdminController.getEmloyeeGeneralDetails with value: "+employee.toString());
		BackendResponse response = new BackendResponse();
		InternalUser intUser = adminService.getEmployeeGeneralInfo(employee);
		if(intUser == null){
			response.setStatus(BackendResponse.FAILURE);
			response.setError("Error in fetching Emp details !!");
			log.error("AdminController.getEmployeeGeneralDetails failed: no record fetched"+employee.toString());
			return new ResponseEntity<BackendResponse>(response, HttpStatus.OK);
		}
		response.setStatus(BackendResponse.SUCCESS);
		response.setData(intUser);
		log.info("AdminController.getEmployeeGeneralDetails successful with value: "+response.toString());
		return new ResponseEntity<BackendResponse>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/employee/piiview", method = RequestMethod.POST)
	public ResponseEntity<BackendResponse> getEmployeePIIDetails(@RequestBody InternalUser employee) {
		log.info("Entering AdminController.updateEmployeeRecord  with value: "+employee.toString());
		BackendResponse response = new BackendResponse();
		System.out.println("Updated Name"+employee.getEmail());
		InternalUser intUser = adminService.getEmployeePIIInfo(employee);
		if(intUser == null){
			response.setStatus(BackendResponse.FAILURE);
			response.setError("Error in fetching Emp details !!");
			log.error("Exit AdminController.getEmployeePIIDetails failed: no user details found "+response.toString());
			return new ResponseEntity<BackendResponse>(response, HttpStatus.OK);
		}
		response.setStatus(BackendResponse.SUCCESS);
		response.setData(intUser);
		log.info("Exit AdminController.getEmployeePIIDetails success "+response.toString());
		return new ResponseEntity<BackendResponse>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/system/users/view", method = RequestMethod.POST)
	public ResponseEntity<BackendResponse> getAllEmployees() {
		log.info("Entering AdminController.getAllEmployees");
		BackendResponse response = new BackendResponse();
		List<InternalUser> intUsers = adminService.getAllEmployees();
//		List<InternalUser> intUsers = getAllEmployees1();//adminService.getAllEmployees();
		if(intUsers == null){
			response.setStatus(BackendResponse.FAILURE);
			response.setError("Error in fetching Employees list !!");
			log.error("Exit AdminController.getAllEmployees failed");
			return new ResponseEntity<BackendResponse>(response, HttpStatus.OK);
		}
		response.setStatus(BackendResponse.SUCCESS);
		response.setData(intUsers);
		return new ResponseEntity<BackendResponse>(response, HttpStatus.OK);
	}

	// TODO : Handle validations
	@RequestMapping(value = "/employee/update", method = RequestMethod.POST)
	public ResponseEntity<BackendResponse> updateEmployeeRecord(@RequestBody InternalUser employee) {
		log.info("Entering AdminController.updateEmployeeRecord with values: "+employee.toString());
		BackendResponse response = new BackendResponse();
		//boolean status = true;//adminService.updateEmployeeRecord(employee);
		
		boolean status = adminService.updateEmployeeRecord(employee);
		if(!status){
			response.setStatus(BackendResponse.FAILURE);
			response.setError("Error updating Employee details !!");
			log.error("Exiting AdminController.updateEmployeeRecord failed with values "+response.toString());
			return new ResponseEntity<BackendResponse>(response, HttpStatus.OK);
		}
		response.setStatus(BackendResponse.SUCCESS);
		log.info("Exiting AdminController.updatEmployeeRecord successful with values: "+response.toString());
		return new ResponseEntity<BackendResponse>(response, HttpStatus.OK);
	}
	@RequestMapping(value = "/employee/updatePII", method = RequestMethod.POST)
	public ResponseEntity<BackendResponse> updateEmployeePIIRecord(@RequestBody InternalUser employee) {
		log.info("Entering AdminController.updateEmployeeRecord with value: "+employee.toString());
		BackendResponse response = new BackendResponse();
		System.out.println("Updated Name"+employee.getE_id());
		//boolean status = true;//adminService.updateEmployeeRecord(employee);
		
		boolean status = adminService.updateEmployeePIIRecord(employee);
		if(!status){
			response.setStatus(BackendResponse.FAILURE);
			response.setError("Error updating Employee details !!");
			log.error("AdminController.updateEmployeePIIRecord failed: Error updating employee details "+response.toString());
			return new ResponseEntity<BackendResponse>(response, HttpStatus.OK);
		}
		response.setStatus(BackendResponse.SUCCESS);
		return new ResponseEntity<BackendResponse>(response, HttpStatus.OK);
	}
	
	//Incomplete
	@RequestMapping(value = "/Admin/logs", method = RequestMethod.POST)
	public ResponseEntity<BackendResponse> accessLogs(@RequestBody TextNode logs) {
		log.info("Entering AdminController.accessLogs entered with value: "+logs.toString());
		BackendResponse response = new BackendResponse();
		String date=logs.asText();
		String logData = adminService.readLogs(date);
		if(logData==null){
			response.setStatus(BackendResponse.FAILURE);
			response.setError("Error updating Employee details !!");
			log.error("AdminController.accessLogs failed "+response.toString());
			return new ResponseEntity<BackendResponse>(response, HttpStatus.OK);
		}
		response.setData(logData);
		response.setStatus(BackendResponse.SUCCESS);
		log.info("AdminController.accessLogs successful with value "+response.toString());
		return new ResponseEntity<BackendResponse>(response, HttpStatus.OK);
	}
	//mock code starts:
	private List<InternalUser> getAllEmployees1()
	{
		/*
		 * testing and mocking:start
		 */
		InternalUser u1=new InternalUser();
		u1.setF_name("Pankaj");
		u1.setL_name("Singh");
		InternalUser u2=new InternalUser();
		u2.setF_name("Neeraj");
		u2.setL_name("Singh");
		List<InternalUser> intUsers=new ArrayList<InternalUser>();
		intUsers.add(u1);
		intUsers.add(u2);
		return intUsers;
	}
}
