<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	language="java"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>${pageTitle}</title>
<link href="<c:url  value="/resources/css/style.css" />"
	rel="stylesheet" />
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

		<main>
	 <c:if test="${message.getType() == 'update'}">
     <div class='update-data'>${message.getText()}</div>
	 </c:if>
	 
	 <c:if test="${message.getType() == 'error'}">
     <div class='error-data'>${message.getText()}</div>
	 </c:if>
		<h2>Users:</h2>
		<table id="user_table" class="user" border="1">
			<thead>
				<tr>
					<th>ID</th>
					<th><spring:message    code="label.data_firstname" /></th>
					<th><spring:message    code="label.data_lastname" /></th>
					<th><spring:message    code="label.data_address" /></th>
					<th><spring:message    code="label.data_dob" /></th>
					<th><spring:message    code="label.data_action" /></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${users}" var="userItem">
					<tr>
						<td>${userItem.getUserId()}</td>
						<td>${userItem.getFirstname()}</td>
						<td>${userItem.getLastname()}</td>
						<td>${userItem.getAddress()}</td>
						<td>${userItem.getDob()}</td>
						<td><a
							href="<c:url value="/user-profile?user_id="/>${userItem.getUserId()}">edit</a>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<p class="total">total:${users.size()}</p>
		<p><a href="<c:url value="/add"/>"><spring:message    code="label.action_add_new_user" /></a></p>
		</main>
		<footer>
			<jsp:include page="includes/footer.jsp"></jsp:include>
		</footer>
		<script type="text/javascript">
			jQuery(document).ready(function() {
				jQuery("#user_table").tablesorter();
			});
		</script>
	</div>
</body>
</html>

