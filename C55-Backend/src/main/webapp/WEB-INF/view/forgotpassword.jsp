<!DOCTYPE html>
<html lang="en">

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<head>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, shrink-to-fit=no, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
<meta name="_csrf" content="${_csrf.token}"/>
<meta name="_csrf_header" content="${_csrf.headerName}"/>

<title>C55 Bank</title>

<!-- Bootstrap Core CSS -->
<link href="/C55-Backend/assets/css/bootstrap.min.css" rel="stylesheet">

<!-- Custom CSS -->
<link href="/C55-Backend/assets/css/simple-sidebar.css" rel="stylesheet">

<script src="/C55-Backend/assets/js/jquery.min.js"></script>
<link href="/C55-Backend/assets/css/keyboard.css" rel="stylesheet"></link>
<script src="/C55-Backend/assets/js/jquery.keyboard.js" ></script>
<script src="/C55-Backend/assets/js/jquery-ui.min.js" ></script>

<script>
$(function(){
$('#custid').keyboard();//bind keyboard to field
});
</script>


<script type="text/javascript">

function noBack() { window.history.forward(); }

function csrfsafe(xhr)
{
	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");
	xhr.setRequestHeader(header, token);
	}

    function forgetPassword()
{
    	var mode = document.getElementById("mode").value;
    	var cust_id = document.getElementById("custid").value;
    	alert(user +" " + mode +" " + cust_id +" ");
    	var postdata;
			if(mode=="Mobile")
			{
			postdata = '{  "item": "'+document.getElementById("mode").value+'",\
			"cust_id": "'+document.getElementById("custid").value+'"}';
			}
		else
			{
	    	postdata = '{  "item": "'+document.getElementById("mode").value+'",\
			"cust_id": "'+document.getElementById("custid").value+'"}';			
			}
          	$.ajax({
                type: "POST",
                async: false,
                dataType: "json",
                url: "${home}support/request",  
                data: postdata,
                contentType: "application/json; charset=utf-8",
                beforeSend: function(xhr) {
    	            // here it is
    	            csrfsafe(xhr);
    				//	xhr.setRequestHeader(header, token);
    	        },
                success: function(data,status){
                	console.log(data);
              	  if(status == "success"){
                  		alert("Request has been sent to Admin");
                  }
              	  else
              		{
              		alert(data);
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
			}

		//window.location = "/";


}
    window.onload=noBack;
</script>

</head>

<body>

	<div id="wrapper">
		<div>
		<h2>Forgot Password</h2>
		<form onsubmit="forgetPassword()">
		<span>Type of User: </span>
		<select id="actor">
			<option value="Customer" label="Customer" />
		</select>
		<br/>
		<span>Mode: </span>
		<select id="mode">
			<option value="Email" label="Email" />
			<option value="Mobile" label="Mobile"/>
		</select>
		<br/>
		<span>CustomerId/Email: (CustomerId if Customer/Merchant and Email if Employee) </span>
		<input id="custid" type="text" pattern="[a-zA-Z0-9]{1,15}" required>
		<button type="submit" value="Submit">Submit</button>
		</form>
		</div>
	</div>
</body>

</html>