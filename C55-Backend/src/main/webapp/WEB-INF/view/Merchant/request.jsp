<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="home" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">

<head>
<style>
form {
	display: none;
}
</style>
<!-- <c:url var="home" value="/" scope="request" /> -->
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
function csrfsafe(xhr)
{
	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");
	xhr.setRequestHeader(header, token);
	}
function noBack() { window.history.forward();}
			 function getAllEmployees()
{
   //Ajax call
   //hitting rest controller
noBack();
$.ajax({
    type: "POST",
    //dataType: "json",
    url: "${home}/merchant/transreq",
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

    	var gE1="<button onclick='approve(";
    	var gE2=")'>View </button>";


    	var table=[];
    	for(var i=0;i<dataSet.length;i++)
    		{
    		var row=[];
    		row[0]=dataSet[i].t_custid;
    		row[1]=dataSet[i].t_amount;
    		row[2]=gE1+i+gE2;
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
	function approve(r)
	{

		if (confirm('Are you sure?')) {
			var postdata =
	        {
	        	"t_custid"	: allData[r].t_custid
	        }
        var dataString = JSON.stringify(postdata);
        $.ajax({
            type: "POST",
            dataType: "json",
            url: "${home}merchant/appreq", //url in AdminController(RestController)
            data: dataString,
            contentType: "application/json; charset=utf-8",
            beforeSend: function(xhr) {
	            // here it is
	            csrfsafe(xhr);
	        },
            success: function(data){
                alert('Transaction Approved successfully');
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

window.onload=getAllEmployees();
</script>
</head>
<body>

	<div id="wrapper">

		<!-- Sidebar -->
		<div id="sidebar-wrapper">
			<ul class="sidebar-nav">
				<li class="sidebar-brand">C55 Bank</li>
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
		<div class="container-fluid"></div>

		<div id="employees">
			<table id="example" class="display" cellspacing="0" width="100%">
				<thead>
					<tr>
						<th>Customer ID</th>
						<th>Amount</th>

						<th>Approve</th>

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
