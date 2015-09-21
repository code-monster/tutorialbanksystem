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



        <form:form  commandName="person" method="post">
                <tr>
                    <td align="left" width="20%">Name: </td>
                    <td align="left" width="40%"><form:input path="name" size="30"/></td>
                    <td align="left"><form:errors path="name" cssClass="error"/></td>
                </tr>
                <tr>
                    <td>age: </td>
                    <td><form:input path="age" size="30"/></td>
                    <td><form:errors path="age" cssClass="error"/></td>
                </tr>
                <tr>
                    <td></td>
                    <td align="center"><input type="submit" value="Login"/></td>
                    <td></td>
                </tr>
        </form:form>



</main>
<footer>
	<jsp:include page="includes/footer.jsp"></jsp:include>
</footer>
</div>
</body>
</html>

