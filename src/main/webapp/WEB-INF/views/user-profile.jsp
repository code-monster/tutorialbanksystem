<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	language="java"%>

<jsp:include page="includes/header.jsp"></jsp:include>

		<main>
		<c:choose>
     <c:when test="${message.getType() == 'update'}">
     <div class='update-data'>${message.getText()}</div>
	 </c:when>
	 
	 <c:when test="${message.getType() == 'error'}">
     <div class='error-data'>${message.getText()}</div>
	 </c:when>
	 </c:choose>
		<h2><spring:message    code="label.page_user_profile" /></h2>
		<form:form class="login-form" method="POST" commandName="user"
			action="change-user">
			<table id="edit_user_table" class="user" border="1">
				<thead>
					<tr>
						<th>ID</th>
						<th><spring:message    code="label.data_firstname" /></th>
						<th><spring:message    code="label.data_lastname" /></th>
						<th><spring:message    code="label.data_address" /></th>
						<th><spring:message    code="label.data_dob" /></th>
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
		<p><a href="javascript:deleteUser()"><spring:message    code="label.action_delete_user" /></a></p>
		<h2><spring:message    code="label.data_acounts" />: </h2>
			<c:choose>
		<c:when test="${accountList.size()==0}">
        <div class='account-alert'>User don't have any accounts</div>
	    </c:when>
	    <c:otherwise>
		<table  class="sort-table" border="1">
			<thead>
				<tr>
					<th>ID</th>
					<th><spring:message    code="label.data_balance" /></th>
					<th>number Of Transaction</th>
					<th><spring:message    code="label.data_action" /></th>					
					<th><spring:message    code="label.data_delete" /></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${accountList}" var="accountItem">
					<tr>
						<td>${accountItem.getAccountId()}</td>
						<td>${accountItem.getBalance()}</td>
						<td>${accountItem.getNumberOfTransaction()}</td>
						<td><a
							href="<c:url value="/account-detail?account_id="/>${accountItem.getAccountId()}">manage transactions</a></td>
						<td><a class="btn-delete" href="javascript:deleteAccount(${accountItem.getAccountId()})">X</a></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<p class="total-money">Total money: ${user.getTotalMoney()}</p>
		</c:otherwise>
		</c:choose>
		<p><a href="<c:url value="/add-account?user_id="/>${user.getUserId()}"><spring:message    code="label.action_add_new_account" /></a></p> 
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

