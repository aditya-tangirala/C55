<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="home" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="en">

<head>
<c:url var="home" value="/" scope="request" />
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, shrink-to-fit=no, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <meta name="_csrf" content="${_csrf.token}"/>
<meta name="_csrf_header" content="${_csrf.headerName}"/>
    

    <title>C55 Bank</title>
    <!-- Bootstrap Core CSS -->
 <link href="/C55-Backend/assets/css/bootstrap.min.css" rel="stylesheet">

	<!-- Custom CSS -->
 <link href="/C55-Backend/assets/css/simple-sidebar.css" rel="stylesheet">

   <!-- jQuery -->
        <script src="/C55-Backend/assets/js/jquery.js"></script>
        <script src="/C55-Backend/assets/js/validator.js"></script>

        <!-- Bootstrap Core JavaScript -->
        <script src="/C55-Backend/assets/js/bootstrap.min.js"></script>
        
        

    <!-- Bootstrap Core CSS -->
    <link href="/C55-Backend/assets/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="/C55-Backend/assets/css/simple-sidebar.css" rel="stylesheet">

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>-
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
        <![endif]-->
  <script type="text/javascript">

  function noBack() { window.history.forward(); }
  window.onload=noBack();
  function csrfsafe(xhr)
  {
  	var token = $("meta[name='_csrf']").attr("content");
  	var header = $("meta[name='_csrf_header']").attr("content");
  	xhr.setRequestHeader(header, token);
  	}

  
  
  
  //validation starts here
  
  function validAddAcc(){
		      var amount = document.getElementById("credit").value;
		      var custid = document.getElementById("custid").value;
		      if(amount == null ||  amount == ""){
		      	alert("fill correct value in amount field");
		      	return false;
		      }
		      if(custid == null ||  custid ==""){
		      	alert("put value in customer field");
		      	return false;
		      }
		      if(amount > 100000){
		      	alert(" amount value should be less than 100000");
		      	return false;
		      }
		
		  }
  function validCreateAcc(){
	  
      var fname = document.getElementById("f_name").value;
      var lname = document.getElementById("l_name").value;
      var uname = document.getElementById("user_name").value;
      var dob = document.getElementById("dob").value;
      var email = document.getElementById("email").value;
      var pass = document.getElementById("pass").value;
      var ssn = document.getElementById("ssn").value;
      var mobile = document.getElementById("mobile").value;
      var addr = document.getElementById("address").value;
      var amount = document.getElementById("new_credit").value;
      if(ssn.length != 9 ){
    	  alert("Please enter correct ssn number");
    	  return false;
      }
      if(mobile.length != 10){
    	  alert("Please enter correct mobile number");
    	  return false;
      }
     /*  if(pass.length > 8 && (pass.search("@") != -1 || pass.search("$")!= -1 || pass.search("%")!= -1 || pass.search("#")!= -1 )){
    	  alert("Please enter strong password with length greater than 8 char or special char ");
    	  return false;
      } */
      if(amount > 100000){
        	alert(" amount value should be less than 100000");
          	return false;
          }
      var mailPattern = /[a-z.A-Z0-9]+@[a-z]+\.[a-z]+$/;
      if(!mailPattern.test(email) ){
		  alert("Enter correct email address");
		  return false;
	  }
      
      /*if(fanme == null ||  fname == ""){
        	alert("fill correct value in first name field");
        	return false;
        }
      if(lname == null ||  lname == ""){
        	alert("fill correct value in last name field");
        	return false;
        }
      if(uname == null ||  uname == ""){
        	alert("fill correct value in username field");
        	return false;
        }
      if(dob == null ||  dob == ""){
        	alert("fill correct value in date of birth field");
        	return false;
        }
      if(email == null ||  email == ""){
        	alert("fill correct value in email field");
        	return false;
        }
      if(pass == null ||  pass == ""){
        	alert("fill correct value in password field");
        	return false;
        }
      if(ssn == null ||  ssn == ""){
        	alert("fill correct value in amount field");
        	return false;
        }
      if(amount == null ||  amount == ""){
        	alert("fill correct value in ssn field");
        	return false;
        }
      if(mobile == null ||  mobile ==""){
      	alert("put value in mobile field");
      	return false;
      }
      if(amount == null ||  amount == ""){
        	alert("fill correct value in amount field");
        	return false;
        }
      if(amount > 100000){
      	alert(" amount value should be less than 100000");
      	return false;
      }if(!document.getElementById("term").checked){
    	  alert("Please check the term and condition");
    	  return true;
      }*/
  }
  
  
  //validation ends here
  
  
  
		
