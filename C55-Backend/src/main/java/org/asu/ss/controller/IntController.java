package org.asu.ss.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.asu.ss.model.BackendResponse;
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
	public ResponseEntity<List<PasswordReset>> retrieveRequest() {
		log.info("Entered IntController:retrieveRequest");
		List<PasswordReset> reqlist;
		reqlist = intService.retrieveRequest();
		if(reqlist.isEmpty()){
			return new ResponseEntity<List<PasswordReset>>(HttpStatus.METHOD_FAILURE);
		}
		log.debug("Exit IntController:retrieveRequest");
		return new ResponseEntity<List<PasswordReset>>(reqlist,HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "/admin/approveresreq", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> approveRequest(@RequestBody PasswordReset pwdres) {
		
		boolean success = intService.approveRequest(pwdres);
		if(!success){
			return new ResponseEntity<Void>(HttpStatus.METHOD_FAILURE);
		}
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
}
