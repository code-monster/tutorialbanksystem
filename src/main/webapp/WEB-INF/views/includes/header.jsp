<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	language="java"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>${pageTitle}</title>
<link href="<c:url  value="/resources/css/style.css" />"
	rel="stylesheet" />
<link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
<script type="text/javascript"
	src="<c:url  value="/resources/js/jquery-2.1.4.min.js" />"></script>
<script type="text/javascript"
	src="<c:url  value="/resources/js/jquery.tablesorter.min.js" />"></script>
	
<script type="text/javascript"
	src="<c:url  value="/resources/js/script.js" />"></script>
	
<script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
<script type="text/javascript">
function deleteUser(){
	if (confirm('<spring:message    code="label.alert_delete_user" />')) {
		window.location.href = '<c:url value="/delete-user?user_id="/>${user.getUserId()}';
	}	
}
function deleteAccount(accountId){
	if (confirm('<spring:message    code="label.alert_delete_account" /> ='+accountId+'?')) {
		window.location.href = '<c:url value="/delete-account?account_id="/>'+accountId+"&user_id=${user.getUserId()}";
	}	
}
</script>
	
</head>
<body>
	<div class="main">
		<header>
			<div class="header">
    <div class="header-contant">
    <div>
    <a href="javascript:bankSystemApp.insertParam('locale','en');">en</a> 
    | 
    <a href="javascript:bankSystemApp.insertParam('locale','ru');">ru</a>   
    </div>
      <div class="h-title">Bank app</div>
	 <div class="img-container">
	      <a href="<c:url value="/"/>">
	           <img  src="<c:url value="/resources/img/bank_img.png"/>"/>
	     </a>
	 </div>
	</div>
	<div class="cleared"></div>	
</div>

		<nav>
			<menu>
				<jsp:include page="menu.jsp"></jsp:include>
			</menu>
		</nav>
		
		<div class="breadcrumbs">
				<jsp:include page="breadcrumbs.jsp"></jsp:include>
		</div>
		</header>
	
	
	
	