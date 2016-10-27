<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">

<head>
<c:url var="home" value="/" scope="request" />
<meta name="_csrf" content="${_csrf.token}"/>
<meta name="_csrf_header" content="${_csrf.headerName}"/>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, shrink-to-fit=no, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<title>C55 Bank</title>


<!-- Bootstrap Core CSS -->
    <link href="/C55-Backend/assets/css/bootstrap.min.css" rel="stylesheet">
	<link href="/C55-Backend/assets/css/jquery.dataTables.min.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="/C55-Backend/assets/css/simple-sidebar.css" rel="stylesheet">
     <!-- jQuery -->
        <script src="/C55-Backend/assets/js/jquery.js"></script>

        <!-- Bootstrap Core JavaScript -->
        <script src="/C55-Backend/assets/js/bootstrap.min.js"></script>
        <script src="/C55-Backend/assets/js/jquery.dataTables.min.js"></script>


        <script type="text/javascript">
        function noBack() { window.history.forward(); }
        function csrfsafe(xhr)
        {
        	var token = $("meta[name='_csrf']").attr("content");
        	var header = $("meta[name='_csrf_header']").attr("content");
        	xhr.setRequestHeader(header, token);
        	}
        function loadPage()
        {

        	noBack();
        	userData=${internalUser};
        	setData(userData);
        }
          function setData(data)
          {
        	 // var data=formData;
        	  document.getElementById("e_id").innerHTML= data["e_id"];
              document.getElementById("firstname").innerHTML= data["f_name"];
             document.getElementById("lastname").innerHTML= data["l_name"];
             document.getElementById("email").value= data["email"];
             document.getElementById("phone").value= data["mobile"];
            document.getElementById("password").value= data["ssn"];


          }
function editprofile()
            {

                //alert(document.getElementById("fromself").value + " " + document.getElementById("toself").value + " " + document.getElementById("transferamount").value);
                //alert(data[][document.getElementById("fromself").value])
                //if(document.getElementById("transferamount").value > )

                var postdata =
                {
                		"e_id"	: document.getElementById("e_id").innerHTML,
                        "f_name":document.getElementById("firstname").innerHTML,
                        "l_name":document.getElementById("lastname").innerHTML,
                    "email":document.getElementById("email").value,
                    "mobile":document.getElementById("phone").value,
                    "ssn":document.getElementById("password").value
                }
                var dataString = JSON.stringify(postdata);
                $.ajax({
                    type: "POST",
                    dataType: "json",
                    url: "${home}employee/updatePII",
                    data: dataString,
                    beforeSend: function(xhr) {
        	            // here it is
        	            csrfsafe(xhr);
        	        },
                    contentType: "application/json; charset=utf-8",
                    success: function(data){
                        alert('transacion successfull');
                    },
                    error: function(e){
                        console.log(e.message);
                    }
                });

            }


window.onload=loadPage;
        </script>


      </head>
      <body>


<div id="wrapper">


        	<!-- Sidebar -->
		<div id="sidebar-wrapper">
			<ul class="sidebar-nav">
				<li class="sidebar-brand"></li>


				<li><a href="${home}Admin/RoleAddition">Add New Employees</a></li>
				<li><a  href="${home}Admin/ViewAllEmployees">View All Employees</a></li>
				<li><a  href="${home}Admin/Requests">Employee Requests</a></li>
				<li><a href="${home}Admin/AccessLog">Access Logs</a></li>
				<li><a href="${home}Admin/logout">Logout</a></li>
			</ul>

		</div>

        <div class="container-fluid">
         <!-- ###################################### PII-->
                    <div id="changeName" class="tab-pane fade in active">
                        <div class="container">
                          <h1>Edit Details</h1>
                          <hr>
                          <div class="row">
                            <!-- left column -->


                            <!-- edit form column -->
                            <div class="col-md-12 personal-info">
                              <div class="alert alert-info alert-dismissable">
                                <a class="panel-close close" data-dismiss="alert"></a>
                                <i class="fa fa-coffee"></i>

                              </div>

                              <form class="form-horizontal" role="form" action="" method="POST">

                                 <div class="form-group">
                                  <label class="col-lg-3 control-label">Employee ID:</label>
                                  <div class="col-lg-8">
                                    <label  id="e_id"> </label>
                                  </div>
                                </div>
                                <div class="form-group">
                                  <label class="col-lg-3 control-label">First name:</label>
                                  <div class="col-lg-8">
                                    <label  id="firstname" ></label>
                                  </div>
                                </div>

                                <div class="form-group">
                                  <label class="col-lg-3 control-label">Last name:</label>
                                  <div class="col-lg-8">
                                    <label  id="lastname"  ></label>
                                  </div>
                                </div>
                                <div class="form-group">
                                  <label class="col-lg-3 control-label">Phone:</label>
                                  <div class="col-lg-8">
                                    <input class="form-control" id="phone" type="number">
                                  </div>
                                </div>

                                <div class="form-group">
                                  <label class="col-lg-3 control-label">Email Id:</label>
                                  <div class="col-lg-8">
                                    <input class="form-control" id="email"  type="email">
                                  </div>
                                </div>

                                <div class="form-group">
                                  <label class="col-lg-3 control-label">SSN</label>
                                  <div class="col-lg-8">
                                    <input class="form-control" id="password"  type="text">
                                  </div>
                                </div>


                            <div class="form-group">
                              <label class="col-md-3 control-label"></label>
                              <div class="col-md-8">

                                <input class="btn btn-primary" value="Update Changes" onclick="editprofile()" type="button">
                                <span></span>

                                <input class="btn btn-default" value="Cancel" onclick="loadPage()" type="button">


                                    </div>
                                </div>

                                </form>

                            </div>
                        </div>
                    </div>
                </div>
<!-- ######################################-->

</div>
<div id="logs"></div>
</div>

</body>
</html>
     
