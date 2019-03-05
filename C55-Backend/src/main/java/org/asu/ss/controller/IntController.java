package org.asu.ss.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.asu.ss.model.BackendResponse;
import org.asu.ss.model.InternalUser;
import org.asu.ss.model.PasswordReset;
import org.asu.ss.service.IntService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IntController {
	
	@Autowired
	private IntService intService;

	final static Logger log= Logger.getLogger(IntController.class); 
	
	public void setIntService(IntService intService) {
		this.intService = intService;
	}
	
	@RequestMapping(value = "/intuser/pwdreset", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BackendResponse> addAccount(@RequestBody PasswordReset pwdres) {
		BackendResponse response = new BackendResponse();
		
		pwdres.setFlag(false);
		
		boolean status;
		status = intService.pwdReset(pwdres);
		if(!status){
			response.setStatus(BackendResponse.FAILURE);
			response.setError("Raise request failed !!");
			return new ResponseEntity<BackendResponse>(response, HttpStatus.OK);
		}
		response.setStatus(BackendResponse.SUCCESS);
		return new ResponseEntity<BackendResponse>(response, HttpStatus.OK);
	}
	@RequestMapping(value = "/admin/reqret", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BackendResponse> retrieveRequest() {
		BackendResponse response = new BackendResponse();

		log.info("Entered IntController:retrieveRequest");
		List<PasswordReset> reqlist;
		reqlist = intService.retrieveRequest();
		if(reqlist.isEmpty()){
			response.setStatus(BackendResponse.FAILURE);
			response.setError("Raise request failed !!");
			return new ResponseEntity<BackendResponse>(response, HttpStatus.OK);
		}
		response.setData(reqlist);
		System.out.println(reqlist);
		log.debug("Exit IntController:retrieveRequest");
		response.setStatus(BackendResponse.SUCCESS);
		return new ResponseEntity<BackendResponse>(response, HttpStatus.OK);
		
	}
	@RequestMapping(value = "/intuser/approveReject", method = RequestMethod.POST)
	public ResponseEntity<BackendResponse> approveReq(@RequestBody PasswordReset pwdres) {
		System.out.println("Inside approveReq");
		//log.info("Entered AdminController.deleteEmployee with value: "+pwdres.getE_id());
		BackendResponse response = new BackendResponse();
		//pwdres.setFlag(true);
		boolean status = intService.approveRequest(pwdres);
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
	
}
