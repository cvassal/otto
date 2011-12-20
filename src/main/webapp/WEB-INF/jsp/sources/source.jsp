<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>

<%@ taglib uri="http://github.com/dbourdette/otto/quartz" prefix="quartz" %>

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

<widget:head/>

<body>
<widget:header/>

<article>
    <widget:nav/>

    <div>
        <sec:authorize access="hasRole('ROLE_ADMIN')">
            <br/>
            Capping : ${source.capped ? 'yes' : 'no'}
            <c:if test="${source.capped}">
                (size : ${source.size}, max : <fmt:formatNumber value="${source.max}" type="number"/>)
            </c:if>
            <c:if test="${not source.capped}">
                (<a href="/sources/${name}/capping/form">add capping</a>)
            </c:if>
            <br/><br/>
            <a href="/sources/${name}/aggregation/form">Aggregation</a> :
            <c:if test="${aggregation.timeFrame eq 'MILLISECOND'}">none</c:if>
            <c:if test="${not (aggregation.timeFrame eq 'MILLISECOND')}">${aggregation.timeFrame} on attribute ${aggregation.attributeName}</c:if>
            <br/><br/>
            <a href="/sources/${name}/default-graph-params/form">Default gragh parameters</a> :
            <c:if test="${not empty defaultGraphParameters.period}">period <b>${defaultGraphParameters.period}</b></c:if>

            <h3>Reports</h3>
            <table>
                <thead>
                <tr>
                    <th>title</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="report" items="${reports}">
                    <tr>
                        <td>${report.title}</td>
                    </tr>
                    <tr>
                        <td colspan="4">
                            <a href="/sources/${name}/report/${report.id}">edit</a>
                            - <a href="/sources/${name}/report/${report.id}/delete">delete</a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>

            <a href="/sources/${name}/report">add a report</a>

            <h3>Mail reports</h3>
            <table>
                <thead>
                <tr>
                    <th>report</th>
                    <th>title</th>
                    <th>planification</th>
                    <th>previous firetime</th>
                    <th>next firetime</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="mailReport" items="${mailReports}">
                    <tr>
                        <td>${mailReport.reportTitle}</td>
                        <td>${mailReport.title}</td>
                        <td>${mailReport.cronExpression}</td>
                        <td>${quartz:previousFiretime(mailReport)}</td>
                        <td>${quartz:nextFiretime(mailReport)}</td>
                    </tr>
                    <tr>
                        <td colspan="4">
                            <a href="/sources/${name}/mailreport/${mailReport.id}">edit</a>
                            - <a href="/sources/${name}/mailreport/${mailReport.id}/send">send now</a>
                            - <a href="/sources/${name}/mailreport/${mailReport.id}/delete">delete</a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>

            <a href="/sources/${name}/mailreport">add a mail report</a>

            <h3>Transform operations</h3>
            <table>
                <thead>
                <tr>
                    <th>parameter</th>
                    <th>operations</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="parameter" items="${transform.config}">
                    <tr>
                        <td>${parameter.name}</td>
                        <td>${parameter.operations}</td>
                    </tr>
                    <tr>
                        <td colspan="4">
                            <a href="/sources/${name}/transform/${parameter.name}">edit</a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>

            <a href="/sources/${name}/transform">add a transform operation</a>

            <h3>Indexes</h3>
            <table>
                <colgroup>
                    <col>
                </colgroup>
                <c:forEach var="index" items="${indexes}">
                    <tr>
                        <td>${index}</td>
                    </tr>
                    <c:if test="${not (index.name eq '_id_')}">
                        <tr>
                            <td>
                                <a href="/sources/${name}/indexes/${index.name}/drop">drop</a>
                            </td>
                        </tr>
                    </c:if>
                </c:forEach>
            </table>
            <a href="/sources/${name}/indexes/form">add index</a>

            <br/><br/>

            <a href="/sources/${name}/delete">delete source</a>
        </sec:authorize>
    </div>
</article>

<widget:footer/>
</body>
</html>