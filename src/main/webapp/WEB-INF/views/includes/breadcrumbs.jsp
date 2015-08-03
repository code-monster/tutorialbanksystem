<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	language="java"%>

<c:choose>
		<c:when test="${breadcrumbs.size()>0}">
		<div class='breadcrumbs-content'>

			<c:forEach items="${breadcrumbs}" var="breadcrumbsItem"
				varStatus="loop">

				<c:choose>
					<c:when test="${loop.last}">
						<span class="current">${breadcrumbsItem.getName()}</span>
					</c:when>
					<c:otherwise>
						<a href="${breadcrumbsItem.getUrl()}">${breadcrumbsItem.getName()}</a> > 
	            	</c:otherwise>
				</c:choose>


			</c:forEach>
		</div>
	</c:when>
</c:choose>