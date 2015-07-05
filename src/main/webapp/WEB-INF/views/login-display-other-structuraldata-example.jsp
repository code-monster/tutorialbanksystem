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

		<h1>HOME PAGE - Главная страница</h1>



		<div class="login-form-container">

			<form:form class="login-form" method="POST" commandName="user" action="check-user">
			  <form:label path="name">Name:</form:label>
		      <form:input path="name"/>
		      
		      <form:label path="password">Password:</form:label>
		      <form:input path="password"/>
				
             <input type="submit" value="Submit">

			</form:form>
		</div>

		<footer>
		
			<ul>
				<li><a href="http://localhost:8080/iserf/">Home</a></li>
				<li><a href="http://localhost:8080/iserf/login/">Login</a></li>
				<li><a href="http://localhost:8080/iserf/bank/">Bank</a></li>
			</ul>
		
			<ul>
			<c:forEach items="${user.getCitizenData2()}" var="userItem">     
	        <li> <c:out value="${userItem}"/></li>
	    	</c:forEach>	
			</ul>
			
			
			<h2>hashmap</h2>
			<ul>
			<c:forEach items="${user.getHashmap()}" var="userItem">     
	        <li>    Key: ${userItem.key}  - Value: ${userItem.value}</li>
	    	</c:forEach>	
			</ul>
			

			
			<h2>arraylist with hashmap</h2>
			<ul>
			<c:forEach items="${user.getCitizenData()}" var="userItem">     
	     
				
		        <li>    Key: ${userItem["id"]}  --  Firstname: ${userItem["firstname"]}   --  Lastname: ${userItem["lastname"]}    </li>
		    	
	
	    	</c:forEach>	
			</ul>
		</footer>



	</div>

</body>
</html>
