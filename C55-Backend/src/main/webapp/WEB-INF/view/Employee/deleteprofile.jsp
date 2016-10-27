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
    <!-- Bootstrap Core JavaScript -->
    <script src="/C55-Backend/assets/js/bootstrap.min.js"></script>
    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
    <script type="text/javascript">
    
    
    function noBack() { window.history.forward(); }
    
    window.onload=noBack;
    function csrfsafe(xhr)
    {
    	var token = $("meta[name='_csrf']").attr("content");
    	var header = $("meta[name='_csrf_header']").attr("content");
    	xhr.setRequestHeader(header, token);
    	}

    
    //validation starts here
    function validDelAcc(){
        var account = document.getElementById("account_no").value;
        if(account == null ||  account ==""){
        	alert("put correct value in account field");
        	return false;
        }
    }
    
    
    //validation ends here
    
    function deleteAccount(){
    	var res = validDelAcc();
    	if(res == false){
    		return false;
    	}
    	
    	var postdata = '{"acc_no":"'+document.getElementById("account_no").value+'","${_csrf.parameterName}":"${_csrf.token}"};'
    	
    	//,"${_csrf.parameterName}":"${_csrf.token}"
    	//alert(postdata);
    	$.ajax({
            type: "POST",
            async: false,
            dataType: "json",
            url: "${home}account/delete",  
            data: postdata,
            contentType: "application/json; charset=utf-8",
            beforeSend: function(xhr) {
	            // here it is
	            csrfsafe(xhr);
				//	xhr.setRequestHeader(header, token);
	        },
			
            success: function(data,xhr){
              if(data.status == "Success")
            	alert("Success");
                //window.location.assign("otp.html");
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
        <form role="form" class="form-horizontal" onsubmit="deleteAccount()">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
          <div class="form-group">
            <h1>Delete Account</h1>
     		<br/>
            <label for="customerid">Customer Account:</label>
            <input type="number" pattern = "{0-9}{"  class="form-control" id="account_no" placeholder="Enter Account Number">
          </div>
          <input class="btn btn-primary" value="Delete Account" type="button" onclick="deleteAccount()">
        </form>
      </div>
  </body>
</html>