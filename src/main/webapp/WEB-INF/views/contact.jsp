<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	language="java"%>
<jsp:include page="includes/header.jsp"></jsp:include>

		<main>
		<h1><spring:message    code="label.page_contact" /></h1>
        <p>Hi my name is Alex, you can contact with me using my <a href="https://www.facebook.com/olexey.bezpalenko" target="_blank">FB page</a>  </p>
		</main>
		<footer>
			<jsp:include page="includes/footer.jsp"></jsp:include>
		</footer>
	</div>
</body>
</html>

