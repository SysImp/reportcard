<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<body>
<head>
    <link href="<c:url value="/resources/css/main.css" />" rel="stylesheet">
    <meta charset="UTF-8" />
    <title>Report Card</title>
</head>
<table id="add">
    <tr>
        <td>
            <jsp:include page="fragments/create.jsp" />
        </td>
    </tr>
</table>
<table id="global">
    <tr>
        <td>
            <table id="main">
                <tr>
                    <td>
                        <jsp:include page="fragments/search.jsp" />
                    </td>
                </tr>
                <tr>
                    <td>
                        <table id="reports">
                            <tr>
                                <th><spring:message code="table.reports.employee" /></th>
                                <th><spring:message code="table.reports.specialty" /></th>
                                <th><spring:message code="table.reports.date" /></th>
                                <th><spring:message code="table.reports.time" /></th>
                                <th><spring:message code="table.reports.description" /></th>
                            </tr>
                            <c:if test="${not empty reports}">
                                <c:forEach var="report" items="${reports}">
                                    <tr>
                                        <td>${report.employee.name}</td>
                                        <td>${report.specialty.name}</td>
                                        <td>${report.localDate}</td>
                                        <td>${report.localTimeStart} - ${report.localTimeEnd}</td>
                                        <td>${report.description}</td>
                                    </tr>
                                </c:forEach>
                            </c:if>
                        </table>
                    </td>
                </tr>
            </table>
        </td>
    </tr>
</table>
</body>
</html>