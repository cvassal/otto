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
    <div class="well">
        <div class="row"><br><br><br></div>
        <div class="row">
            <div class="span6 offset3">Please log in...</div>
        </div>
        <div class="row">
            <div class="span6 offset3">
                <form class="form-inline" method="post" action="j_spring_security_check">
                    <input name="j_username" value="" type="text" placeholder="Email"/>
                    <input name="j_password" type="password" placeholder="Password"/>
                    <button type="submit" class="btn">Submit</button>
                </form>
            </div>
        </div>
        <div class="row"><br><br><br></div>
    </div>
</div>

<layout:footer/>
</body>
</html>