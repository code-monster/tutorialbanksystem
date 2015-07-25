<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	language="java"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>${pageTitle}</title>
<link href="<c:url  value="/resources/css/style.css" />" rel="stylesheet" />
<link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
<script type="text/javascript"
	src="<c:url  value="/resources/js/jquery-2.1.4.min.js" />"></script>
<script type="text/javascript"
	src="<c:url  value="/resources/js/jquery.tablesorter.min.js" />"></script>
<script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
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
		<c:choose>
     <c:when test="${message.getType() == 'update'}">
     <div class='update-data'>${message.getText()}</div>
	 </c:when>
	 
	 <c:when test="${message.getType() == 'error'}">
     <div class='error-data'>${message.getText()}</div>
	 </c:when>
	 </c:choose>
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
		
		
		<h2>Accounts: </h2>
			<c:choose>
		<c:when test="${accountList.size()==0}">
        <div class='account-alert'>User don't have any accounts</div>
	    </c:when>
	    <c:otherwise>
		<table  class="sort-table" border="1">
			<thead>
				<tr>
					<th>ID</th>
					<th>Balance</th>
					<th>Action</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${accountList}" var="accountItem">
					<tr>
						<td>${accountItem.getAccountId()}</td>
						<td>${accountItem.getBalance()}</td>
						<td><a
							href="<c:url value="/transactions?account_id="/>${accountItem.getAccountId()}">show transactions</a></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		</c:otherwise>
		</c:choose>
		</main>
		<footer>
			<jsp:include page="includes/footer.jsp"></jsp:include>
		</footer>
		  <script>
		  jQuery(function() {
		    jQuery( "#dob" ).datepicker({
		    	  dateFormat: "yy-mm-dd",
		    	  changeMonth: true,
		    	  changeYear: true,
		    	  minDate:  new Date(1900, 1 - 1, 1),
		    	  maxDate:  new Date(2002, 1 - 1, 1)
		    	});
		  });
		  </script>
		  <script type="text/javascript">
			jQuery(document).ready(function() {
				jQuery(".sort-table").tablesorter();
			});
		</script>
	</div>
</body>
</html>

