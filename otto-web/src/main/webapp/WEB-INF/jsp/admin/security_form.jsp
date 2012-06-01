<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@include file="../directives.jsp"%>

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
    <widget:admin_nav />

    <h2>Authorization provider</h2>

    <form:form action="/security" commandName="form" method="post" cssClass="form-horizontal">
        <form:errors path="*">
            <div class="container">
                <div class="alert alert-danger">
                    <a class="close" data-dismiss="alert">×</a>
                        ${fn:escapeXml(messages[0])}
                </div>
            </div>
        </form:errors>
        <bootstrap:control path="authProviderClass" label="Class">
            <form:input path="authProviderClass" id="authProviderClass" cssClass="input-xxlarge" /> fully qualified class name of AuthProviderPlugin implementation
        </bootstrap:control>
        <bootstrap:control path="configuration" label="Configuration">
            <form:textarea path="configuration" id="configuration" rows="10" cssClass="input-xxlarge" /> key values properties to configure plugin
        </bootstrap:control>
        <bootstrap:submit cancelUrl="/security" />
    </form:form>
</div>

<layout:footer/>
</body>
</html>