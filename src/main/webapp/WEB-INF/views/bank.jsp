<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	language="java"%>
<html>
<head>
<title>Home</title>
<link href="<c:url  value="/resources/css/home.css" />" rel="stylesheet" />
</head>
<body>
	<div class="main">
        <h1><a href="http://localhost:8080/iserf/">Home</a></h1>
		<h1>Bank</h1>

		<h2>${user_id}</h2>
			<h2>Citizens</h2>
			<form:form class="login-form" method="POST" commandName="user" action="change-user">
				<table id="citizen_table" class="citizen" border="1">
				<thead> 
				<tr>
					<th>ID</th>
					<th>Firstname</th>
					<th>Lastname</th>
					<th>Address</th>
					<th>Date of birthday</th>
				</tr>
				</thead>
				<tbody> 
				<c:forEach items="${user.getCitizenData()}" var="userItem">     
		     		<tr>
						<td><form:input path="userId"/></td>
						<td><form:input path="name"/></td>
						<td>${userItem["lastname"]}</td>
						<td>${userItem["address"]}</td>
						<td>${userItem["dob"]}</td>
					</tr>
		    	</c:forEach>
		    	</tbody>	
		       </table>
             <input type="submit" value="Submit">

			</form:form>

	</div>

</body>
</html>
