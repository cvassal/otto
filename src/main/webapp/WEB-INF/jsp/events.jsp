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
		logs - <a href="/events/${name}/graph">graph</a> <br/><br/>
		
		<c:forEach var="event" items="${events}">
			${event}<br/>
		</c:forEach>
		
		<form action="/events/${name}" method="post">
			<input type="text" size="100" name="values" />
			<input type="submit" value="poster" />
		</form>
		
		<form:form action="/events/${name}" commandName="form" method="delete">
			<input type="submit" value="supprimer" />
		</form:form>
	</article>
	
	<widget:footer />
</body>
</html>