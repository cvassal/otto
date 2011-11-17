<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

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
<html lang="fr">

<widget:head />

<body>
	<widget:header />
	
	<article>
		<widget:nav />
	
		<h2>Edit source</h2>

        <div>
            <form:form action="/sources/edit" commandName="form" method="post">
                <p>
                    name : ${form.name}
                    <form:hidden path="name" />
                </p>
                <p>
                    display group : <form:input path="displayGroup"/>
                    <form:errors path="displayGroup" />
                </p>
                <p>
                    display name : <form:input path="displayName"/>
                    <form:errors path="displayName" />
                </p>
                <input type="submit" value="Save" />
            </form:form>
        </div>
	</article>
	
	<widget:footer />
</body>
</html>