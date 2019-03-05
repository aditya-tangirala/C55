package org.asu.ss.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.asu.ss.model.Account;
import org.asu.ss.model.BackendResponse;
import org.asu.ss.model.ExternalUser;
import org.asu.ss.model.TempExternalUser;
import org.asu.ss.model.TempExternalUserTrivial;
import org.asu.ss.model.TempTransaction;
import org.asu.ss.service.ExtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
public class ExtController {

	final static Logger log = Logger.getLogger(AccountController.class);

	@Autowired
	private ExtService extService;

	public void setExtService(ExtService extService) {
		this.extService = extService;
	}

	// To update the profile(otp verification)(Email and Phone numbers)//Add approve request by external customer (Completed)
	@RequestMapping(value = {
			"/profile/approveupdate" }, method = RequestMethod.POST, produces = MediaType.ALL_VALUE)
	public ResponseEntity<String> validateOTP(@RequestBody TempExternalUser tempexternaluser) {
		log.info("Enter ExtController.validateOTP with value: "+tempexternaluser.toString());
		String response;
		response = extService.validateOTP(tempexternaluser, tempexternaluser.getOtp_value());
		if (response.equals("Validaton Successful")) {
			log.info("Exit ExtController.validateOTP with value: success "+response.toString());
			return new ResponseEntity<String>(response, HttpStatus.OK);
		} else if (response.equals("Wrong OTP entered")) {
			log.info("Exit ExtController.validateOTP with value: Wrong OTP "+response.toString());
			return new ResponseEntity<String>(response, HttpStatus.EXPECTATION_FAILED);
		} else {
			log.info("Exit ExtController.validateOTP with value: OTP timeout "+response.toString());
			return new ResponseEntity<String>("OTP Value Expired - Request for new OTP", HttpStatus.NOT_MODIFIED);
		}
	}

