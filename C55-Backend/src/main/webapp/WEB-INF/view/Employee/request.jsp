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
<script src="/C55-Backend/assets/js/jquery.dataTables.min.js"></script>


<script type="text/javascript">

function noBack() { window.history.forward(); }

function csrfsafe(xhr)
{
	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");
	xhr.setRequestHeader(header, token);
	}



	//this requires ajax and hits Rest Controller
	function accept(r) {
		var urlAdd = "${home}account/approve/credit";
		callDataServer(r,urlAdd);

	}
	function decline(r) {
		var urlAdd = "${home}account/reject/credit";
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
		
		//,"${_csrf.parameterName}":"${_csrf.token}"
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
				//	xhr.setRequestHeader(header, token);
	        },
			
			success : function(dataSet) {
				if (dataSet.status == "Success") {
					//alert(dataSet.data);
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
		var dataSet2 = [];
		var t=0;
		for(var i=0;i<dataSet.length;i++) { 
			if(dataSet[i].t_status=="sc_initiated") { 
				dataSet2[t]=dataSet[i];
				t++;
			}
			}
		var table = [];
		console.log(dataSet2);
		for (var i = 0; i < dataSet2.length ; i++) {
			var row = [];
			//alert(dataSet[i].t_id);
			//$('#test').html(dataSet[i].t_id);
			row[0] = dataSet2[i].t_id;
			row[1] = dataSet2[i].from_acc;
			row[2] = dataSet2[i].to_acc;
			row[3] = dataSet2[i].t_timestamp;
			row[4] = dataSet2[i].t_amount;

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

		var postdata = '{"t_status": "sc_initiated&&sd_initiated","${_csrf.parameterName}":"${_csrf.token}"}';
		var dataString = JSON.stringify(postdata);

		//alert(postdata);

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
				//	xhr.setRequestHeader(header, token);
	        },
			
			success : function(dataSet, status) {
				if (dataSet.status == "Success") {
					allData = dataSet.data; //global data to store data for table creation and row selection
					//console.log(dataSet);
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


	function gettabdetails() {
		transactiontab();
	}

	
	
	
	
	function accept2(r) {
		var urlAdd = "${home}account/approve/debit";
		callDataServer2(r,urlAdd);

	}
	function decline2(r) {
		var urlAdd = "${home}account/reject/debit";
		callDataServer2(r,urlAdd);

	}
	function callDataServer2(r,urlAdd) {
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
				//	xhr.setRequestHeader(header, token);
	        },
			
			success : function(dataSet) {
				if (dataSet.status == "Success") {
					//alert(dataSet.data);
					//getAllEmployees();
					
					var table = $('#example2').DataTable();
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
	function addButton2(dataSet) {

		var gE1 = "<button onclick='accept2(";
		var gE2 = ")'>Accept </button>";
		var pi1 = "<button onclick='decline2(";
		var pi2 = ")'>Decline</button>";
		var dataSet2 = [];
		var t=0;
		for(var i=0;i<dataSet.length;i++) { 
			if(dataSet[i].t_status=="sd_initiated") { 
				dataSet2[t]=dataSet[i];
				t++;
			}
			}
		var table = [];
		console.log(dataSet2);
		for (var i = 0; i < dataSet2.length ; i++) {
			var row = [];
			//$('#test').html(dataSet[i].t_id);
			row[0] = dataSet2[i].t_id;
			row[1] = dataSet2[i].from_acc;
			row[2] = dataSet2[i].to_acc;
			row[3] = dataSet2[i].t_timestamp;
			row[4] = dataSet2[i].t_amount;

			row[5] = gE1 + i + gE2;
			row[6] = pi1 + i + pi2;

			table[i] = row;
		}
		return table;
	}
	function addData2(dataSet) {

		$(document).ready(function() {
			$('#example2').DataTable({
				data : dataSet,//getAllEmployees(),

				buttons : [ {
					text : 'Reload',
					action : function(e, dt, node, config) {
						dt.ajax.reload();
					}
				} ]
			});

			//code to select and highlight a particular row
			var table = $('#example2').DataTable();

			$('#example2 tbody').on('click', 'tr', function() {
				if ($(this).hasClass('selected')) {
					$(this).removeClass('selected');
				} else {
					table.$('tr.selected').removeClass('selected');
					$(this).addClass('selected');
				}
			});
		});
	}

	function transactiontab2() {

		var postdata = '{"t_status": "sc_initiated&&sd_initiated","${_csrf.parameterName}":"${_csrf.token}"}';
		var dataString = JSON.stringify(postdata);

		//alert(postdata);

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
				//	xhr.setRequestHeader(header, token);
	        },
			
			success : function(dataSet, status) {
				if (dataSet.status == "Success") {
					allData = dataSet.data; //global data to store data for table creation and row selection
					//console.log(dataSet);
					data = addButton2(allData);
					addData2(data);
					return allData;
				}
			},
			error : function(e) {
				//console.log(e.message);
			}
		});

	}


	function gettabdetails2() {
		transactiontab2();
	}
	
	function start(){
		noBack();
		gettabdetails();
		gettabdetails2();
	}
	
	window.onload = start;
</script>


</head>

<body>

	<div id="wrapper">

		<!-- Sidebar -->
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
		<!-- /#sidebar-wrapper -->

		<!-- Page Content -->
		<div class="container-fluid">
			<ul class="nav nav-tabs nav-justified" role="tablist">

				<li><a data-toggle="tab" href="#transaction">Credit Request</a></li>
				<li><a data-toggle="tab" href="#service">Debit Requests</a></li>

			</ul>
			<div class="tab-content">
				<div id="transaction" class="tab-pane fade in active">



					<h2>Credit Requests</h2>


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


				<div id="service" class="tab-pane fade">



					<h2>Debit Requests</h2>


					<div id="servicerequests">

						<table id="example2" class="display" cellspacing="0" width="100%">
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





				</div>




<label id="test"></label>


			</div>
			<!--end of tabbed content-->
		</div>

		<!-- /#page-content-wrapper -->

	<!-- /#wrapper -->



</body>

</html>