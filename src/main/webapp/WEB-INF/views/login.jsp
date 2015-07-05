<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	language="java"%>
<html>
<head>
<title>Home</title>
<link href="<c:url  value="/resources/css/home.css" />" rel="stylesheet" />

<script type="text/javascript" src="<c:url  value="/resources/js/jquery-2.1.4.min.js" />"></script> 
<script type="text/javascript" src="<c:url  value="/resources/js/jquery.tablesorter.min.js" />"></script> 

</head>
<body>
	<div class="main">

		<h1>HOME PAGE - Главная страница</h1>



		<div class="login-form-container">

			<form:form class="login-form" method="POST" commandName="user" action="change-user">
			  <form:label path="name">Name:</form:label>
		      <form:input path="name"/>
		      
		      <form:label path="password">Password:</form:label>
		      <form:input path="password"/>
				
             <input type="submit" value="Submit">

			</form:form>
		</div>

		<footer>
		<img class="home_image" src="<c:url value="/resources/img/bank.jpg"/>"/>
		
			<ul>
				<li><a href="http://localhost:8080/iserf/">Home</a></li>
				<li><a href="http://localhost:8080/iserf/login/">Login</a></li>
				<li><a href="http://localhost:8080/iserf/bank/">Bank</a></li>
			</ul>

			<h2>Citizens</h2>
				<table id="citizen_table" class="citizen" border="1">
				<thead> 
				<tr>
					<th>ID</th>
					<th>Firstname</th>
					<th>Lastname</th>
					<th>Address</th>
					<th>Date of birthday</th>
					<th>Action</th>
				</tr>
				</thead>
				<tbody> 
				<c:forEach items="${user.getCitizenData()}" var="userItem">     
		     		<tr>
						<td>${userItem["id"]}</td>
						<td>${userItem["firstname"]}</td>
						<td>${userItem["lastname"]}</td>
						<td>${userItem["address"]}</td>
						<td>${userItem["dob"]}</td>
						<td><a href="<c:url value="/bank?user_id="/>${userItem['id']}">edit</a></td>
					</tr>
		    	</c:forEach>
		    	</tbody>	
		       </table>
		</footer>

	</div>
<script type="text/javascript">

jQuery(document).ready(function() { 
	    jQuery("#citizen_table").tablesorter(); 
    }); 
</script>
</body>
</html>
