<!DOCTYPE html>
<html lang="en">

<head>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, shrink-to-fit=no, initial-scale=1">
  <meta name="description" content="">
  <meta name="author" content="">
	<meta name="_csrf" content="${_csrf.token}"/>
<meta name="_csrf_header" content="${_csrf.headerName}"/>s


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
  <script>
  function csrfsafe(xhr)
  {
  	var token = $("meta[name='_csrf']").attr("content");
  	var header = $("meta[name='_csrf_header']").attr("content");
  	xhr.setRequestHeader(header, token);
  	}
  function noBack() { window.history.forward();
  }
  window.onload=noBack;
  </script>
</head>
<body>


<div class="container be-detail-container">
    <div class="row">
        <div class="col-sm-6 col-sm-offset-3">
            <br>
            <img src="/C55-Backend/assets/images/otp.png" class="img-responsive" style="width:200px; height:200px;margin:0 auto;"><br>
            
            <h1 class="text-center">Verify your mobile number</h1><br>

            <div class="alert alert-info alert-dismissable">
                        <a class="panel-close close" data-dismiss="alert">×</a> 
                        <i class="fa fa-coffee"></i>
                        <strong>OTP Status:</strong><div id="otp_status"></div>
            </div>

            <p class="lead" style="align:center"></p><p> Thanks for giving your details. An OTP has been sent to your Mobile Number. Please enter the 6 digit OTP below </p>  <p></p>
        <br>
       
            <form:form method="POST" action="transfer/otp" modelAttribute="transferOtp">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
				<div class="row">                    
                <div class="form-group col-sm-8">
                     <span style="color:red;"></span>                    
                     <form:input type="text" path="otp_value" class="form-control" id="otp" placeholder="Enter your OTP number" required="required"/>
                </div>
                <input type="submit" value="Verify" class="btn btn-primary  pull-right col-sm-3">
                </div>
            </form:form>
        <br><br>
        </div>
    </div>        
</div>

</body>
</html>