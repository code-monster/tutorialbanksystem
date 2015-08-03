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

		<main>
		<c:choose>
     <c:when test="${message.getType() == 'update'}">
     <div class='update-data'>${message.getText()}</div>
	 </c:when>
	 
	 <c:when test="${message.getType() == 'error'}">
     <div class='error-data'>${message.getText()}</div>
	 </c:when>
	 </c:choose>
		<h2>Account detail</h2>

			<c:choose>
		<c:when test="${transactionList.size()==0}">
        <div class='account-alert'>User don't have any transactions in account</div>
	    </c:when>
	    <c:otherwise>
		<table  class="sort-table" border="1">
			<thead>
				<tr>
					<th>ID</th>
					<th>Money</th>
					<th>Operation info</th>
					<th>Date</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${transactionList}" var="transactionItem">
					<tr>
						<td>${transactionItem.getAccountId()}</td>
						<td>${transactionItem.getMoney()}</td>
						<td>${transactionItem.getOperation()}</td>
						<td>${transactionItem.getDate()}</td>
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
		  <script type="text/javascript">
			jQuery(document).ready(function() {
				jQuery(".sort-table").tablesorter();
			});
		</script>
	</div>
</body>
</html>

