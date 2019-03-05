<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="home" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="en">

<head>
<!-- <c:url var="home" value="/" scope="request" /> -->

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


	
        <script type="text/javascript">

        function csrfsafe(xhr)
        {
        	var token = $("meta[name='_csrf']").attr("content");
        	var header = $("meta[name='_csrf_header']").attr("content");
        	xhr.setRequestHeader(header, token);
        	}
        	
        function noBack() { window.history.forward();
        }
        	function getdetails()
        	{
				noBack();
        		$.ajax({
        			type: "POST",
        			dataType: "json",
        			url: "${home}account/list",
        			contentType: "application/json; charset=utf-8",
        			beforeSend: function(xhr) {
        	            // here it is
        	            csrfsafe(xhr);
        	        },
        			success: function(responsedata,status){
        				if(status=="success"){

        					for (var i =0 ; i< responsedata.length ; i++)
				              	{
					              var option = document.createElement("option");
					              option.text = responsedata[i]["acc_no"];
					              option.value = responsedata[i]["acc_no"];
					              var select1 = document.getElementById("from_t1");
					              select1.appendChild(option);
        						}
        					for (var i =0 ; i< responsedata.length ; i++)
			              	{
				              var option = document.createElement("option");
				              option.text = responsedata[i]["acc_no"];
				              option.value = responsedata[i]["acc_no"];
				              var select1 = document.getElementById("from_t2");
				              select1.appendChild(option);
    						}
        					for (var i =0 ; i< responsedata.length ; i++)
				              	{
					              var option = document.createElement("option");
					              option.text = responsedata[i]["acc_no"];
					              option.value = responsedata[i]["acc_no"];
					              var select1 = document.getElementById("to_t2");
					              select1.appendChild(option);
        						}	
        					for (var i =0 ; i< responsedata.length ; i++)
				              	{
					              var option = document.createElement("option");
					              option.text = responsedata[i]["acc_no"];
					              option.value = responsedata[i]["acc_no"];
					              var select1 = document.getElementById("from_t3");
					              select1.appendChild(option);
        						}
        					for (var i =0 ; i< responsedata.length ; i++)
				              	{
					              var option = document.createElement("option");
					              option.text = responsedata[i]["acc_no"];
					              option.value = responsedata[i]["acc_no"];
					              var select1 = document.getElementById("from_t7");
					              select1.appendChild(option);
	    						}
        						for (var i =0 ; i< responsedata.length ; i++)
				              	{
					              var option = document.createElement("option");
					              option.text = responsedata[i]["acc_no"];
					              option.value = responsedata[i]["acc_no"];
					              var select1 = document.getElementById("to_t1");
					              select1.appendChild(option);
        						}
        			}
        			},
        			error: function(e){
        				console.log(e.message);
        			}
        		});

        }

  // validation functionns being called from repective funcitons      	
        	
  		function validBtAcc(){
	        var amount = document.getElementById("amount_t1").value;
	        var remark = document.getElementById("remark_t1").value;
	        if(amount == null ||  amount == ""){
	        	alert("fill correct value in amount field");
	        	return false;
	        }
	        if(remark == null ||  remark ==""){
	        	alert("put value in remark field");
	        	return false;
	        }
	        if(amount > 100000){
	        	alert(" amount value should be less");
	        	return false;
	        }
	      /*  if(typeof(amount)!=number){
	        	alert(" amount should not be in number");
	        	return false;
	        }if(typeof(remark)!=string){
	        	alert(" remark should be in string");
	        	return false;
	        }
	        if(amount > 100000){
	        	alert(" amount value should be less");
	        	return false;
	        }*/
        }
        
        function validBtDifAcc(){
	        var amount = document.getElementById("amount_t2").value;
	        var remark = document.getElementById("remark_t2").value;
	        var account = document.getElementById("to_t2").value;
	        if(amount == null ||  amount == ""){
	        	alert("fill correct value in amount field");
	        	return false;
	        }
	        if(remark == null ||  remark ==""){
	        	alert("put value in remark field");
	        	return false;
	        }
	        if(account == null ||  account ==""){
	        	alert("put correct value in account field");
	        	return false;
	        }
	        if(amount > 100000){
	        	alert(" amount value should be less than 100000");
	        	return false;
	        }
        }
        
        function validQuickpay(){
        	var amount = document.getElementById("amount_t3").value;
	        var remark = document.getElementById("remark_t3").value;
	        var to_account = document.getElementById("to_t3").value;
	        if(amount == null ||  amount == ""){
	        	alert("fill correct value in amount field");
	        	return false;
	        }
	        if(remark == null ||  remark ==""){
	        	alert("put value in remark field");
	        	return false;
	        }
	        if(to_account == null ||  to_account ==""){
	        	alert("put correct calue in fro account field");
	        	return false;
	        }
	        if(amount > 100000){
	        	alert(" amount value should be less than 100000");
	        	return false;
	        }
        }

        function validDebCre(){
        	var amount = document.getElementById("dc_amount").value;
        	if(amount == null ||  amount == ""){
	        	alert("fill correct value in amount field");
	        	return false;
	        }
        	var radios = document.getElementsByName('makedebitcredit');
        	var item = null;
        	for (var i = 0, length = radios.length; i < length; i++) {
        		if (radios[i].checked) {
        			item = radios[i].value;      		
        		}
        	}
        	if (item == null){
        		alert("select correct type of transaction Debit/credit");
        		return false;
        	}
     		return true;
        	
        }
        //Validation function ends here
  	
  
        	
        	
        	
        	
        	
