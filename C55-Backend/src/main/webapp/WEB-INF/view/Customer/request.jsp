<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="home" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="en">

<head>
<!-- <c:url var="home" value="/" scope="request" /> -->

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
<script src="/C55-Backend/assets/js/jquery.dataTables.min.js"></script>


<script type="text/javascript">

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
	//this requires ajax and hits Rest Controller
	function csrfsafe(xhr)
{
	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");
	xhr.setRequestHeader(header, token);
	}
	function accept(r) {
		var urlAdd = "${home}transfer/approve/internal";
		callDataServer(r,urlAdd);

	}
	function decline(r) {
		var urlAdd = "${home}transfer/decline/internal";
		callDataServer(r,urlAdd);

	}
	function callDataServer(r,urlAdd) {
		var postdata = {

			"t_id" : allData[r].t_id,
			"from_acc" : allData[r].from_acc,
			"to_acc" : allData[r].to_acc,
			"t_amount" : allData[r].t_amount,
			'${_csrf.parameterName}':"${_csrf.token}"

		}
		var dataString = JSON.stringify(postdata);
		$.ajax({
			type : "POST",
			dataType : "json",
			url : urlAdd, //url in AdminController(RestController)
			data : dataString,
			contentType : "application/json; charset=utf-8",
			beforeSend: function(xhr) {
	            // here it is
	            csrfsafe(xhr);
	        },
			success : function(dataSet) {
				if (dataSet.status == "Success") {
					alert(dataSet.data);
					//getAllEmployees();
					var table = $('#example').DataTable();
					table.row('.selected').remove().draw(false);
				} else {
					alert(dataSet.error);
				}
			},
			error : function(e) {
				alert('Transaction Denied');
				//getAllEmployees();
			}
		});
	}

	//addition of buttons for table data and conversion of map(json) to array of array(2D array)
	function addButton(dataSet) {

		var gE1 = "<button onclick='accept(";
		var gE2 = ")'>Accept </button>";
		var pi1 = "<button onclick='decline(";
		var pi2 = ")'>Decline</button>";

		var table = [];
		for (var i = 0; i < dataSet.length; i++) {
			var row = [];
			//alert(dataSet[i].t_id);
			$('#test').html(dataSet[i].t_id);
			row[0] = dataSet[i].t_id;
			row[1] = dataSet[i].from_acc;
			row[2] = dataSet[i].to_acc;
			row[3] = timeConverter(dataSet[i].t_timestamp);
			row[4] = dataSet[i].t_amount;

			row[5] = gE1 + i + gE2;
			row[6] = pi1 + i + pi2;

			table[i] = row;
		}
		return table;
	}
	function addData(dataSet) {

		$(document).ready(function() {
			$('#example').DataTable({
				data : dataSet,//getAllEmployees(),

				buttons : [ {
					text : 'Reload',
					action : function(e, dt, node, config) {
						dt.ajax.reload();
					}
				} ]
			});

			//code to select and highlight a particular row
			var table = $('#example').DataTable();

			$('#example tbody').on('click', 'tr', function() {
				if ($(this).hasClass('selected')) {
					$(this).removeClass('selected');
				} else {
					table.$('tr.selected').removeClass('selected');
					$(this).addClass('selected');
				}
			});
		});
	}

	function transactiontab() {

		var postdata = '{"t_status": "initiated","${_csrf.parameterName}":"${_csrf.token}"}';
		//var dataString = JSON.stringify(postdata);


		$.ajax({
			type : "POST",
			async : false,
			dataType : "json",
			url : "${home}list/transactions",
			data : postdata,
			contentType : "application/json; charset=utf-8",
			beforeSend: function(xhr) {
	            // here it is
	            csrfsafe(xhr);
	        },
			success : function(dataSet, status) {
				if (dataSet.status == "Success") {
					allData = dataSet.data; //global data to store data for table creation and row selection
					//console.log(dataSet);
					//alert(allData);
					data = addButton(allData);
					addData(data);
					return allData;
				}
			},
			error : function(e) {
				//console.log(e.message);
			}
		});

	}

	function servicetab() {
		$
				.ajax({
					type : "POST",
					async : false,
					dataType : "json",
					url : "${home}profile/request",
					contentType : "application/json; charset=utf-8",
					beforeSend: function(xhr) {
	    	            // here it is
	    	            csrfsafe(xhr);
	    	        },
					success : function(responsedata, status) {
						if (status == "success") {
							//alert(responsedata.data);
							if (responsedata.data.email) {
								document.getElementById("changelabel").innerHTML = "New Email";
								document.getElementById("email_phone").value = responsedata.data.email;
							}

							if (responsedata.data.mobile) {
								document.getElementById("changelabel").innerHTML = "New Phone";
								document.getElementById("email_phone").value = responsedata.data.mobile;
							}

						}

					},
					error : function(e) {
						//console.log(e.message);
					}
				});

	}

	function noBack() { window.history.forward();}
	function gettabdetails() {
		noBack();
		transactiontab();
		servicetab();

	}

	window.onload = gettabdetails;
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

				<li><a data-toggle="tab" href="#transaction">Transaction
						Requests</a></li>
				<li><a data-toggle="tab" href="#service">Service Requests</a></li>

			</ul>
			<div class="tab-content">
				<div id="transaction" class="tab-pane fade in active">



					<h2>Transaction Requests</h2>


					<div id="transactionrequests">

						<table id="example" class="display" cellspacing="0" width="100%">
							<thead>
								<tr>
									<th>ID</th>
									<th>From</th>
									<th>To</th>
									<th>TimeStamp</th>
									<th>Amount</th>
									<th>Accept</th>
									<th>Decline</th>
								</tr>
							</thead>
							<!-- <tfoot>
            <tr>
									<th>ID</th>
									<th>From</th>
									<th>To</th>
									<th>TimeStamp</th>
									<th>Amount</th>
									<th>Accept</th>
									<th>Decline</th>
								</tr>
        </tfoot> -->
						</table>

					</div>



				</div>


				<div id="service" class="tab-pane fade ">

                    <h2>
                        Service  Requests
                    </h2>

                    <form:form class="form-horizontal" action="serviceOTP" modelAttribute="OTPDetails">


<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                        <div class="form-group">
                            <label  id ="changelabel" class="col-lg-3 control-label"></label>
                            <div class="col-lg-8">
                                <form:input path ="emailId" class="form-control"  id="email_phone" readonly="true" />
                            </div>
                        </div>



                        <div class="form-group">
                            <label class="col-md-3 control-label"></label>
                            <div class="col-md-8">
                                <form:input path ="status" class="btn btn-primary" value="Approve" type="submit"/>
                                <form:input path ="status" class="btn btn-primary" value="Decline" type="submit"/>
                                <span></span>    
                            </div>
                        </div>

                    </form:form>


				</div>



			</div>
			<!--end of tabbed content-->
		</div>

		<!-- /#page-content-wrapper -->

	</div>
	<!-- /#wrapper -->



</body>

</html>