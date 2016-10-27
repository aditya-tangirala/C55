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
	<link href="/C55-Backend/assets/css/jquery-ui.css" rel="stylesheet">

     <!-- jQuery -->
        <script src="/C55-Backend/assets/js/jquery.js"></script>

        <!-- Bootstrap Core JavaScript -->
        <script src="/C55-Backend/assets/js/bootstrap.min.js"></script>
		<script src="/C55-Backend/assets/js/jquery-ui.js"></script>
		<script src="/C55-Backend/assets/js/jquery.dataTables.min.js"></script>
    	<script src="https://cdnjs.cloudflare.com/ajax/libs/jspdf/1.3.2/jspdf.debug.js"></script>
    	
    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
        <![endif]-->

<script>

function csrfsafe(xhr)
{
	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");
	xhr.setRequestHeader(header, token);
	}
	
			$(function () {
			
			  $('#cmd').click(function () {
			    var doc = new jsPDF();
			    doc.addHTML($('#demoTable')[0], 15, 15, {
			      'background': '#fff',
			    }, function() {
			      doc.save('Account Statement.pdf');
			    });
			  });
			});

         
			//
			
			function timeConverter(UNIX_timestamp){ 
				var a = new Date(UNIX_timestamp); 
				var months = ['Jan','Feb','Mar','Apr','May','Jun','Jul','Aug','Sep','Oct','Nov','Dec']; 
				var year = a.getFullYear();
				var month = months[a.getMonth()]; 
				var date = a.getDate(); var hour = a.getHours(); 
				var min = a.getMinutes(); 
				var sec = a.getSeconds();
				var time = date + ' ' + month + ' ' + year + ' ' + hour + ':' + min + ':' + sec ; 
				return time;
				}

			
			
			///
			
			
			                        
		function noBack() { window.history.forward(); }
			
          function getdetails()
          {
			noBack();
            var postdata = 
            {
          //    "cust_id":session()
				'${_csrf.parameterName}':"${_csrf.token}"
            }
            var dataString = JSON.stringify(postdata);
            $.ajax({
              type: "POST",
              dataType: "json",
              url: "${home}account/list",
              data: dataString,
              contentType: "application/json; charset=utf-8",
              beforeSend: function(xhr) {
  	            // here it is
  	            csrfsafe(xhr);
  	        },
              success: function(responsedata,status){
                if(status=="success"){
				console.log(responsedata);
                  for (var i =0 ; i< responsedata.length ; i++)
                        {
                     var option = document.createElement("option");
                     option.text = responsedata[i]["acc_no"];
                     option.value = responsedata[i]["acc_no"];
                     var select1 = document.getElementById("myaccounts");
                     select1.appendChild(option);
                    }
                 

              }},
              error: function(e){
                console.log(e.message);
              }
            });

          }

			
          //Validation function called from getstatement function 
          function validation(){
        	  var account = document.getElementById("myaccounts").value;
        	  if(account == "None"){
        		  alert("Please select the correct account number");
        		  return false;
        	  }
        	  var from = document.getElementById("from").value;
        	  var to = document.getElementById("to").value;
        	  
        	  
        	  var datePattern = /\d{4}-\d{2}-\d{2}/;
        	  if(!datePattern.test(from) || !datePattern.test(to)){
        		  alert("correctly fill the dates");
        		  return false;
        	  }
        	 /* var dateone = new Date(from);
        	  var datetwo = new Date(to);
        	  alert(dateone>datetwo);
        	  if(dateone>datetwo){
        		  alert("date selection is worng");
        		  return false;
        	  }*/
          }
          
    	function getStatement() {
    		var res = validation();
    		if (res == false){
				return false;
			}	
    		$('#demoTable1').empty();
    		
          var postdata = 
              {
                  "acc_no":document.getElementById("myaccounts").value,
                  "startDate":document.getElementById("from").value,
                  "endDate":document.getElementById("to").value,
				  '${_csrf.parameterName}':"${_csrf.token}"
              }
          var dataString = JSON.stringify(postdata);
          //alert(dataString);
      	$.ajax({
              type: "POST",
              async : false,
              dataType: "json",
              url: "${home}account/download",
              data: dataString,
              contentType: "application/json; charset=utf-8",
              beforeSend: function(xhr) {
  	            // here it is
  	            csrfsafe(xhr);
  	        }, 
              success: function(dataSet){
              	 if (dataSet.status == "Success") {


              		 $.each(dataSet.data, function(index, element) {
              			 
                       //    alert(element.t_id+" "+element.t_timestamp+" "+element.remarks+" "+element.t_amount+" "+element.from_acc+" "+element.to_acc); 
                           $row = '<div class="row" style="padding:10px">'+ 
                                     '<div class="col col-md-2">'+element.t_id+'</div>'+
                                      '<div class="col col-md-2">'+timeConverter(element.t_timestamp)+'</div>'+
                                      '<div class="col col-md-2">'+element.from_acc+'</div>'+
                                     '<div class="col col-md-2">'+element.to_acc+'</div>'+
                                     '<div class="col col-md-2">'+element.t_amount+'</div>'+
                                     '<div class="col col-md-2">'+element.remarks+'</div>'+
                                      '</div>'
                           $('#demoTable').append($row);
                       });
              		 
              		 
                      }
              
                  },
              
        
              error: function(e){
                  console.log(e.message);
              }
                  
              
          });
			
      	return true;

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
        <div id="banner">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-lg-12">
                      <div class="inner">
                         <div class="logo"><span class="icon fa-diamond"></span></div>
                         <h1>Account Statement</h1>

						<form role="form" class="form-horizontal" >
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                         <label for="self">From Account Number:</label>
                             <select class="form-control" id="myaccounts" required >
                                <option value="None"> </option>
                            </select>
							<br>
							&nbsp;
							<label for="from">Start Date: </label>
							<input type="date" id="from" name="from">
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<label for="to">End Date: </label>
							<input type="date" id="to" name="to">
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="button" onclick="getStatement()" class="btn btn-default" value="Get Statement"/>							<br>
                          </form>
                            
                      </div>
            			<div> <div class="col col-md-2">#</div>
                          <div class="col col-md-2">Date/Time</div>
                          <div class="col col-md-2">Transfered From</div>
                          <div class="col col-md-2">Transfered To</div>
                          <div class="col col-md-2">Amount</div>
                          <div class="col col-md-2">Remarks</div>
					</div>
                  	 <div id="demoTable">
                     
                      <div class="row">
                          
                          </div>
                  
                  </div>
                  
                  
                  
                  

          


</div>

<div>
 <input id="cmd" type="button" value="Download"/>
</div>
</div>
</div>
</div>
</div>
<!-- /#page-content-wrapper -->

<!-- /#wrapper -->

<!-- jQuery -->

<!-- Menu Toggle Script -->


</body>

</html>