<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	language="java"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title> ${pageTitle}</title>
<link href="<c:url  value="/resources/css/style.css" />" rel="stylesheet" />
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
		<h1>Contact</h1>
        <p>Hi my name is Alex, you can contact with me using my <a href="https://www.facebook.com/olexey.bezpalenko" target="_blank">FB page</a>  </p>
		</main>
		<footer>
			<jsp:include page="includes/footer.jsp"></jsp:include>
		</footer>
	</div>
</body>
</html>

