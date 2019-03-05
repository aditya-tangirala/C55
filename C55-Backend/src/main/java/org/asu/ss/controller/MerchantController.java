package org.asu.ss.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MerchantController {

	final static Logger log = Logger.getLogger(MerchantController.class);
	
//	@RequestMapping(value = "Merchant/editprofile")
//	public String testLink4(HttpServletRequest request) {
//		log.info("Entering MerchantController.testLink4 ");
//		HttpSession session = request.getSession(false);
//		if (session.getAttribute("custId")!=null){
//			log.info("Exit MerchantController.testLink4 if");
//		return "Merchant/editprofile";
//		}
//		else{
//			log.info("Exit MerchantController.testLink4 else");
//		return "redirect:/";
//		}
//	}
//	@RequestMapping(value = "Merchant/register")
//	public String testLink5(HttpServletRequest request) {
//		log.info("Entering MerchantController.testLink5 ");
//		HttpSession session = request.getSession(false);
//		if (session.getAttribute("custId")!=null){
//			log.info("Exiting MerchantController.testLink5 if");
//		return "Merchant/register";}
//		else{
//			log.info("Exiting MerchantController.testLink5 else");
//		return "redirect:/";}
//	}
	
//	@RequestMapping(value = "Merchant/transfercash")
//	public String testLink6(HttpServletRequest request) {
//		log.info("Entering MerchantController.testLink6 ");
//		HttpSession session = request.getSession(false);
//		if (session.getAttribute("custId")!=null){
//			log.info("Exiting MerchantController.testLink6 if");
//		return "Merchant/transfercash";}
//		else{
//			log.info("Exiting MerchantController.testLink6 else");
//		return "redirect:/";}
//	}

//	@RequestMapping(value = "Merchant/deleteprofile")
//	public String testLink7(HttpServletRequest request) {
//		log.info("Entering MerchantController.testLink7 ");
//		HttpSession session = request.getSession(false);
//		if (session.getAttribute("custId")!=null){
//			log.info("Exiting MerchantController.testLink7 if");
//		return "Merchant/deleteprofile";}
//		else{
//			log.info("Exiting MerchantController.testLink7 else");
//		return "redirect:/";}
//	}
	
//	@RequestMapping(value = "Merchant/request")
//	public String testLink8(HttpServletRequest request) {
//		HttpSession session = request.getSession(false);
//		if (session.getAttribute("custId")!=null)
//			return "Merchant/request";
//		else
//		return "redirect:/";
//	}
	
	@RequestMapping(value = "Merchant/balanceinfo")
	public String balanceinfoMerchant(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (null != session && session.getAttribute("custId")!=null)
			return "Merchant/balanceinfo";
		else
		return "redirect:/";
	}
	@RequestMapping(value = "Merchant/accounts")
	public String accountsMerchant(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (null != session && session.getAttribute("custId")!=null)
			return "Merchant/accounts";
		else
		return "redirect:/";
	}
	@RequestMapping(value = "Merchant/editprofile")
	public String editprofileMerchant(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (null != session && session.getAttribute("custId")!=null)
			return "Merchant/editprofile";
		else
		return "redirect:/";
	}
	@RequestMapping(value = "Merchant/request")
	public String requestMerchant(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (null != session && session.getAttribute("custId")!=null)
			return "Merchant/request";
		else
		return "redirect:/";
	}
	@RequestMapping(value = "Merchant/viewprofile")
	public String viewprofileMerchant(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (null != session && session.getAttribute("custId")!=null)
			return "Merchant/viewprofile";
		else
		return "redirect:/";
	}
}
