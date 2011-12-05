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

		<h2>Report</h2>

        <div>
            <form:form action="/sources/${name}/report" commandName="form" method="post">
                <form:hidden path="id" />
                <p>
                    title : <form:input path="title"/>
                    <form:errors path="title" />
                </p>
                <p>
                    split on : <form:input path="splitOn" />  (comma separated values)
                    <form:errors path="splitOn" />
                </p>
                <p>
                    sum on : <form:input path="sumOn" />
                    <form:errors path="sumOn" />
                </p>
                <p>
                    tokenize on : <form:input path="tokenizeOn" />
                    <form:errors path="tokenizeOn" />
                </p>
                <p>
                    tokenize stop words : <form:input path="tokenizeStopWords" />
                    <form:errors path="tokenizeStopWords" />
                </p>
                <p>
                    sort by : <form:select path="sort">
                                <form:option value="" />
                                <form:options items="${form.sorts}" />
                            </form:select>
                    <form:errors path="sort" />
                </p>
                <input type="submit" value="Save" />
            </form:form>
        </div>
	</article>

	<widget:footer />
</body>
</html>