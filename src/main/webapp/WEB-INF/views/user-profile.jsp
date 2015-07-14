<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	language="java"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>${pageTitle}</title>
<link href="<c:url  value="/resources/css/style.css" />"
	rel="stylesheet" />
</head>
<body>
	<div class="main">
		<header>
			<jsp:include page="includes/header.jsp"></jsp:include>
		</header>
		<nav>
			<menu>
				<jsp:include page="includes/menu.jsp"></jsp:include>
			</menu>
		</nav>
		<main> <%
 	if (request.getParameter("pageMessage") != null) {
 		if (request.getParameter("pageMessage").equals("update")) {
 			out.println("<div class='update-data'>Data is update</div>");
 		} else if (request.getParameter("pageMessage").equals("error")) {
 			out.println("<div class='update-data'>Data is update</div>");
 		}
 	}
 %>
		<h2>Edit User</h2>
		<form:form class="login-form" method="POST" commandName="user"
			action="change-user">
			<table id="edit_user_table" class="citizen" border="1">
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
					<tr>
						<td><form:hidden path="userId" />${user.getUserId()}</td>
						<td><form:input path="firstname" /></td>
						<td><form:input path="lastname" /></td>
						<td><form:input path="address" /></td>
						<td><form:input path="dob" /></td>
					</tr>
				</tbody>
			</table>
			<input type="submit" value="Submit">

		</form:form> 
		<p><a href="<c:url value="/delete-user?user_id="/>${user.getUserId()}">Delete user</a></p>
		</main>
		<footer>
			<jsp:include page="includes/footer.jsp"></jsp:include>
		</footer>
	</div>
</body>
</html>

