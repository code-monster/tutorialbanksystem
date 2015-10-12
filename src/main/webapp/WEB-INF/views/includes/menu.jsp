<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	language="java"%>

			<ul>
				<li><a href="<c:url value="/"/>"><spring:message    code="label.page_home" /></a></li>
				<li><a href="<c:url value="/contact"/>"><spring:message    code="label.page_contact" /></a></li>
				<li><a href="<c:url value="/transaction-list"/>">Transaction list</a></li>
			</ul>

