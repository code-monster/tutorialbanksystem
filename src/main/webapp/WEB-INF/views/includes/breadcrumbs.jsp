<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	language="java"%>

		<c:choose>
		<c:when test="${breadcrumbs.size()>0}">
			<ul>
				<c:forEach items="${breadcrumbs}" var="breadcrumbsItem">
					<li>
                    <a href="${breadcrumbsItem.getUrl()}" >${breadcrumbsItem.getName()}</a>
					</li>
				</c:forEach>
			</ul>
	    </c:when>
		</c:choose>