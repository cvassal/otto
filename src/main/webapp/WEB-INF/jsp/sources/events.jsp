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
		<widget:nav />
		
		<div class="logs">
			<c:forEach var="event" items="${events}">
				${event}<br/>
			</c:forEach>
		</div>
		
		<br/>
		
		<a href="/sources/${name}/events/delete">Delete events</a>
	</article>
	
	<widget:footer />
</body>
</html>