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


        <script type="text/javascript">

        function noBack() { window.history.forward(); }

        function csrfsafe(xhr)
        {
        	var token = $("meta[name='_csrf']").attr("content");
        	var header = $("meta[name='_csrf_header']").attr("content");
        	xhr.setRequestHeader(header, token);
        	}

        function getdetails()
    	{    noBack();
    	//var token = $("meta[name='_csrf']").attr("content");
    	//var header = $("meta[name='_csrf_header']").attr("content");
        	$.ajax({
    			type: "POST",
    			dataType: "json",
    			url: "${home}account/list",
    			contentType: "application/json; charset=utf-8",
    			beforeSend: function(xhr) {
    	            // here it is
    	            csrfsafe(xhr);
    				//	xhr.setRequestHeader(header, token);
    	        },
    			success: function(responsedata,status){
    				if(status=="success"){
    					var txt = "";
    					for (var i =0 ; i< responsedata.length;i++)
    					{
    						txt+="<div class='row'><div class='col-md-8'><h3>Account Number:</h3><h4>" + responsedata[i]["acc_no"].toString()+ "</h4></div><div class='col-md-4'><h3>Balance:</h3><h5>$" + responsedata[i]["acc_balance"].toString()+ "</h5></div></div>";
    					}
    					document.getElementById("accounts").innerHTML=txt;
    				}
    			},
    			error: function(e){
    				console.log(e.message);
    			}
    		});

    	}


        window.onload=getdetails;
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
							<a href="${home}logout">Logout</a>
						</li>

			</ul>
		</div>

		<div class="jumbotron">
			<div class="container">

				<h3> Accounts</h3>
				<div id="accounts">        </div>





			</div>





		</div>





	</body>

	</html>
