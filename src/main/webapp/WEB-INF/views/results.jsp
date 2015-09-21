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
	Form test
</h2>



		Congratulations! You are old enough to sign up for this site.



</main>
<footer>
	<jsp:include page="includes/footer.jsp"></jsp:include>
</footer>
</div>
</body>
</html>

