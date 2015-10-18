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
	<h2>Filter by</h2>

	<table>
		<tr>
			<td>
				<div class="filter">
				<h4>user:</h4>
				<select class="auto-sender">
					<c:forEach items="${userList}" var="userItem">
						<option value="${userItem.getUserId()}"
								<c:if test="${userItem.getUserId() == param.showUserTransaction}">
									selected
								</c:if>
								>${userItem.getFirstname()} ${userItem.getLastname()}</option>
					</c:forEach>
				</select>
			</div></td>
			<td>
				<div class="filter">
				<h4>Date range:</h4>
				<script type="text/javascript" src="//cdn.jsdelivr.net/momentjs/latest/moment.min.js"></script>

				<!-- Include Date Range Picker -->
				<script type="text/javascript" src="//cdn.jsdelivr.net/bootstrap.daterangepicker/2/daterangepicker.js"></script>
				<link rel="stylesheet" type="text/css" href="//cdn.jsdelivr.net/bootstrap.daterangepicker/2/daterangepicker.css" />

				<input type="text" name="daterange"  />
					<script type="text/javascript">
							$(function() {

								var startDate = '${param.startDateRange}';
								var endDate = '${param.endDateRange}';

								if(startDate=="" || endDate == ""){
									startDate = '2015-10-08';
									endDate = '2015-10-20';
								}else{

									$(".btn-clear-date-filter").show();
								}


								$('input[name="daterange"]').daterangepicker(
										{
											locale: {
												format: 'YYYY-MM-DD'
											},
											startDate: startDate,
											endDate: endDate
										}
								);

								$('input[name="daterange"]').on('apply.daterangepicker', function(ev, picker) {

									document.location.search = $.query.set("startDateRange", picker.startDate.format('YYYY-MM-DD')).set("endDateRange", picker.endDate.format('YYYY-MM-DD')).toString();
								});

							});


				    </script>
					<a class="btn-clear-date-filter" href="javascript:document.location.search =  $.query.remove('startDateRange').remove('endDateRange')">clear date filter</a>
			</div>
			</td>
		</tr>
	</table>



	<h2>Transactions:</h2>
	<table class=" sort-table" border="1">
		<thead>
		<tr>
			<th>ID</th>
			<th>Account ID</th>
			<th>Account owner</th>
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
				<td>${transaction.getAccountOwnerFirstname()} ${transaction.getAccountOwnerLastname()}</td>
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