function addnewaccount(){
	  
	  
	var res = validAddAcc();
	if(res == false){
		return false;
	}
	  
	var acc_type = document.getElementById("account_type");
	var acc_type_text = acc_type.options[acc_type.selectedIndex].text;
	var postdata = '{"cust_id":"'+document.getElementById("custid").value+'","type": "'+acc_type_text+'","acc_balance":"'+document.getElementById("credit").value+'","${_csrf.parameterName}":"${_csrf.token}"}';
          
	// alert(document.getElementById("new_phone").value)
        	//alert(postdata);

        	$.ajax({
            type: "POST",
            async: false,
            dataType: "json",
            url: "${home}account/create",  
            data: postdata,
            contentType: "application/json; charset=utf-8",
            beforeSend: function(xhr) {
	            // here it is
	            csrfsafe(xhr);
				//	xhr.setRequestHeader(header, token);
	        },
			
            success: function(data,status){
            	if(status=="success")
            		{
              console.log(data);
              alert("Account Created");
                //window.location.assign("otp.html");
            		}
            	else
            		alert(status);
            },
            error: function(jqXHR, exception){
                     var msg = '';
                if (jqXHR.status === 0) {
                    msg = 'Not connect.\n Verify Network.';
                } else if (jqXHR.status == 404) {
                    msg = 'Requested page not found. [404]';
                } else if (jqXHR.status == 500) {
                    msg = 'Internal Server Error [500].';
                } else if (exception === 'parsererror') {
                    msg = 'Requested JSON parse failed.';
                } else if (exception === 'timeout') {
                    msg = 'Time out error.';
                } else if (exception === 'abort') {
                    msg = 'Ajax request aborted.';
                } else {
                    msg = 'Uncaught Error.\n' + jqXHR.responseText;
                }

                alert(msg);

                }

        });
     return true;
	
}


function addNewUserAccount(custid){
			var acc_type = document.getElementById("new_account_type");
			var acc_type_text = acc_type.options[acc_type.selectedIndex].text;
			var postdata = '{"cust_id":"'+custid+'","type": "'+acc_type_text+'","acc_balance":"'+document.getElementById("new_credit").value+'","${_csrf.parameterName}":"${_csrf.token}"}';
            
			//lert(document.getElementById("new_phone").value)
          	//alert(postdata);

          	$.ajax({
              type: "POST",
              async: false,
              dataType: "json",
              url: "${home}account/create",  
              data: postdata,
              contentType: "application/json; charset=utf-8",
          	beforeSend: function(xhr) {
	            // here it is
	            csrfsafe(xhr);
				//	xhr.setRequestHeader(header, token);
	        },
              success: function(data,status){
            	if(status=="success")
        		{
          console.log(data);
          alert("Account has been created for "+ custid.toString());
            //window.location.assign("otp.html");
        		}
        	else
        		alert(status);
              },
              error: function(jqXHR, exception){
                       var msg = '';
                  if (jqXHR.status === 0) {
                      msg = 'Not connect.\n Verify Network.';
                  } else if (jqXHR.status == 404) {
                      msg = 'Requested page not found. [404]';
                  } else if (jqXHR.status == 500) {
                      msg = 'Internal Server Error [500].';
                  } else if (exception === 'parsererror') {
                      msg = 'Requested JSON parse failed.';
                  } else if (exception === 'timeout') {
                      msg = 'Time out error.';
                  } else if (exception === 'abort') {
                      msg = 'Ajax request aborted.';
                  } else {
                      msg = 'Uncaught Error.\n' + jqXHR.responseText;
                  }
			}
          });
	      return true;
			
		}		
		
