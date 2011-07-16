<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<%@ taglib tagdir="/WEB-INF/tags/widgets" prefix="widget" %>

<!DOCTYPE html>
<html lang="fr">

<widget:head />

<body>
	<widget:header />
	
	<article>
		<h2>Existing event types</h2>
	
		<ul>
			<c:forEach var="type" items="${types}">
				<li><a href="/types/${type}">${type}</a></li>
			</c:forEach>
		</ul>
		
		<br/>
		
		<a href="/types/form">Create a new event type</a>
	</article>
	
	<widget:footer />
</body>
</html>