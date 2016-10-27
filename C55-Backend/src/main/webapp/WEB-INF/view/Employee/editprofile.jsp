<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="home" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <%-- <home:url var="home" value="/" scope="request" /> --%>
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
    <!-- Bootstrap Core JavaScript -->
    <script src="/C55-Backend/assets/js/bootstrap.min.js"></script>
    <!-- Bootstrap Core CSS -->
    <link href="/C55-Backend/assets/css/bootstrap.min.css" rel="stylesheet">
    <!-- Custom CSS -->
    <link href="/C55-Backend/assets/css/simple-sidebar.css" rel="stylesheet">
    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
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

    
    //validation code begins here
    function validEmail(){
		  var mail = document.getElementById("new_email").value;
		  var cust_id = document.getElementById("custid_t1").value;
		  var mailPattern = /[a-z.A-Z0-9]+@[a-z]+\.[a-z]+$/;
		  if(cust_id == null || cust_id ==""){
			  alert("Enter customer id field");
			  return false;
		  }
		  if(mail == null || mail ==""){
			  alert("Enter mail address to be updated");
			  return false;
		  }
		  if(!mailPattern.test(mail) ){
			  alert("Enter correct email address");
			  return false;
		  }
	  }
  
  function validPhone(){
		  var phone = document.getElementById("new_phone").value;
		  var cust_id = document.getElementById("custid_t2").value;
		  var carrier = document.getElementById("carrier").value;
		  var phonePattern = /[0-9]{10}/;
		    // /[0-9]+$/
		  if(cust_id == null || cust_id ==""){
			  alert("Enter the customer id in the field");
			  return false;
		  }
		  if(phone == null || phone ==""){
			  alert("Enter first phone number to be updated");
			  return false;
		  }
		  if(!phone.match(phonePattern)){
			  alert("Enter correct phone number");
			  return false;
		  }
		  if(!(document.getElementById("at").checked || document.getElementById("tm").checked
				  || document.getElementById("sp").checked || document.getElementById("vz").checked ||
				  document.getElementById("us").checked)){
			  alert("please select a carrier");
			  return false;
		  }
	  }
  
  //Validation ends here
  
    
    
    
      function changeemail()
                  {
    	  
			  var res = validEmail();
			  if(res == false){
				  return false;
			  }
                     
      
                      var postdata = '{"cust_id":" '+ document.getElementById("custid_t1").value + ' ","email":" '+document.getElementById("new_email").value+'","item":"Email","${_csrf.parameterName}":"${_csrf.token}"}';
                    //  '${_csrf.parameterName}':"${_csrf.token}"
            			// alert(document.getElementById("new_phone").value)
            	//var dataString = JSON.stringify(postdata);
            			
                      $.ajax({
                          type: "POST",
                          async: false,
                          dataType: "json",
                          url: "${home}profile/initiateupdate",  
                          data: postdata,
                          contentType: "application/json; charset=utf-8",
                          beforeSend: function(xhr) {
              	            // here it is
              	            csrfsafe(xhr);
              				//	xhr.setRequestHeader(header, token);
              	        },
              			
                          success: function(data,status){
                              console.log(data)
                        	  if(data.status=="success"){
                            alert("Your request has been raised");
                        	  }
                              else
                            	  alert(data.error)
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
      
      
      function changephoneno()
                  {
    	  	
    		var res = validPhone();
	  		if(res == false){
	  			return false;
	  		}
    	  
      //alert(document.getElementById("at").checked);
                      
      			// alert(document.getElementById("new_phone").value);
      			var postdata = "{}"; 
      			if(document.getElementById("at").checked)
                      postdata = '{"cust_id": " ' +document.getElementById("custid_t2").value+ ' ","mobile": "'+document.getElementById("new_phone").value+'","mobile_carrier":"AT&T","item" : "Mobile","${_csrf.parameterName}":"${_csrf.token}"}';
                if(document.getElementById("tm").checked)		
                      postdata = '{"cust_id": " '+document.getElementById("custid_t2").value+' ","mobile": "'+document.getElementById("new_phone").value+'","mobile_carrier":"T-Mobile","item" : "Mobile","${_csrf.parameterName}":"${_csrf.token}"}';
                if(document.getElementById("sp").checked)		
                      postdata = '{"cust_id": " '+document.getElementById("custid_t2").value+' ","mobile": "'+document.getElementById("new_phone").value+'","mobile_carrier":"Sprint","item" : "Mobile","${_csrf.parameterName}":"${_csrf.token}"}';
                if(document.getElementById("vz").checked)		
                      postdata = '{"cust_id": " '+document.getElementById("custid_t2").value+' ","mobile": "'+document.getElementById("new_phone").value+'","mobile_carrier":"Verizon","item" : "Mobile","${_csrf.parameterName}":"${_csrf.token}"}';
                if(document.getElementById("us").checked)		
                      postdata = '{"cust_id": " '+document.getElementById("custid_t2").value+' ","mobile": "'+document.getElementById("new_phone").value+'","mobile_carrier":"MTS","item" : "Mobile","${_csrf.parameterName}":"${_csrf.token}"}';
               // alert(postdata);
            	$.ajax({
                          type: "POST",
                          async: false,
                          dataType: "json",
                          url: "${home}profile/initiateupdate",  
                          data: postdata,
                          contentType: "application/json; charset=utf-8",
                          beforeSend: function(xhr) {
              	            // here it is
              	            csrfsafe(xhr);
              				//	xhr.setRequestHeader(header, token);
              	        },
              			
                          success: function(data, status){
             
                            if(data.status="success")
                        	  alert("Success");
                            else
                          	  alert(data.error)

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
                
      
              
    </script>
  </head>
  <body>
  <div id="wrapper">
    <!-- Sidebar -->
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
    <!-- /#sidebar-wrapper -->
    <!-- /#sidebar-wrapper -->
    <!--
      saurabh start
      -->
    <!-- Page Content -->
    <div class="container-fluid">
      <ul class="nav nav-tabs nav-justified" role="tablist">
        <li><a data-toggle="tab" href="#changeEmail">Change Email id</a></li>
        <li><a data-toggle="tab" href="#changePhone">Change Phone Number</a></li>
      </ul>
      <div class="tab-content">
        <!-- ###################################### email tab-->
        <div id="changeEmail" class="tab-pane fade in active ">
          <div class="container">
            <h1>Edit Email ID</h1>
            <hr>
            <div class="row">
              <!-- left column -->
              <!-- edit form column -->
              <div class="col-md-12 personal-info">
                <div class="alert alert-info alert-dismissable">
                  <a class="panel-close close" data-dismiss="alert">�</a> 
                  <i class="fa fa-coffee"></i>
                  <strong>Change Email Status:</strong>
                  <div id="c_email"></div>
                </div>
                <form class="form-horizontal" onsubmit="changeemail()">
                  <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                  <div class="form-group">
                    <label class="col-lg-3 control-label">Customer ID:</label>
                    <div class="col-lg-8">
                      <input class="form-control" type="number" pattern="[0-9]{10}" min="1000000000" max="9999999999999" id="custid_t1" required>
                    </div>
                  </div>
                  

                  <div class="form-group">
                    <label class="col-lg-3 control-label">New Email:</label>
                    <div class="col-lg-8">
                      <input class="form-control"   type="email" id="new_email" required >
                    </div>
                  </div>
                  <div class="form-group">
                    <label class="col-md-3 control-label"></label>
                    <div class="col-md-8">
                      <input class="btn btn-primary" value="Save Changes" type="submit">
                      <span></span>
                      <input class="btn btn-default" value="Cancel" type="reset">
                    </div>
                  </div>
                </form>
              </div>
            </div>
          </div>
        </div>
        <!-- ######################################-->
        <!-- ###################################### Phone no tab-->
        <div id="changePhone" class="tab-pane fade ">
          <div class="container">
            <h1>Edit Phone Number</h1>
            <hr>
            <div class="row">
              <!-- left column -->
              <!-- edit form column -->
              <div class="col-md-12 personal-info">
                <div class="alert alert-info alert-dismissable">
                  <a class="panel-close close" data-dismiss="alert">�</a> 
                  <i class="fa fa-coffee"></i>
                  <div id="c_phone"></div>
                  <strong>Change Phone Status:</strong>
                  <div id="c_phone"></div>
                </div>
                <form class="form-horizontal" onsubmit="changephoneno()">
        
          <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                    
                  <div class="form-group">
                    <label class="col-lg-3 control-label">Customer ID:</label>
                    <div class="col-lg-8">
                      <input class="form-control" type="number" pattern = "{0-9}{10}" min="100000000000" max="9999999999999" id="custid_t2" required >
                    </div>
                  </div>

                  <div class="form-group">
                    <label class="col-md-3 control-label">New Phone number:</label>
                    <div class="col-md-8">
                      <input class="form-control" id="new_phone" type="number" pattern = "{0-9}{10}" required>
                    </div>
                  </div>
                  <div class="form-group">
                    <label class="col-md-3 control-label">Mobile Carrier:</label>
                    <div class="col-md-8">
                      <div class="radio" id="carrier">
                        <label><input id="at" type="radio" value="at" name="carrier" selected>AT&T</label>
                      </div>
                      <div class="radio">
                        <label><input id ="tm" type="radio" value="tm" name="carrier">T-Mobile</label>
                      </div>
                      <div class="radio">
                        <label><input id ="sp" type="radio" value="sp" name="carrier">Sprint</label>
                      </div>
                      <div class="radio">
                        <label><input id ="vz" type="radio" value="vz" name="carrier">Verizon</label>
                      </div>
                      <div class="radio">
                        <label><input id ="us" type="radio" value="mts" name="carrier">MTS</label>
                      </div>
                    </div>
                  </div>
                  <div class="form-group">
                    <label class="col-md-3 control-label"></label>
                    <div class="col-md-8">
                      <input class="btn btn-primary" value="Save Changes" type="submit">
                      <span></span>
                      <input class="btn btn-default" value="Cancel" type="reset">
                    </div>
                  </div>
                </form>
              </div>
            </div>
          </div>
        </div>
        <!-- ######################################-->
      </div>
      <!--end of tabbed content-->
    </div>
    <!--
      saurabh end
      -->
    <!-- /#wrapper -->
    </div>
  </body>
</html>