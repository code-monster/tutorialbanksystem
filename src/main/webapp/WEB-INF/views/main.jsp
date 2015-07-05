<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	language="java"%>
<html>
<head>
<title>Home</title>
<link href="<c:url  value="/resources/css/home.css" />" rel="stylesheet" />
</head>
<body>
	<div class="main">

     <p>Hello  ${user.name}!</p>
     <p>Password  ${user.password}!</p>



	</div>

</body>
</html>
