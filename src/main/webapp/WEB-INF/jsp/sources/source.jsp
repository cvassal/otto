<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<%@ taglib tagdir="/WEB-INF/tags/widgets" prefix="widget" %>

<!DOCTYPE html>
<html lang="fr">

<widget:head />

<body>
	<widget:header />
	
	<article>
		<widget:nav />
		
		Event count in db : ${source.count} <br/>
		<br/>
		Capping : ${source.capped ? 'yes' : 'no'}
        <c:if test="${source.capped}">
            (size : ${source.size}, max : ${source.max})
        </c:if>
		<br/><br/>
		<a href="/sources/${name}/aggregation/form">TimeFrame</a> : ${timeFrame eq 'MILLISECOND' ? 'none' : timeFrame}
		<br/><br/>
		Event frequency : <br/>
		<ul>
			<li>today : <fmt:formatNumber value="${todayFrequency.eventsPerMinute}" pattern="# ###.######"/> events per minute</li>
			<li>yesterday : <fmt:formatNumber value="${yesterdayFrequency.eventsPerMinute}" pattern="# ###.######"/> events per minute</li>
			<li>last week : <fmt:formatNumber value="${lastWeekFrequency.eventsPerMinute}" pattern="# ###.######"/> events per minute</li>
		</ul>
		
		<br/>
		Statistics : ${source.stats}
        <br/><br/>
		
		<a href="/sources/${name}/delete">delete source</a>
	</article>
	
	<widget:footer />
</body>
</html>