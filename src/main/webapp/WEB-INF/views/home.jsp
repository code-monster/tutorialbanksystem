<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	language="java"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title> ${pageTitle}</title>
<link href="<c:url  value="/resources/css/style.css" />" rel="stylesheet" />
<script type="text/javascript"
	src="<c:url  value="/resources/js/jquery-2.1.4.min.js" />"></script>
<script type="text/javascript"
	src="<c:url  value="/resources/js/jquery.tablesorter.min.js" />"></script>
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
		<main>
		<h2>Citizens:</h2>
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
						<td><a
							href="<c:url value="/edit-user?user_id="/>${userItem['id']}">edit</a></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		</main>
		<footer>
			<jsp:include page="includes/footer.jsp"></jsp:include>
		</footer>
		<script type="text/javascript">
			jQuery(document).ready(function() {
				jQuery("#citizen_table").tablesorter();
			});
		</script>
	</div>
</body>
</html>