window.onload=getdetails;

function selfaccount()
{
	var result = validBtAcc();
	if(result == false){
		return false;
	}
	
	if(document.getElementById("from_t1").value == document.getElementById("to_t1").value)
	{
		alert("Can't transfer money between same account");
		return false;
	}


	var postdata = 
	{
		
		"from_acc":document.getElementById("from_t1").value,
		"to_acc":document.getElementById("to_t1").value,
		"t_amount":document.getElementById("amount_t1").value,
		"remarks":document.getElementById("remark_t1").value,
		'${_csrf.parameterName}':"${_csrf.token}"
	}
	var dataString = JSON.stringify(postdata);
	$.ajax({
		type: 'POST',
		async : false,
		dataType: "json",
		url: "${home}transfer/fund",
		data: dataString,
		contentType: "application/json; charset=utf-8",
		beforeSend: function(xhr) {
            // here it is
            csrfsafe(xhr);
        },
		success: function(responsedata,status){
			console.log(status);
			if(responsedata.status=="Success"){
			alert("Success");
			}else
				alert("Insufficient funds");
		},
		error: function(jqXHR, exception) {
		          alert("Failure");
		}
	});

}

function otheraccount()
{
	var val = validBtDifAcc();
	if(val == false)
		{
			return false;
		}
	

	var postdata = 
	{
		//cust_id: session;
		"from_acc":document.getElementById("from_t2").value,
		"to_acc":document.getElementById("to_t2").value,
		"t_amount":document.getElementById("amount_t2").value,
		"remarks":document.getElementById("remark_t2").value,
		'${_csrf.parameterName}':"${_csrf.token}"
	}
	var dataString = JSON.stringify(postdata);
	$.ajax({
		type: 'POST',
		async: false,
		dataType: "json",
		url: "${home}transfer/fund",
		data: dataString,
		contentType: "application/json; charset=utf-8",
		beforeSend: function(xhr) {
            // here it is
            csrfsafe(xhr);
        },
		success: function(responsedata,status){
			if(responsedata.status=="success"){
			alert("Success");
			}
			else
				alert("Insufficient funds");
		},
		error: function(e){
			alert("Failure");
		}
	});

}

function quickpay()
{
	

	var res = validQuickpay();
	if(res == false){
		return false;
	}

var postdata;
	if(document.getElementById("email").selected)
		{
		postdata = 
			{     //cust_id: session;
				"from_acc":document.getElementById("from_t3").value,
				"email": document.getElementById("to_t3").value,
				"t_amount":document.getElementById("amount_t3").value,
				"remarks":document.getElementById("remark_t3").value,
				'${_csrf.parameterName}':"${_csrf.token}"
			}
		}
	else
		{
		postdata = 
		{     //cust_id: session;
			"from_acc":document.getElementById("from_t3").value,
			"t_amount":document.getElementById("amount_t3").value,
			"remarks":document.getElementById("remark_t3").value,
			"mobile":document.getElementById("to_t3").value,
			'${_csrf.parameterName}':"${_csrf.token}"
		}
		}
	var dataString = JSON.stringify(postdata);
	$.ajax({
		type: "POST",
		async: false,
		dataType: "json",
		url: "${home}transfer/quick",
		data: dataString,
		contentType: "application/json; charset=utf-8",
		beforeSend: function(xhr) {
            // here it is
            csrfsafe(xhr);
        },
		success: function(data,status){
			if(data.status=="Success")
			alert("Request Has been made. You will be notified soon");
			else
			alert(status);
		},
		error: function(e){
			alert("Failure");
		}
	});

}

