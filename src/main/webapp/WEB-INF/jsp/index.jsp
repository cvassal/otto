<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>

<%@ taglib tagdir="/WEB-INF/tags/layout" prefix="layout" %>
<%@ taglib tagdir="/WEB-INF/tags/widgets" prefix="widget" %>

<%--
  ~ Copyright 2011 Damien Bourdette
  ~
  ~ Licensed under the Apache License, Version 2.0 (the "License");
  ~ you may not use this file except in compliance with the License.
  ~ You may obtain a copy of the License at
  ~
  ~ http://www.apache.org/licenses/LICENSE-2.0
  ~
  ~ Unless required by applicable law or agreed to in writing, software
  ~ distributed under the License is distributed on an "AS IS" BASIS,
  ~ WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  ~ See the License for the specific language governing permissions and
  ~ limitations under the License.
  --%>

<!DOCTYPE html>
<html lang="en">

<layout:head/>

<body>
<layout:header/>

<div class="container">
    <div class="page-header">
        <sec:authorize access="hasRole('ROLE_ADMIN')">
            <a href="/sources/form" style="float: right">
                <button class="btn btn-primary">new source</button>
            </a>
        </sec:authorize>
        <h1>Existing event sources</h1>
    </div>

    <ul class="nav nav-list">
        <c:forEach var="group" items="${groups.groups}">
            <li class="nav-header">${group.name}</li>
            <c:forEach var="source" items="${group.sources}">
                <sec:authorize access="T(com.github.dbourdette.otto.security.Security).hasSource('${source.name}')">
                    <li><a href="/sources/${source.name}">
                        <c:if test="${not empty source.displayName}">${fn:escapeXml(source.displayName)}</c:if>
                        <c:if test="${empty source.displayName}">${fn:escapeXml(source.name)}</c:if>
                    </a></li>
                </sec:authorize>
            </c:forEach>
        </c:forEach>
    </ul>
</div>

<layout:footer/>
</body>
</html>