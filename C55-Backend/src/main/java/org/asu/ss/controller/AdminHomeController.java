
package org.asu.ss.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.asu.ss.model.InternalUser;
import org.asu.ss.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

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
	public String roleAddition(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (null != session && session.getAttribute("custId") != null) {
			log.info("Entered AdminHomeController.roleAddition");
			return "Admin/RoleAddition";
		} else
			return "redirect:/";
	}

	@RequestMapping(value = "Admin/Requests")
	public String requests(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (null != session && session.getAttribute("custId") != null) {
			log.info("Entered AdminHomeController.roleAddition");
			return "Admin/Requests";
		} else
			return "redirect:/";
	}

	@RequestMapping(value = "Admin/ViewAllEmployees")
	public String viewAllEmployees(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (null != session && session.getAttribute("custId") != null) {
			log.info("Enter AdminHomeController.viewAllEmployees");
			return "Admin/ViewAllEmployees";
		} else
			return "redirect:/";
	}

	// @RequestMapping(value = "/Admin/ViewEmployeeDetails")
	// public ModelAndView viewEmployeeDetails() {
	//
	// System.out.println("In viewEmployeeDetails");
	// //System.out.println("Username ="+employee.getF_name());
	// System.out.println(adminService);
	// InternalUser intUser = new InternalUser();//
	// getEmployeeGeneralInfo(employee);//adminService.getEmployeeGeneralInfo(employee);
	// intUser=getEmployeeGeneralInfo(intUser);
	// ModelAndView modView=new ModelAndView();
	//
	// ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
	// String json = null;
	// try {
	// json = ow.writeValueAsString(intUser);
	// } catch (JsonProcessingException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// // String json = gson.toJson(intUser);
	// System.out.println(json);
	// modView.addObject("internalUser", json);
	// modView.setViewName("Admin/ViewModifyEmpl");
	// return modView;
	//
	// }
	@RequestMapping(value = "/Admin/piipage")
	public ModelAndView viewEmployeePIIDetails(HttpServletRequest request, @RequestParam("e_id") String e_id,
			@RequestParam("e_id") String f_name, @RequestParam("e_id") String l_name) {
		log.info("Enter AdminHomeController.viewEmployeePIIDetails with value " + f_name + " " + l_name + " " + e_id);
		HttpSession session = request.getSession(false);
		if (null != session && session.getAttribute("custId") != null) {
			long empId = Long.parseLong(e_id);
			InternalUser intUser = new InternalUser();
			intUser.setE_id(empId);
			intUser.setF_name(f_name);
			intUser.setL_name(l_name);
			intUser = adminService.getEmployeePIIInfo(intUser);
			ModelAndView modView = new ModelAndView();
			ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
			String json = null;
			try {
				json = ow.writeValueAsString(intUser);
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				log.error("AdminController.viewEmployeePIIDetails error " + e.toString());
				e.printStackTrace();
			}
			// String json = gson.toJson(intUser);
			//System.out.println(json);
			modView.addObject("internalUser", json);

			modView.setViewName("Admin/PII");
			log.info("AdminController.viewEmployeePIIDetails exit successfully");
			return modView;
		} else {
			ModelAndView mv = new ModelAndView("redirect:/");
			return mv;
		}
	}

	@RequestMapping(value = "/Admin/GetGeneralEmployeeDetails")
	public ModelAndView viewEmployeeDetails(HttpServletRequest request, @RequestParam("e_id") String e_id,
			@RequestParam("e_id") String f_name, @RequestParam("e_id") String l_name) {
		log.info("Enter AdminHomeController.viewEmployeePIIDetails with value " + f_name + "," + l_name + "," + e_id);
		HttpSession session = request.getSession(false);
		if (null != session && session.getAttribute("custId") != null) {
			long empId = Long.parseLong(e_id);
			InternalUser intUser = new InternalUser();
			intUser.setE_id(empId);
			intUser.setF_name(f_name);
			intUser.setL_name(l_name);
			intUser = adminService.getEmployeeGeneralInfo(intUser);
			ModelAndView modView = new ModelAndView();
			ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
			String json = null;
			try {
				json = ow.writeValueAsString(intUser);
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// String json = gson.toJson(intUser);
			// System.out.println(json);
			modView.addObject("internalUser", json);

			modView.setViewName("Admin/ViewModifyEmpl");
			log.info("Exit AdminHomeController.viewEmployeePIIDetails with value ");
			return modView;
		} else {
			ModelAndView mv = new ModelAndView("redirect:/");
			return mv;
		}
	}

	@RequestMapping(value = "Admin/AccessLog")
	public String accessLogs(HttpServletRequest request) {
		log.info("Entering AdminHomeController.accessLogs");
		HttpSession session = request.getSession(false);
		if (null != session && session.getAttribute("custId") != null) {
			// System.out.println("In accessLogs");
			return "Admin/AccessLog";
		} else
			return "redirect:/";
	}

	// mock code starts:
	private InternalUser getEmployeeGeneralInfo(InternalUser employee) {
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