function make_dc()
{
	var res = validDebCre();
	if(res == false){
		return false;
	}
	
	var radios = document.getElementsByName('makedebitcredit');

	for (var i = 0, length = radios.length; i < length; i++) {
		if (radios[i].checked) {
				var item= radios[i].value;
				break;       		
		}
	}

if(item=="debit")
	{
	var postdata = 
		{
		"from_acc": document.getElementById("from_t7").value,
		"to_acc":"-1",
		"t_amount":document.getElementById("dc_amount").value,
		'${_csrf.parameterName}':"${_csrf.token}"
		}
var dataString = JSON.stringify(postdata);
    $.ajax({
    	type: "POST",
    	async: false,
    	dataType: "json",
    	url: "${home}account/debit",
    	data: dataString,
    	contentType: "application/json; charset=utf-8",
    	beforeSend: function(xhr) {
            // here it is
            csrfsafe(xhr);
        },
    	success: function(data,status){
    		if(data.status=="Success"){
    			alert("Request Has been made. You will be notified soon");
    		}
    		else
    			alert(data.error);
    	},
    	error: function(e){
    		alert(e.message);
    	}
    });

	}
else
	{
	var postdata = 
	{
	"from_acc":"-1",
	"to_acc": document.getElementById("from_t7").value,
	"t_amount":document.getElementById("dc_amount").value,
	'${_csrf.parameterName}':"${_csrf.token}"
	}

var dataString = JSON.stringify(postdata);
$.ajax({
	type: "POST",
	async: false,
	dataType: "json",
	url: "${home}account/credit",
	data: dataString,
	contentType: "application/json; charset=utf-8",
	beforeSend: function(xhr) {
        // here it is
        csrfsafe(xhr);
    },
	success: function(data,status){
		if(data.status=="success"){
			alert("Request Has been made. You will be notified soon");
		}
		else
			alert(data.error);
	},
	error: function(e){
		alert(e.message);
	}
});
	}
}

</script>


</head>

<body>

	<div id="wrapper">

		<!-- Sidebar -->
		<div id="sidebar-wrapper">
			<ul class="sidebar-nav">
    				<li class="sidebar-brand">
						C55 Bank
    				</li>
    				<li>
    					<a href="${home}accounts">Account Information</a>
    				</li>
    				<li>
    					<a href="${home}transfercash">Transfer Cash</a>
    				</li>
    				<li>
    					<a href="${home}balanceinfo">Balance Statement</a>
    				</li>
    				<li>
    					<a href="${home}request">Requests</a>
    				</li>
    				<li>
    					<a href="${home}viewprofile">View Profile</a>
    				</li>
    				<li>
    					<a href="${home}editprofile">Edit Profile</a>
    				</li>
    				<li>
    					<a href="${home}creditcard">Credit Card</a>
    				</li>
    				<li>
    					<a href="${home}logout">Logout</a>
    				</li>

			</ul>
		</div>
		<!-- /#sidebar-wrapper -->

		<!-- Page Content -->
		<div class="container-fluid">
			<ul class="nav nav-tabs nav-justified" role="tablist">

				<li><a data-toggle="tab" href="#self">Between your accounts</a></li>
				<li><a data-toggle="tab" href="#other">InterAccount Transfer </a></li>
				<li><a data-toggle="tab" href="#quickpay">Quick Pay</a></li>
				<li><a data-toggle="tab" href ="#dc">Debit/Credit</a></li>
			</ul>
			<div class="tab-content">
			
			
			
		<!-- ###################################### Phone no tab-->
		<div id="dc" class="tab-pane fade ">
			<div class="container">
				<h1>Credit/Debit Amount</h1>
				<hr>
				<div class="row">
					<!-- left column -->


					<!-- edit form column -->
					<div class="col-md-12 personal-info">


						<form class="form-horizontal" role="form" method="POST" onsubmit="make_dc()">
<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />

							<div class="form-group">
								<label class="col-md-3 control-label">Debit/Credit:</label>
								<div class="col-md-8">
									<div class="radio">
										<label><input type="radio" name="makedebitcredit" value="debit" >Debit</label>
									</div>
									<div class="radio">
										<label><input type="radio" name="makedebitcredit" value="credit" >Credit</label>
									</div>
								</div>
							</div>



							<div class="form-group">
								<label class="col-lg-3 control-label">Debit/Credit Number:</label>
								<div class="col-lg-8">
										<select class="form-control" id="from_t7" required>
											<option></option>

										</select>
								</div>
							</div>

							<div class="form-group">
								<label class="col-lg-3 control-label">Amount:</label>
								<div class="col-lg-8">
									<input class="form-control"  id="dc_amount" type="number" pattern="[0-9]" min="1" max="10000" required>
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
				
			
			
			
			
			
			
			
				<div id="quickpay" class="tab-pane fade ">
						
						<div  class="alert alert-info alert-dismissable">
							<a class="panel-close close" data-dismiss="alert">×</a> 
							<i class="fa fa-coffee"></i>
							<strong>Transaction Status</strong><div id="pt_status" ></div>
						</div>


					<h2>
						Transfer your money by Quick Pay
					</h2>



					<form role="form" class="form-horizontal" onsubmit="quickpay()">
