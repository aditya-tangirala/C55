package org.asu.ss.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

	@Controller
	public class ManagerController {

		@RequestMapping(value = "Manager/editprofile")
		public String testLink4(HttpServletRequest request) {
			System.out.println("In testLink3");
			HttpSession session = request.getSession(false);
			if (null != session && session.getAttribute("custId")!=null)
				return "Manager/editprofile";
			else
				return "redirect:/";
			
		}
		@RequestMapping(value = "Manager/register")
		public String testLink5(HttpServletRequest request) {

			System.out.println("In testLink3");
			HttpSession session = request.getSession(false);
			if (null != session && session.getAttribute("custId")!=null)
				return "Manager/register";
			else
				return "redirect:/";
		}
		@RequestMapping(value = "Manager/transfercash")
		public String testLink6(HttpServletRequest request) {
			System.out.println("In testLink3");
			HttpSession session = request.getSession(false);
			if (null != session && session.getAttribute("custId")!=null)
				return "Manager/transfercash";
			else
				return "redirect:/";
		}

		@RequestMapping(value = "Manager/deleteprofile")
		public String testLink7(HttpServletRequest request) {
			System.out.println("In testLink3");
			HttpSession session = request.getSession(false);
			if (null != session && session.getAttribute("custId")!=null)
				return "Manager/deleteprofile";
			else
				return "redirect:/";
		}
		@RequestMapping(value = "Manager/request")
		public String testLink8(HttpServletRequest request) {
			System.out.println("In testLink3");
			HttpSession session = request.getSession(false);
			if (null != session && session.getAttribute("custId")!=null)
				return "Manager/request";
			else
				return "redirect:/";
		}
	}
