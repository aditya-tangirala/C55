<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
<c:url var="home" value="/" scope="request" />
<%-- <c:set var="contextPath" value="${pageContext.request.contextPath}"/> --%>

<meta name="_csrf" content="${_csrf.token}" />
<meta name="_csrf_header" content="${_csrf.headerName}" />
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, shrink-to-fit=no, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

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
 function csrfsafe(xhr)
 {
 	var token = $("meta[name='_csrf']").attr("content");
 	var header = $("meta[name='_csrf_header']").attr("content");
 	xhr.setRequestHeader(header, token);
 	}
 function getLogs()
 {
	 postdata=$('#logDate').val()
	 var dataString = JSON.stringify(postdata);
     $.ajax({
         type: "POST",
         dataType: "json",
         url: "${home}Admin/logs",
         data: dataString,
         contentType: "application/json; charset=utf-8",
         beforeSend: function(xhr) {
	            // here it is
	            csrfsafe(xhr);
	        },
         success: function(dataSet){
     		if(dataSet.status=="Success"){
     			
     		 var data=dataSet.data;
     		//alert(data);
        	 $('#logs').html(data);
             
         }
         },
         error: function(e){
             console.log(e.message);
         }
     });
 }
 window.onload=noBack();
 </script>
<body>

	<div id="wrapper">

		<!-- Sidebar -->
		<div id="sidebar-wrapper">
			<ul class="sidebar-nav">
				<li class="sidebar-brand"></li>


				<li><a href="${home}Admin/RoleAddition">Add New Employees</a></li>
				<li><a href="${home}Admin/ViewAllEmployees">View All
						Employees</a></li>
				<li><a href="${home}Admin/Requests">Employee Requests</a></li>
				<li><a href="${home}Admin/AccessLog">Access Logs</a></li>
				<li><a href="${home}Admin/logout">Logout</a></li>
			</ul>

		</div>

		<div class="container-fluid">
			<label>Log Date:</label> <input type="date" id="logDate"> <input
				class="btn btn-default" value="Get Logs" onclick="getLogs()"
				type="button">

		</div>
		<div>
			<code id="logs"></code>
		</div>
	</div>

</body>
</html>