<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />

						<div class="form-group">
							
							<div class="col-sm-6"><label for="self">Email/Mobile Number</label>
								<!-- <script>
									function chooseOption(){
										element_selected = document.getElementById("fromaccount").options[document.getElementById("fromaccount").selectedIndex].text;
										if(element_selected === 'Email') {
											parent = document.getElementById("fromaccount").parentNode;
											parent.innerHTML = "<label>Email</label><input class=\"form-control\"  type=\"email\" id=\"email\" required>";
											parent.nextElementSibling.innerHTML = "<label>Confirm Email</label><input class=\"form-control\"  type=\"email\" id=\"confirmemail\" required>";
										};
										if(element_selected === 'Phone Number') {
											parent = document.getElementById("fromaccount").parentNode;
											parent.innerHTML = "<label>Phone Number</label><input class=\"form-control\"  type=\"number\" id=\"number\" required>";
											parent.nextElementSibling.innerHTML = "<label>Confirm Phone Number</label><input class=\"form-control\"  type=\"number\" id=\"confirmphnumber\" required>";
										};
									}
								</script> onchange="chooseOption()"-->
								<select class="form-control" id="fromaccount"  required>
									<option value="email" id="email">Email</option>
									<option value="mobile" id="mobile">Mobile Number</option>
								</select></div> 

								<div class="col-sm-6"><label>Confirm Email/Mobile Number:</label><input class="form-control" id="to_t3"  required></div>
							</div>

							<div class="form-group">
								<div class="col-sm-6"><label for="self">Pay from Account Number:</label>
									<select class="form-control" id="from_t3" required>
										<option></option>
									</select></div>
									

								</div>


								<div class="form-group">
									<div class="col-sm-6"><label>Amount</label><input class="form-control" id="amount_t3"  type="number" pattern="[0-9]" min="1" max="10000" required></div>
									<div class="col-sm-6"><label>Remark</label><input class="form-control" id="remark_t3"  type="text"  required></div>
								</div>

								


								<button type="submit" class="btn btn-default">Pay</button>
							</form>
						</div>

							




						<div id="self" class="tab-pane fade in active">

							<div  class="alert alert-info alert-dismissable">
							<a class="panel-close close" data-dismiss="alert">×</a> 
							<i class="fa fa-coffee"></i>
							<strong>Transaction Status:</strong><div id="st_status" ></div>
						</div>



							<h2>
								Transfer your money within your own accounts
							</h2>

							<form role="form" class="form-horizontal" method="POST" onsubmit="selfaccount()" >
							<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
							
							<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" /> 
								<div class="form-group">
									<div class="col-sm-6">
										<label for="self">From Account Number:</label>
										<select class="form-control" id="from_t1" required>
											<option></option>

										</select>
									</div>



									<div class="col-sm-6">
										<label for="sel1">To Account Number:</label>
										<select class="form-control" id="to_t1" required>
											<option></option>

										</select>
									</div>
								</div>

								<div class="form-group">
									<div class="col-sm-6"><label>Amount</label><input class="form-control" id="amount_t1"  type="number" pattern="[0-9]" min="1" max="10000" required></div>

									<div class="col-sm-6"><label>Remark</label><input class="form-control" id="remark_t1" required></div>
									

								</div>


								<button type="submit" class="btn btn-default">Transfer</button>
								
							<button type="button" class="btn btn-default" onclick="openVKeyboard">Virtual Keyboard</button>
									
							

						</form>

						


					</div>




					<div id="other" class="tab-pane fade ">
						
						<div  class="alert alert-info alert-dismissable">
							<a class="panel-close close" data-dismiss="alert">×</a> 
							<i class="fa fa-coffee"></i>
							<strong>Transaction Status</strong><div id="qt_status" ></div>
						</div>


					<h2>
						Transfer your money to someone else account
					</h2>



					<form role="form" class="form-horizontal" onsubmit="otheraccount()">
<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />

						
							<div class="form-group">
								<div class="col-sm-6"><label for="self">Pay from Account Number:</label>
									<select class="form-control" id="from_t2" required>
										<option></option>
									</select></div>
									<div class="col-sm-6"><label>To Account Number:</label><input type="number" class="form-control" id="to_t2"  required></div>

								</div>


								<div class="form-group">
									<div class="col-sm-6"><label>Amount</label><input class="form-control" id="amount_t2"  type="number" pattern="[0-9]" min="1" max="100000" required></div>
									<div class="col-sm-6"><label>Remark</label><input class="form-control" id="remark_t2"  type="text"  required></div>

								</div>


								<button type="submit" class="btn btn-default">Pay</button>
							</form>
						</div>


				</div><!--end of tabbed content-->
			</div>

			<!-- /#page-content-wrapper -->

		</div>
		<!-- /#wrapper -->




	</body>

	</html>
