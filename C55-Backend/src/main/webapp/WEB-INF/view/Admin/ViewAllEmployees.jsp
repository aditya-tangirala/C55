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
function noBack() { window.history.forward(); }
function csrfsafe(xhr)
{
	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");
	xhr.setRequestHeader(header, token);
	}

function getAllEmployees()
{
   //Ajax call 
   //hitting rest controller
noBack();
$.ajax({
    type: "POST",
    //dataType: "json",
    url: "${home}system/users/view",
    //data: 'name='+name+'&lastname='+ lastName,//{myData:dataString},
    //contentType: "application/json; charset=utf-8",
    async:false,
    beforeSend: function(xhr) {
        // here it is
        csrfsafe(xhr);
    },
    success: function(dataSet){
		if(dataSet.status=="Success"){
		 allData=dataSet.data; //global data to store data for table creation and row selection
        //console.log(dataSet);
        //alert(allData);
        data=addButton(allData);
        addData(data);
        return allData;
		}
    },
    error: function(e){
        //console.log(e.message);
        alert('Did Not found data');
    }
});

}
//addition of buttons for table data and conversion of map(json) to array of array(2D array)
    function addButton(dataSet)
    {
    	
    	var gE1="<button onclick='getEmployee(";
    	var gE2=")'>View </button>";
    	var pi1="<button onclick='getEmployeePII(";
    	var pi2=")'>View PII</button>";
    	var del1="<button onclick='deleteEmployee(";
    	var del2=")'>Remove</button>";
    	
    	var table=[];
    	for(var i=0;i<dataSet.length;i++)
    		{
    		var row=[];
    		row[0]=dataSet[i].e_id;
    		row[1]=dataSet[i].f_name;
    		row[2]=dataSet[i].l_name;
    		row[3]=gE1+i+gE2;
    		row[4]=pi1+i+pi2;
    		row[5]=del1+i+del2;
    		table[i]=row;
    		}
    	return table;
    }
    function addData(dataSet)
    {
    	 
    	$(document).ready(function() {
    	    $('#example').DataTable( {
    	        data: dataSet,//getAllEmployees(),
    	        
    	    buttons: [
    	              {
    	                  text: 'Reload',
    	                  action: function ( e, dt, node, config ) {
    	                      dt.ajax.reload();
    	                  }
    	              }
    	          ]
    	    } );
    	    
    	    //code to select and highlight a particular row
    	    var table = $('#example').DataTable();
    	    
    	    $('#example tbody').on( 'click', 'tr', function () {
    	        if ( $(this).hasClass('selected') ) {
    	            $(this).removeClass('selected');
    	        }
    	        else {
    	            table.$('tr.selected').removeClass('selected');
    	            $(this).addClass('selected');
    	        }
    	    } );
    	} );
    }
    
    //this requires ajax and hits Rest Controller
	function deleteEmployee(r)
	{
		
		if (confirm('Are you sure?')) {
			var postdata = 
	        {
	        	"e_id"	: allData[r].e_id,
	            "f_name":allData[r].f_name,
	            "l_name":allData[r].l_name,
	            
	           
	        }
        var dataString = JSON.stringify(postdata);
        $.ajax({
            type: "POST",
            dataType: "json",
            url: "${home}employee/delete", //url in AdminController(RestController)
            data: dataString,
            contentType: "application/json; charset=utf-8",
            beforeSend: function(xhr) {
	            // here it is
	            csrfsafe(xhr);
	        },
            success: function(data){
                alert('Employee record deleted successfully');
                //getAllEmployees();
                var table = $('#example').DataTable();
                table.row('.selected').remove().draw( false );
            },
            error: function(e){
                console.log(e.message);
            }
        });
		}
	}
    
    //fetches details of employee needs redirection to different page 
    function getEmployee(r)
    {
    	
    var urlAdd="${home}Admin/GetGeneralEmployeeDetails";// url in AdminHomeController
	submitData(r,urlAdd);
    }
    function getEmployeePII(r)
    {
    	
    var urlAdd="${home}Admin/piipage";
	submitData(r,urlAdd);
    }
    function submitData(r,urlAdd)
    {
    	//form submission achieved through hidden form myForm (look at the bottom of body )
    	$('#e_id').val(allData[r].e_id);
    	$('#f_name').val(allData[r].f_name);
    	$('#l_name').val(allData[r].l_name);
    	$("#myForm").attr("action",  urlAdd);
    	$('${_csrf.parameterName}').val('${_csrf.token}');
    	$("#myForm").submit();
		
    }
    
    
window.onload=getAllEmployees();
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
						<th>First Name</th>
						<th>Last Name</th>
						<th>Employee Info.</th>
						<th>PII.</th>
						<th>Remove Employee</th>
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

	<form id="myForm" action="#" method="post">
		<input type="text" id="f_name" name="f_name"> <input
			type="text" id="l_name" name="l_name"> <input type="text"
			id="e_id" name="e_id"> <input type="hidden"
			name="${_csrf.parameterName}" value="${_csrf.token}" />

	</form>

</body>
</html>