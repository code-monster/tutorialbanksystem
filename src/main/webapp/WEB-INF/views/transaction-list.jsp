<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
		 language="java"%>

<jsp:include page="includes/header.jsp"></jsp:include>


<main>
	<c:if test="${message.getType() == 'update'}">
		<div class='update-data'>${message.getText()}</div>
	</c:if>

	<c:if test="${message.getType() == 'error'}">
		<div class='error-data'>${message.getText()}</div>
	</c:if>
	<h2>Transactions:</h2>
	<table class=" sort-table" border="1">
		<thead>
		<tr>
			<th>ID</th>
			<th>Account ID</th>
			<th>Operation</th>
			<th>Date</th>
			<th>Money</th>
		</tr>
		</thead>
		<tbody>
		<c:forEach items="${transactionList}" var="transaction">
			<tr>
				<td>${transaction.getTransactionId()}</td>
				<td>${transaction.getAccountId()}</td>
				<td>${transaction.getOperation()}</td>
				<td>${transaction.getDate()}</td>
				<td>${transaction.getMoney()}</td>
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
		jQuery(".sort-table").tablesorter();
	});
</script>
</div>
</body>
</html>
