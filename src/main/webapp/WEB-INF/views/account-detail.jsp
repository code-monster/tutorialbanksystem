<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	language="java"%>

<jsp:include page="includes/header.jsp"></jsp:include>

<main> <c:choose>
	<c:when test="${message.getType() == 'update'}">
		<div class='update-data'>${message.getText()}</div>
	</c:when>

	<c:when test="${message.getType() == 'error'}">
		<div class='error-data'>${message.getText()}</div>
	</c:when>
</c:choose>
<h2>
	<spring:message code="label.page_account_detail" />
</h2>

<c:choose>
	<c:when test="${transactionList.size()==0}">
		<div class='account-alert'>User don't have any transactions in
			account</div>
	</c:when>
	<c:otherwise>
		<table class="sort-table" border="1">
			<thead>
				<tr>
					<th>ID</th>
					<th><spring:message code="label.data_money" /></th>
					<th><spring:message code="label.data_operation_info" /></th>
					<th><spring:message code="label.data_date" /></th>
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
<div class="transaction-control">
	<div class="transaction-control-add">
		<h2>Add money</h2>
		<form:form  method="POST" commandName="transaction"
			action="add-transaction">
			<table border="1" class="add-transaction">
				<thead>
					<tr>
						<th>money</th>
						<th>comment</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td><form:errors path="money" cssClass="error-data"/><form:input path="money" /></td>
						<td><form:errors path="operation" cssClass="error-data"/><form:input path="operation" /></td>
					</tr>
				</tbody>
			</table>
			<form:hidden path="accountId" />
			<input type="submit" value="Submit">
		</form:form>
	</div>

</div>
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

