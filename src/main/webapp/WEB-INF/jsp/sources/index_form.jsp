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

		<h2>Add an index</h2>

        <div>
            <form:form action="/sources/${name}/indexes" commandName="form" method="post">
                <p>
                    key : <form:input path="key"/> (ex : date)
                    <form:errors path="key" />
                </p>
                <p>
                    name : <form:input path="indexName"/> (defaulted to key)
                    <form:errors path="indexName" />
                </p>
                <p>
                    ascending : <form:checkbox path="ascending"/> (should be descending for date key)
                    <form:errors path="ascending" />
                </p>
                <p>
                    background : <form:checkbox path="background"/> (build index without blocking database)
                    <form:errors path="background" />
                </p>
                <input type="submit" value="Create" />
            </form:form>
        </div>
	</article>

	<widget:footer />
</body>
</html>