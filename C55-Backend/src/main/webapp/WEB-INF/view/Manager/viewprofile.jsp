<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="home" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="en">

<head>

<c:url var="home" value="/" scope="request" />
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
<!-- jQuery -->
<script src="/C55-Backend/assets/js/jquery.js"></script>

<!-- Bootstrap Core JavaScript -->
<script src="/C55-Backend/assets/js/bootstrap.min.js"></script>

<script type="text/javascript">
	
function noBack() { window.history.forward(); }
window.onload=noBack();
function csrfsafe(xhr)
{
	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");
	xhr.setRequestHeader(header, token);
	}

	
	
	function getdetails() {
		$.ajax({
					type : "POST",
					dataType : "json",
					url : "${home}profile/view",
					contentType : "application/json; charset=utf-8",
					beforeSend: function(xhr) {
	    	            // here it is
	    	            csrfsafe(xhr);
	    				//	xhr.setRequestHeader(header, token);
	    	        },
	    			
					success : function(responsedata, status) {
						if (status == "success") {

							document.getElementById("username").innerHTML = responsedata.data['username'];
							document.getElementById("custid").innerHTML = responsedata.data['cust_id'];
							document.getElementById("firstname").innerHTML = responsedata.data['f_name'];
							document.getElementById("lastname").innerHTML = responsedata.data['l_name'];
							document.getElementById("ssn").innerHTML = responsedata.data['ssn'];
							document.getElementById("email").innerHTML = responsedata.data['email'];
							document.getElementById("mobile").innerHTML = responsedata.data['mobile'];
							document.getElementById("mobilecarrier").innerHTML = responsedata.data['mobile_carrier'];
							document.getElementById("address").innerHTML = responsedata.data['address'];
							document.getElementById("dob").innerHTML = responsedata.data['dob'];
							document.getElementById("orgname").innerHTML = responsedata.data['org_name'];
							document.getElementById("orgid").innerHTML = responsedata.data['org_id'];
						}
					},
					error : function(e) {
						console.log(e.message);
					}
				});
	}
</script>

</head>

<body>

	<div id="wrapper">

		<!-- Sidebar -->
		<div id="sidebar-wrapper">
			<ul class="sidebar-nav">
				<li class="sidebar-brand"></li>
				<li><a href="${home}accounts">Account Information</a></li>
				<li><a href="${home}transfercash">Transfer Cash</a></li>
				<li><a href="${home}balanceinfo">Balance Statement</a></li>
				<li><a href="${home}request">Requests</a></li>
				<li><a href="${home}viewprofile">View Profile</a></li>
				<li><a href="${home}editprofile">Edit Profile</a></li>
				<li><a href="${home}creditcard">Credit Card</a></li>
				<li><a href="${home}logout">Logout</a></li>

			</ul>
		</div>

		<div class="jumbotron">
		<form>
		<h1>Enter Customer Id:</h1><input type="number" pattern="[0-9]{12}" required>
			</form>
			<div class="container">
				<h1>Customer Profile</h1>
				<h2 id="customername"></h2>

				<div class=" col-md-12 col-lg-12 ">
					<table class="table table-user-information">
						<tbody>

							<tr>
								<th>Username</th>
								<td id="username"></td>
							</tr>

							<tr>
								<th>Customer ID</th>
								<td id="custid"></td>
							</tr>


							<tr>
								<th>First Name</th>
								<td id="firstname"></td>
							</tr>


							<tr>
								<th>Last Name</th>
								<td id="lastname"></td>
							</tr>


							<tr>
								<th>SSN</th>
								<td id="ssn"></td>
							</tr>


							<tr>
								<th>Email</th>
								<td id="email"></td>
							</tr>


							<tr>
								<th>Mobile Number</th>
								<td id="mobile"></td>
							</tr>


							<tr>
								<th>Mobile Carrier</th>
								<td id="mobilecarrier"></td>
							</tr>


							<tr>
								<th>Address</th>
								<td id="address"></td>
							</tr>

							<tr>
								<th>DOB</th>
								<td id="dob"></td>
							</tr>


							<tr>
								<th>Organisation Name</th>
								<td id="orgname"></td>
							</tr>


							<tr>
								<th>Organisation ID</th>
								<td id="orgid"></td>
							</tr>


						</tbody>
					</table>

				</div>

			</div>


		</div>
</body>
</html>