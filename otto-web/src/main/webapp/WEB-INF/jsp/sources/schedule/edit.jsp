<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@include file="../../directives.jsp"%>

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
    <widget:nav />

    <h2>
        <c:if test="${not empty form.id}">
            <a href="/sources/${name}/schedule/${form.id}/delete" class="pull-right btn btn-danger">delete</a>
        </c:if>
        Mail schedule
    </h2>

    <div class="row">
        <div class="span12">
            <br>
            This schedules a report by mail.
            <br><br>
        </div>
    </div>

    <form:form action="/sources/${name}/schedule" commandName="form" method="post" cssClass="form-horizontal">
        <form:hidden path="id" />
        <fieldset>
            <legend>Report</legend>
            <bootstrap:control path="report" label="Report">
                <form:select path="report" items="${reports}" itemValue="title" itemLabel="title" />
            </bootstrap:control>
            <bootstrap:control path="period" label="Period">
                <form:select path="period" items="${form.periods}" />
            </bootstrap:control>
        </fieldset>
        <fieldset>
            <legend>Mail</legend>
            <bootstrap:control path="to" label="To">
                <form:textarea path="to" cssClass="input-xxlarge" rows="5"/> (comma separated values)
            </bootstrap:control>
            <bootstrap:control path="title" label="Subject">
                <form:input path="title" size="80" cssClass="input-xxlarge" />
            </bootstrap:control>
        </fieldset>
        <fieldset>
            <legend>Planification</legend>
            <bootstrap:control path="cronExpression" label="Cron expression">
                <form:input path="cronExpression"/>
            </bootstrap:control>
        </fieldset>
        <bootstrap:submit cancelUrl="/sources/${name}/configuration" />
    </form:form>

    <h2>Cron expressions</h2>

    <pre class="well">
<b>expressions are made of 5 tokens : </b>
*    *    *    *    *
┬    ┬    ┬    ┬    ┬
│    │    │    │    └───── day of week (0 - 6) (0 is Sunday, or use names)
│    │    │    └────────── month (1 - 12)
│    │    └─────────────── day of month (1 - 31)
│    └──────────────────── hour (0 - 23)
└───────────────────────── min (0 - 59)

A field may be an asterisk (*), which always stands for "every".

<b>"nicknames" are supported :</b>
@yearly	  :    Run once a year, ie.  "0 0 1 1 *".
@annually :    Run once a year, ie.  "0 0 1 1 *".
@monthly  :    Run once a month, ie. "0 0 1 * *".
@weekly	  :    Run once a week, ie.  "0 0 * * 0".
@daily	  :    Run once a day, ie.   "0 0 * * *".
@hourly	  :    Run once an hour, ie. "0 * * * *".

<b>Examples :</b>
Every minute                 : * * * * *
Every 1 minute               : * * * * *
23:00:00 every weekday night : 0 23 ? * MON-FRI

full doc on <a href="http://unixhelp.ed.ac.uk/CGI/man-cgi?crontab+5" target="_blank">cron expression <i class="icon-share"></i></a>
    </pre>
</div>

<layout:footer/>
</body>
</html>