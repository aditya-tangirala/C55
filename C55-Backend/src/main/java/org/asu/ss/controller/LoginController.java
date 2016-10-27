package org.asu.ss.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.asu.ss.model.Admin;
import org.asu.ss.model.ExternalUser;
import org.asu.ss.model.InternalUser;
import org.asu.ss.model.Login;
import org.asu.ss.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginController {

	@Autowired
	private LoginService loginService;

	public void setLoginService(LoginService loginService) {
		this.loginService = loginService;
	}
	@RequestMapping(value = {"Customer/logout","Employee/logout","Admin/logout","Merchant/logout","Manager/logout"})
	public String transferViewProfile(HttpServletRequest request) {
		return "redirect:/";
	}

/*	@RequestMapping(value = "Employee/logout")
	public String transferViewProfile2(HttpServletRequest request) {
		return "redirect:/";
	}
*/

	@RequestMapping(value = { "/", "/index", "user/login" })
	public String Welcome(Model model, HttpServletRequest request) {
		model.addAttribute("login", new Login());
		HttpSession session = request.getSession(false);
		SecurityContextHolder.clearContext();
		System.out.println("Welcome");
		if (session != null) {
			System.out.println(session.getId());
			session.invalidate();
		}
		return "index";
	}

	@RequestMapping(value = "Customer/home", method = RequestMethod.GET)
	public String custAccounts(HttpServletRequest request) {
		System.out.println("Accounts");
		HttpSession session = request.getSession(false);
		if (session.getAttribute("custId")!=null) {
			System.out.println("valid session");
			//HttpSession session = request.getSession(false);
			System.out.println(session.getId());
			// return (String) session.getAttribute("url");
			return "Customer/accounts";
		} else
			return "redirect:/";
	}

	@RequestMapping(value = "Merchant/home", method = RequestMethod.GET)
	public String merchantAccounts(HttpServletRequest request) {
		System.out.println("Accounts");
		HttpSession session = request.getSession(false);
		if (session.getAttribute("custId")!=null) {
			System.out.println("valid session");
			//HttpSession session = request.getSession(false);
			System.out.println(session.getId());
			// return (String) session.getAttribute("url");
			return "Merchant/accounts";
		} else
			return "redirect:/";
	}

	@RequestMapping(value = "Employee/home", method = RequestMethod.GET)
	public String EmpAccounts(HttpServletRequest request) {
		System.out.println("Employee");
		HttpSession session = request.getSession(false);
		if (session.getAttribute("custId")!=null) {
			System.out.println("valid session");
			//HttpSession session = request.getSession(false);
			System.out.println(session.getId());
			// return (String) session.getAttribute("url");
			return "Employee/register";
		} else
			return "redirect:/";
	}

	@RequestMapping(value = "Manager/home", method = RequestMethod.GET)
	public String ManagerAccounts(HttpServletRequest request) {
		System.out.println("Manager");
		HttpSession session = request.getSession(false);
		if (session.getAttribute("custId")!=null) {
			System.out.println("valid session");
			//HttpSession session = request.getSession(false);
			System.out.println(session.getId());
			// return (String) session.getAttribute("url");
			return "Manager/register";
		} else
			return "redirect:/";
	}

	@RequestMapping(value = "Admin/home", method = RequestMethod.GET)
	public String adminAccounts(HttpServletRequest request) {
		System.out.println("Admin");
		HttpSession session = request.getSession(false);
		if (session.getAttribute("custId")!=null) {
			System.out.println("valid session");
			//HttpSession session = request.getSession(false);
			System.out.println(session.getId());
			// return (String) session.getAttribute("url");
			return "redirect:/Admin/RoleAddition";
		} else
			return "redirect:/";
	}

	@RequestMapping(value = "user/login", method = RequestMethod.POST)
	public String validateLoginCredentials(@ModelAttribute("login") Login login, Model model,
			HttpServletRequest request) {
		try {

			String custType = login.getCustType();
			HttpSession session = request.getSession(false);
			
			if (request.isRequestedSessionIdValid() && !custType.equals(null)) {
				ExternalUser user = null;
				Admin admin = null;
				InternalUser iUser = null;
				if (custType.equals("Customer") || custType.equals("Merchant")) {
					user = loginService.validateExtUser(login.getUname(), login.getPass(), custType);
					if (user != null) {
						if (user.getLogin_counter() > 10) {
							// Let user know about exceeding the attempts
							return "redirect:/";
						} else {
							session.setAttribute("custId", user.getCust_id());
						}
					}
					// custType = "customer";
				} else if (custType.equals("Admin")) {
					admin = loginService.validateAdmin(login.getUname(), login.getPass());
					if(null!=admin)
					{
						session.setAttribute("custId", admin.getE_id());
					}
						// custType = "admin";
				} else if (custType.equals("Employee") || custType.equals("Manager")) {
					iUser = loginService.validateIntUser(login.getUname(), login.getPass(), custType);
					if(null!=iUser){
						session.setAttribute("custId", iUser.getE_id());
					}
					// custType = "employee";
				}
				if (user != null || admin != null || iUser != null) {
					System.out.println("redirect");
					System.out.println(session.getId());
					System.out.println("=====");
					session.setAttribute("custType", custType);
					session.setAttribute("url", custType + "/" + "home");
					String rValue = "redirect:/" + custType + "/" + "home";
					return rValue;
				} else {
					System.out.println("not found");
					return "redirect:/";
				}
			} else {
				return "redirect:/";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:/";
		}

	}

	@RequestMapping(value = "forgotpassword")
	public String validateLoginCredentials() {
		return "forgotpassword";
	}
}