function createUserAccount(){

	var res = validCreateAcc();
	if(res == false){
		return false;
	}
	
	
	var user_type = document.getElementById("user_type");
	var user_type_text = user_type.options[user_type.selectedIndex].text;
	var mob_carrier = document.getElementById("carrier");
	var mob_carrier_text = mob_carrier.options[mob_carrier.selectedIndex].text;
	var gender_type = document.getElementById("gender_type");
	var gender_type_text = gender_type.options[gender_type.selectedIndex].text;
	
			
			var postdata = '{  "username": "'+document.getElementById("user_name").value+'", \
					"password": "'+document.getElementById("pass").value+'", \
					"user_type": "'+document.getElementById("user_type").value+'",\
					"f_name": "'+document.getElementById("f_name").value+'",\
					"l_name": "'+document.getElementById("l_name").value+'",\
					"email": "'+document.getElementById("email").value+'",\
					"mobile_carrier": "'+mob_carrier_text+'",\
					"gender": "'+gender_type_text+'",\
					"address": "'+document.getElementById("address").value+'",\
					"ssn": "'+document.getElementById("ssn").value+'",\
					"mobile": "'+document.getElementById("mobile").value+'",\
					"dob": "'+document.getElementById("dob").value+'","${_csrf.parameterName}":"${_csrf.token}"}';
            
          	//alert(postdata);

          	$.ajax({
              type: "POST",
              async: false,
              dataType: "json",
              url: "${home}externaluser/create",  
              data: postdata,
              contentType: "application/json; charset=utf-8",
              beforeSend: function(xhr) {
    	            // here it is
    	            csrfsafe(xhr);
    				//	xhr.setRequestHeader(header, token);
    	        },
              success: function(data,status){
            	  if(status == "success"){
                		alert("Profile creation success! Your customer id is "+ data.data.cust_id + ".");
                        addNewUserAccount(data.data.cust_id);
            	  }
            	  else
            		  {
            		  alert(data.status);
            		  }
              },
              error: function(jqXHR, exception){
                       var msg = '';
                  if (jqXHR.status === 0) {
                      msg = 'Not connect.\n Verify Network.';
                  } else if (jqXHR.status == 404) {
                      msg = 'Requested page not found. [404]';
                  } else if (jqXHR.status == 500) {
                      msg = 'Internal Server Error [500].';
                  } else if (exception === 'parsererror') {
                      msg = 'Requested JSON parse failed.';
                  } else if (exception === 'timeout') {
                      msg = 'Time out error.';
                  } else if (exception === 'abort') {
                      msg = 'Ajax request aborted.';
                  } else {
                      msg = 'Uncaught Error.\n' + jqXHR.responseText;
                  }
              }

          });
	      return true;
			
		}


        </script>


    </head>

    <body>

                <div id="wrapper">

            <!-- Sidebar -->
            <div id="sidebar-wrapper">
            <ul class="sidebar-nav">
        <li>
          <a href="${home}register">Create Account</a>
        </li>
        <li>
          <a href="${home}transfercash">Transactions</a>
        </li>
        <li>
          <a href="${home}editprofile">Edit Profile</a>
        </li>
        <li>
          <a href="${home}deleteprofile">Delete Account</a>
        </li>
        <li>
          <a href="${home}request">Requests</a>
        </li>
        <li>
			<a href="${home}logout">Logout</a>
		</li>
            </ul>
        </div>
        
        
        
        
        <div class="container-fluid">
            
            
            <ul class="nav nav-tabs nav-justified" role="tablist">

                    <li><a data-toggle="tab" href="#createuser">Create User</a></li>
                    <li><a data-toggle="tab" href="#addaccount">Add Account</a></li>
                </ul>
        
            
            <div class="tab-content">
            <div id="createuser" class="tab-pane fade in active">
                        <div class="container">
                    
            
            
            
		<h2>Personal Bank Account Initial Application</h2>
		<form role="form" data-toggle="validator" class="form-horizontal" onsubmit="createUserAccount()">
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />

			<fieldset>
				<div class="form-group">
				<label for="User Type">User Type:</label>
					<select class="form-control" id="user_type" required>
           			  <option value="person" >Customer</option>
           			 </select>
				</div>
			</fieldset>

			<fieldset>
				<legend>Gender</legend>
				<div class="form-group">
				<select class="form-control" id="gender_type" required>
                      <option value="male" id="male" title="checkid">Male</option>
           			  <option value="female" id="female" title="savingid">Female</option>
                  </select>
				</div>

				<div class="form-group">
      				<div class="col-sm-6"><label>First name</label><input id="f_name"class="form-control" pattern="[A-Za-z]{1,15}" placeholder="First Name" type="text" required></div>
      				<div class="col-sm-6"><label>Last name</label><input id="l_name"class="form-control" pattern="[A-Za-z]{1,15}"  placeholder="Last Name" type="text" required></div>
    			
    			</div>
				
				<div class="form-group">
					<label>UserName</label>
					<input type="text" class="form-control" id="user_name" placeholder="Enter Username" pattern="[A-Za-z0-9]{0,13}" data-error="Only Alphanumeric characters allowed" required>
				</div>
				<div class="form-group">
					<label>Date of Birth</label>
					<input class="form-control" id="dob" placeholder="Date of Birth" type="date" required>
				</div>

				<div class="form-group">
					<label for="email">Email address:</label>
					<input type="email" class="form-control" id="email" placeholder="Enter email-id" data-error="That email address is invalid" required>
				</div>
				
				<div class="form-group">
					<label for="password">Password (Alphanumeric and Special Characters Allowed $@_.! )</label>
					<input type="password" class="form-control" id="pass" pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])[a-zA-Z0-9$@_.!]{8,20}"  placeholder="Password should be 8-20. One Caps and one small with special characters" required>
				</div>
				
				<div class="form-group">
					<label for="ssn">SSN (9 Digit US SSN)</label>
					<input type="number" class="form-control" pattern="[0-9]{9}" min ="100000000" max="999999999" id="ssn" required>
				</div>
				
				<div class="form-group">
					<label for="mobile">Mobile No: (10 Digit US Mobile Number)</label>
					<input type="number" class="form-control" pattern="[0-9]{10}" data-error="10 digit number only" id="mobile" required/>
				</div>
				
				<div class="form-group">
					<label for="carrier">Mobile Carrier:</label>
					 <select class="form-control" id="carrier" required>
                              <option value="checking" id="at&t">AT&T</option>
           					  <option value="saving" id="tm">T-Mobile</option>
           					  <option value="Sprint" id="sp">Sprint</option>
           					  	  <option value="Verizona" id="vz">Verizon</option>
           					  <option value="MTS" id="us">MTS</option>
           				
                           </select>
				</div>

				

			</fieldset>


			<fieldset>
	

				<div class="form-group">
					<label for="Apt name">Address:</label>
					<input type="text" class="form-control" id="address"  pattern="[a-zA-Z0-9.-\s]" required>
				</div>
				


			</fieldset>

			

			
			

		
			<fieldset>
			<div class="form-group">
           <label for="customerid">Credit Amount</label>
           <input type="number" class="form-control" id="new_credit" placeholder="Enter Amount" pattern="[0-9]" min="1" max="10000" required/>
            </div>
           <div class="form-group">
           <label for="sel1">Account Type:</label>
           <select class="form-control" id="new_account_type" required>
                              <option value="checking" title="checkid">Checking</option>
           <option value="saving" title="savingid">Savings</option>
                           </select>
            </div>
			</fieldset>
		

			<fieldset>
			<div class="form-group">
				<legend>Terms And Conditions</legend>
				<label><input type="checkbox" required> I/We confirm having read and understood the account rules of The C55  Corporation Limited ('the C55 Bank'), and hereby agree to be bound by the terms and conditions and amendments governing the account(s) issued by the Bank from time-to-time.</label>
			</div>
			</fieldset>


			<input class="btn btn-primary" value="Save Changes" type="submit" >
		</form>
                </div>
                
                </div>
                
                
                <div id="addaccount" class="tab-pane fade" onsubmit="addnewaccount()">
                        <div class="container-fluid">
                    
                            <form role="form" class="form-horizontal">
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                                
                                <div class="form-group">
					               <label for="sel1">Customer ID:</label>
					            	<input type="number" class="form-control" id="custid" pattern="[0-9]" required >   
                                </div>
                                
                                
                                
                                <div class="form-group">
                                <label for="customerid">Credit Amount</label>
                                <input type="number" class="form-control" id="credit" placeholder="Enter Amount" pattern="[0-9]" min="1" max="10000" required/>
                                 </div>
                                 
                                  
                                 
                                <div class="form-group">
					               <label for="sel1">Account Type:</label>
					               <select class="form-control" id="account_type" required>
                                       <option value="checking" title="checkid">Checking</option>
						              <option value="saving" title="savingid">Savings</option>
                                    </select>
                                </div>
                               <input class="btn btn-primary" value="Save Changes" type="submit" >
                            </form>
                                
                           
                    </div>
                </div>
                
                
            </div>
	</div>
<!-- /#wrapper -->



</body>

</html>