		@RequestMapping(value = "Customer/profile/request", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BackendResponse> getRequests(Model model, HttpServletRequest request)
	{
		log.info("Entered ExtController.getRequests with value "+model.toString());
		System.out.println("Reached Backend");
		TempExternalUser tempexternaluser = new TempExternalUser();

		HttpSession session = request.getSession(false);
		// System.out.println(session.getId());
		long user_id = (long) session.getAttribute("custId");
		// System.out.println("HI bro!"+ user_id);
		tempexternaluser.setCust_id(user_id);
		BackendResponse backendResponse = new BackendResponse();
		TempExternalUser retTuple= extService.findTupleById(tempexternaluser.getCust_id());
		System.out.println("Reached ME 2"+retTuple);
		if (retTuple == null) {
			backendResponse.setStatus("Failure");
			backendResponse.setError("Requests not found!!");
			return new ResponseEntity<BackendResponse>(backendResponse, HttpStatus.NOT_FOUND);
		}
		backendResponse.setStatus("Success");
		backendResponse.setData(retTuple);
		System.out.println("Sent the response");
		return new ResponseEntity<BackendResponse>(backendResponse, HttpStatus.OK);
	}


	// To update the profile(OTP Generation)(Email and Phone numbers)(Regular employee calls this method)(Completed)
	@RequestMapping(value = {"/Employee/profile/initiateupdate","/Manager/profile/initiateupdate"}, method = RequestMethod.POST, produces = MediaType.ALL_VALUE)
	public ResponseEntity<BackendResponse> updateInfo(@RequestBody TempExternalUser tempexternaluser) {
		log.info("Enter ExtController.validateOTP with value: "+tempexternaluser.toString());
		String[] response = new String[2];
		BackendResponse backendResponse = new BackendResponse();
		response[0] = "Error - Profile Update cannot be completed.";
		response[1] = "Otp - Password Generation Failed";
		String responsetoUI = response[0] + "::" + response[1];
		try {

			response = extService.tempUpdateProfile(tempexternaluser, tempexternaluser.getItem());
			if (response != null) {
			responsetoUI = response[0] + "::" + response[1];
			backendResponse.setStatus("Success");
			backendResponse.setData(responsetoUI);
			System.out.println("Sent the response");
				log.info("Exit ExtController.validateOTP with value: "+responsetoUI);
				return new ResponseEntity<BackendResponse>(backendResponse, HttpStatus.OK);
			} else {
				log.info("Exit ExtController.validateOTP with value: "+responsetoUI);
				backendResponse.setStatus("Failure");
				backendResponse.setData(responsetoUI);
				return new ResponseEntity<BackendResponse>(backendResponse, HttpStatus.NOT_MODIFIED);
			}
		} catch (Exception e) {
			e.printStackTrace();
			backendResponse.setStatus("Failure");
			backendResponse.setData(responsetoUI);
			log.info("Exit ExtController.updateInfo: not modified");
			return new ResponseEntity<BackendResponse>(backendResponse, HttpStatus.NOT_MODIFIED);
		}

	}

	// To update the password(otp verification)(Email and Phone numbers)(Completed)(Design a password reset page)
	@RequestMapping(value = {
			"/profile/passwordotp" }, method = RequestMethod.POST, produces = MediaType.ALL_VALUE)
	public ResponseEntity<String> validateOTPforPasswordReset(@RequestBody TempExternalUser tempexternaluser) {
		log.info("Enter ExtController.validateOTPforPasswordReset with value: "+tempexternaluser);
		String response;
		response = extService.validateOTPForPasswordReset(tempexternaluser, tempexternaluser.getOtp_value());
		if (response.equals("Validaton Successful, go to PasswordReset Page")) {
			return new ResponseEntity<String>(response, HttpStatus.OK);
		} else if (response.equals("Wrong OTP entered, no PasswordReset Page")) {
			return new ResponseEntity<String>(response, HttpStatus.EXPECTATION_FAILED);
		} else {
			return new ResponseEntity<String>("OTP Value Expired - Request for new OTP", HttpStatus.NOT_MODIFIED);
		}
		//Write the service & controller for to update the password in the external table
	}

	// To update the password(OTP Generation)(Email and Phone numbers)(Completed)
		@RequestMapping(value = {"/support/request", "user/support/request"}, method = RequestMethod.POST, produces = MediaType.ALL_VALUE)
		public ResponseEntity<String> updateInfoforPasswordReset(@RequestBody TempExternalUser tempexternaluser) {
			String[] response = new String[2];
			response[0] = "Error - Profile Update cannot be completed.";
			response[1] = "Otp - Password Generation Failed";
			String responsetoUI = response[0] + "::" + response[1];
			try {
				ExternalUser extforpassword = new ExternalUser();
				extforpassword = extService.findUserById(tempexternaluser.getCust_id());
				tempexternaluser.setEmail(extforpassword.getEmail());
				tempexternaluser.setMobile(extforpassword.getMobile());
				tempexternaluser.setMobile_carrier(extforpassword.getMobile_carrier());
				response = extService.tempUpdateProfileforPasswordReset(tempexternaluser,tempexternaluser.getItem() );
				responsetoUI = response[0] + "::" + response[1];
				if (response != null) {
					return new ResponseEntity<String>(responsetoUI, HttpStatus.OK);
				} else {
					return new ResponseEntity<String>(responsetoUI, HttpStatus.NOT_MODIFIED);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return new ResponseEntity<String>(responsetoUI, HttpStatus.NOT_MODIFIED);
			}

		}

		/*@RequestMapping(value = { "/profile/declineupdate" }, method = RequestMethod.POST, produces = MediaType.ALL_VALUE)
		public ResponseEntity<String> declineServiceRequest(@RequestBody TempExternalUser tempexternaluser) {

			String decline;
			String decline1="Decline UNSuccessful";
			decline = extService.declineServiceRequest(tempexternaluser);
			if (decline.equals("Decline Successful")) {
				return new ResponseEntity<String>(decline, HttpStatus.OK);
			}
			return new ResponseEntity<String>(decline1, HttpStatus.OK);
		}
*/

	// To update the profileTrivial(Name and Address)(Completed)
	@RequestMapping(value = {"/profile/update", "/Customer/profile/update"}, method = RequestMethod.POST, produces = MediaType.ALL_VALUE)
	public ResponseEntity<String> updateInfoTrivial(@RequestBody TempExternalUserTrivial tempexternalusertrivial, Model model, HttpServletRequest request) {
		System.out.println("Reached Backend");
		String response = "Error - Profile Update cannot be completed.";

		System.out.println("AccountController.getAccountsList()");

		HttpSession session = request.getSession(false);
		//System.out.println(session.getId());
		List<Account> accounts = new ArrayList<Account>();

		long user_id = (long) session.getAttribute("custId");
		//System.out.println("HI bro!"+ user_id);
		tempexternalusertrivial.setCust_id(user_id);
		//System.out.println("The Values are :"+ tempexternalusertrivial.getAddress()+tempexternalusertrivial.getItem()+tempexternalusertrivial.getCust_id());
		//System.out.println("The Values are :"+ tempexternalusertrivial.getPassword()+tempexternalusertrivial.getItem()+tempexternalusertrivial.getCust_id());
		try {

			response = extService.permanentUpdateCustomerProfileTrivial(tempexternalusertrivial,
					tempexternalusertrivial.getItem());
			if (response != null) {
				System.out.println("Sent response from backend !!!!");
				return new ResponseEntity<String>(response, HttpStatus.OK);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<String>(response, HttpStatus.NOT_MODIFIED);
		}
		return new ResponseEntity<String>(response, HttpStatus.NOT_MODIFIED);
	}

	// To update the transactions(OTP Validation)(Completed)
	@RequestMapping(value = {
			"/externaluserControllers/verifytrans" }, method = RequestMethod.POST, produces = MediaType.ALL_VALUE)
	public ResponseEntity<String> validateOTPforTransactions(@RequestBody TempTransaction temptrans) {

		String response;
		response = extService.validateOTPforTransactions(temptrans, temptrans.getOtp_value());
		if (response.equals("Validaton Successful")) {
			return new ResponseEntity<String>(response, HttpStatus.OK);
		} else if (response.equals("Wrong OTP entered")) {
			return new ResponseEntity<String>(response, HttpStatus.EXPECTATION_FAILED);
		} else {
			return new ResponseEntity<String>("OTP Value Expired - Request for new OTP", HttpStatus.NOT_MODIFIED);
		}
	}

	// To update the transactions(OTP Generation)(Completed)
	@RequestMapping(value = "/externaluserControllerr/updatetrans", method = RequestMethod.POST, produces = MediaType.ALL_VALUE)
	public ResponseEntity<String> updateInfoTrans(@RequestBody TempTransaction temptrans) {
		String response = "Error - Profile Update cannot be completed.";
		try {

			response = extService.updateOTPTransactionsTable(temptrans, temptrans.getItem());
			if (response != null) {
				return new ResponseEntity<String>(response, HttpStatus.OK);
			} else {
				return new ResponseEntity<String>(response, HttpStatus.NOT_MODIFIED);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<String>(response, HttpStatus.NOT_MODIFIED);
		}

	}

	// To view external user profile
	  @RequestMapping(value = {"Customer/profile/view","Merchant/profile/view"}, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	    public ResponseEntity<BackendResponse> getUser(Model model, HttpServletRequest request) {
	        ExternalUser externaluser=new ExternalUser();
	        System.out.println("Entered getUser");
	        HttpSession session = request.getSession(false);
	        long user_id = (long) session.getAttribute("custId");
	        externaluser.setCust_id(user_id);
	        BackendResponse backendResponse = new BackendResponse();
	        ExternalUser retUser = extService.findUserById(externaluser.getCust_id());
	        if (retUser == null) {
	            backendResponse.setStatus("Failure");
	            backendResponse.setError("User not found!!");
	            return new ResponseEntity<BackendResponse>(backendResponse, HttpStatus.OK);
	        }
	        backendResponse.setStatus("Success");
	        backendResponse.setData(retUser);
	        //System.out.println(((ExternalUser)backendResponse.getData()).getF_name()+":"+((ExternalUser)backendResponse.getData()).getL_name()+":"+((ExternalUser)backendResponse.getData()).getAddress());
	        return new ResponseEntity<BackendResponse>(backendResponse, HttpStatus.OK);
	    }

	// To add new external user
	@RequestMapping(value ={"Employee/externaluser/create","Manager/externaluser/create"}, method = RequestMethod.POST)
	public ResponseEntity<BackendResponse> addUser(@RequestBody ExternalUser externalUser, UriComponentsBuilder ucb) {

		BackendResponse backendResponse = new BackendResponse();

		ExternalUser extUser = extService.createExtUser(externalUser);
		if (extUser == null) {
			backendResponse.setStatus("Failure");
			backendResponse.setError("Create External User Failed!");
			return new ResponseEntity<BackendResponse>(backendResponse, HttpStatus.OK);
		}
		backendResponse.setData(extUser);
		backendResponse.setStatus("Success");
		return new ResponseEntity<BackendResponse>(backendResponse, HttpStatus.OK);
	}


	// To delete external user
	@RequestMapping(value = "externaluser/delete", method = RequestMethod.POST)
	public ResponseEntity<BackendResponse> deleteUser(@RequestBody ExternalUser externaluser,
			UriComponentsBuilder ucb) {

		BackendResponse backendResponse = new BackendResponse();

		boolean status = extService.deleteExtUser(externaluser);
		if (status == false) {
			backendResponse.setStatus("Failure");
			backendResponse.setError("Delete External User Failed!");
			return new ResponseEntity<BackendResponse>(backendResponse, HttpStatus.OK);
		}
		backendResponse.setStatus("Success");
		return new ResponseEntity<BackendResponse>(backendResponse, HttpStatus.OK);
	}

}
