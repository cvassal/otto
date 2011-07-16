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
		index - <a href="/types/${name}/events">logs</a> - <a href="/types/${name}/graph">graph</a> <br/><br/>
		
		<form:form action="/types/${name}" commandName="form" method="delete">
			<input type="submit" value="supprimer" />
		</form:form>
	</article>
	
	<widget:footer />
</body>
</html>