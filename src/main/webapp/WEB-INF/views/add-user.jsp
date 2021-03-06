<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	language="java"%>
<jsp:include page="includes/header.jsp"></jsp:include>

	<main> 
		
     <c:if test="${message.getType() == 'error'}">
     <div class='error-data'>${message.getText()}</div>
	 </c:if>
		
		<h2><spring:message    code="label.page_add_new_user" /></h2>
		<form:form class="login-form" method="POST" commandName="user"
			action="save-new-user">
			<table id="edit_user_table" class="user" border="1">
				<thead>
					<tr>			
					<th><spring:message    code="label.data_firstname" /></th>
					<th><spring:message    code="label.data_lastname" /></th>
					<th><spring:message    code="label.data_address" /></th>
					<th><spring:message    code="label.data_dob" /></th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td><form:input path="firstname" /></td>
						<td><form:input path="lastname" /></td>
						<td><form:input path="address" /></td>
						<td><form:input path="dob" /></td>
					</tr>
				</tbody>
			</table>
			<input type="submit" value="Submit">

		</form:form> </main>
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
	</div>
</body>
</html>

