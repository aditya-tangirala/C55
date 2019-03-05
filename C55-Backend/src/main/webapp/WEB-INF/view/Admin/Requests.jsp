<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">

<head>
<style>
form {
	display: none;
}
</style>
<c:url var="home" value="/" scope="request" />

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, shrink-to-fit=no, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
<meta name="_csrf" content="${_csrf.token}" />
<meta name="_csrf_header" content="${_csrf.headerName}" />

<title>C55 Bank</title>


<!-- Bootstrap Core CSS -->
<link href="/C55-Backend/assets/css/bootstrap.min.css" rel="stylesheet">
<link href="/C55-Backend/assets/css/jquery.dataTables.min.css"
	rel="stylesheet">

<!-- Custom CSS -->
<link href="/C55-Backend/assets/css/simple-sidebar.css" rel="stylesheet">
<!-- jQuery -->
<script src="/C55-Backend/assets/js/jquery.js"></script>

<!-- Bootstrap Core JavaScript -->
<script src="/C55-Backend/assets/js/bootstrap.min.js"></script>
<script src="/C55-Backend/assets/js/jquery.dataTables.min.js"></script>
<script type="text/javascript">
	function noBack() {
		window.history.forward();
	}
	function csrfsafe(xhr) {
		var token = $("meta[name='_csrf']").attr("content");
		var header = $("meta[name='_csrf_header']").attr("content");
		xhr.setRequestHeader(header, token);
	}
	function getAllEmployees() {
		//Ajax call 
		//hitting rest controller
		noBack();
		$.ajax({
			type : "POST",
			//dataType: "json",
    			url: "${home}admin/reqret",
			//data: 'name='+name+'&lastname='+ lastName,//{myData:dataString},
			//contentType: "application/json; charset=utf-8",
			async : false,
			beforeSend : function(xhr) {
				// here it is
				csrfsafe(xhr);
			},
			success : function(dataSet) {
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
				alert('Did Not found data');
			}
		});

	}
	//addition of buttons for table data and conversion of map(json) to array of array(2D array)
    function addButton(dataSet)
    {
    	
    	
    	var pi1="<button onclick='submit(";
    	var pi2=",true)'>accept</button>";
    	var pi3="<button onclick='submit(";
    	var pi4=	",false)'>reject</button>";
    	
	var table=[];
    	for(var i=0;i<dataSet.length;i++)
    		{
    		var row=[];
    		row[0]=dataSet[i][0];
    		row[1]=pi1+i+pi2;
    		row[2]=pi3+i+pi4;
    		table[i]=row;
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

	    //this requires ajax and hits Rest Controller
	function submit(r,flag)
	{
		
		if (confirm('Are you sure?')) {
			var postdata = 
	        {
	        	"e_id"	: allData[r][0],
	            "flag" : flag
	         };
			var dataString = JSON.stringify(postdata);
            //alert(dataString);
            $.ajax({
                type: "POST",
                async:false,
                dataType: "json",
                url: "${home}intuser/approveReject",
                data: dataString,
                beforeSend: function(xhr) {
                    // here it is
                    csrfsafe(xhr);
                },
                contentType: "application/json; charset=utf-8",
                success: function(data){
                    alert('transacion successfull');
                    var table = $('#example').DataTable();
                    table.row('.selected').remove().draw( false );
                },
                error: function(e){
                    console.log(e);
                }
            });
		}
	}

	window.onload = getAllEmployees();
</script>
</head>
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

	<div class="container-fluid"></div>

	<div id="employees">
		<table id="example" class="display" cellspacing="0" width="100%">
			<thead>
				<tr>
					<th>Employee ID</th>
						<th>Approve</th>
						<th>Reject</th>

				</tr>
			</thead>
			<!-- <tfoot>
            <tr>
                <th>Name</th>
                <th>Position</th>
                <th>Office</th>
                <th>Extn.</th>
               
            </tr>
        </tfoot> -->
		</table>
	</div>
	</div>



</body>
</html>
