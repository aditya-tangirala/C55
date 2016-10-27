
package org.asu.ss.controller;
import java.io.IOException;
//import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.asu.ss.model.InternalUser;
import org.asu.ss.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

@Controller
public class AdminHomeController {
	
	final static Logger log = Logger.getLogger(AccountController.class);
	
	@Autowired
	private AdminService adminService;

	public void setAdminService(AdminService adminService) {
		this.adminService = adminService;
	}

	@RequestMapping(value = "Admin/RoleAddition")
	public String roleAddition()
	{
		log.info("Entered AdminHomeController.roleAddition");
		return "Admin/RoleAddition";
	}
	@RequestMapping(value = "Admin/Requests")
	public String requests()
	{
		log.info("Entered AdminHomeController.roleAddition");
		return "Admin/Requests";
	}
	@RequestMapping(value = "Admin/ViewAllEmployees")
	public String viewAllEmployees() {

		log.info("Enter AdminHomeController.viewAllEmployees");
		return "Admin/ViewAllEmployees";
	}
//	@RequestMapping(value = "/Admin/ViewEmployeeDetails")
//	public ModelAndView viewEmployeeDetails() {
//		
//		System.out.println("In viewEmployeeDetails");
//		//System.out.println("Username ="+employee.getF_name());
//		System.out.println(adminService);
//		InternalUser intUser = new InternalUser();// getEmployeeGeneralInfo(employee);//adminService.getEmployeeGeneralInfo(employee);
//		intUser=getEmployeeGeneralInfo(intUser);
//		ModelAndView modView=new ModelAndView();
//		
//		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
//		String json = null;
//		try {
//			json = ow.writeValueAsString(intUser);
//		} catch (JsonProcessingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//       // String json = gson.toJson(intUser);
//        System.out.println(json);
//		modView.addObject("internalUser", json);
//		modView.setViewName("Admin/ViewModifyEmpl");
//		return modView;
//		
//	}
	@RequestMapping(value = "/Admin/piipage")
	public ModelAndView viewEmployeePIIDetails(HttpServletRequest request, @RequestParam("e_id")String e_id,
			@RequestParam("e_id")String f_name, @RequestParam("e_id")String l_name) {
		log.info("Enter AdminHomeController.viewEmployeePIIDetails with value "+f_name+" "+l_name+" "+e_id);
		long empId=Long.parseLong(e_id);
		InternalUser intUser = new InternalUser();
		intUser.setE_id(empId);
		intUser.setF_name(f_name);
		intUser.setL_name(l_name);
		intUser=adminService.getEmployeePIIInfo(intUser);
		ModelAndView modView=new ModelAndView();
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = null;
		try {
			json = ow.writeValueAsString(intUser);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			log.error("AdminController.viewEmployeePIIDetails error "+e.toString());
			e.printStackTrace();
		}
       // String json = gson.toJson(intUser);
        System.out.println(json);
		modView.addObject("internalUser", json);
		
		modView.setViewName("Admin/PII");
		log.info("AdminController.viewEmployeePIIDetails exit successfully");
		return modView;
	}
	@RequestMapping(value = "/Admin/GetGeneralEmployeeDetails")
	public ModelAndView viewEmployeeDetails(HttpServletRequest request, @RequestParam("e_id")String e_id,
			@RequestParam("e_id")String f_name, @RequestParam("e_id")String l_name) {
		log.info("Enter AdminHomeController.viewEmployeePIIDetails with value "+f_name+","+l_name+","+e_id);
		long empId=Long.parseLong(e_id);
		InternalUser intUser = new InternalUser();
		intUser.setE_id(empId);
		intUser.setF_name(f_name);
		intUser.setL_name(l_name);
		intUser=adminService.getEmployeeGeneralInfo(intUser);
		ModelAndView modView=new ModelAndView();
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = null;
		try {
			json = ow.writeValueAsString(intUser);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       // String json = gson.toJson(intUser);
        System.out.println(json);
		modView.addObject("internalUser", json);
		
		modView.setViewName("Admin/ViewModifyEmpl");
		log.info("Exit AdminHomeController.viewEmployeePIIDetails with value ");
		return modView;
	}
	
	
	@RequestMapping(value = "Admin/AccessLog")
	public String accessLogs() {
		log.info("Entering AdminHomeController.accessLogs");
		System.out.println("In accessLogs");
		return "Admin/AccessLog";
	}
	
	//mock code starts:
		private InternalUser getEmployeeGeneralInfo(InternalUser employee)
		{
			/*
			 * testing and mocking:start
			 */
			employee.setAccess_level("manager");
			employee.setE_id(1234);
			employee.setF_name("Pankaj");
			employee.setL_name("Singh");
			employee.setEmail("pankaj_singh@asu.edu");
			return employee;
			
		}
	
}