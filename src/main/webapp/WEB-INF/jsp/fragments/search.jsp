<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<form:form method="post" modelAttribute="searchReport" action="search">
    <table id="search">
        <tr>
            <th colspan="5">
                <spring:message code="table.search.nameForm" />
            </th>
        </tr>
        <tr>
            <td>
                <table class="element_search">
                    <tr>
                        <th>
                            <spring:message code="table.search.employee" />
                        </th>
                    </tr>
                    <tr class="ht">
                        <td>
                            <form:select path="employee">
                                <form:option value="-1">
                                    <spring:message code="table.search.notSelected"/>
                                </form:option>
                                <form:options items="${employees}" itemValue="id" itemLabel="name"/>
                            </form:select>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <form:errors path="employee" cssClass="error" />
                        </td>
                    </tr>
                </table>
            </td>
            <td>
                <table class="element_search">
                    <tr>
                        <th>
                            <spring:message code="table.search.specialty" />
                        </th>
                    </tr>
                    <tr class="ht">
                        <td>
                            <form:select path="specialty">
                                <form:option value="-1">
                                    <spring:message code="table.search.notSelected"/>
                                </form:option>
                                <form:options items="${specialties}" itemValue="id" itemLabel="name"/>
                            </form:select>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <form:errors code="specialty" cssClass="error" />
                        </td>
                    </tr>
                </table>
            </td>
            <td>
                <table class="element_search">
                    <tr>
                        <th colspan="2">
                            <spring:message code="table.search.date" />
                        </th>
                    </tr>
                    <tr class="ht2">
                        <td>Начало: </td>
                        <td>
                            <form:input path="localDateStart" />
                        </td>
                    </tr>
                    <tr class="ht2">
                        <td>Конец: </td>
                        <td>
                            <form:input path="localDateEnd" />
                        </td>
                    </tr>
                    <tr>
                        <th colspan="2">
                            <form:errors path="localDateStart" cssClass="error" /> <br />
                            <form:errors path="localDateEnd" cssClass="error" />
                        </th>
                    </tr>
                </table>
            </td>
            <td>
                <table class="element_search">
                    <tr>
                        <th colspan="2">
                            <spring:message code="table.search.time" />
                        </th>
                    </tr>
                    <tr class="ht2">
                        <td>Начало: </td>
                        <td>
                            <form:input path="localTimeStart" />
                        </td>
                    </tr>
                    <tr class="ht2">
                        <td>Конец: </td>
                        <td>
                            <form:input path="localTimeEnd" />
                        </td>
                    </tr>
                    <tr>
                        <th colspan="2">
                            <form:errors path="localTimeStart" cssClass="error"/> <br />
                            <form:errors path="localTimeEnd" cssClass="error" />
                        </th>
                    </tr>
                </table>
            </td>
            <td>
                <table class="element_search">
                    <tr>
                        <th>
                            <spring:message code="table.search.description" />
                        </th>
                    </tr>
                    <tr class="ht">
                        <td>
                            <form:input path="description" />
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <form:errors path="description" cssClass="error"/>
                        </td>
                    </tr>
                </table>
            </td>
        </tr>
        <tr>
            <th colspan="5">
                <button class="btn" type="submit">Поиск</button>
                <form>
                    <input class="btn" type="button" value="Сброс" onClick='location.href="http://localhost:8080"'>
                </form>
            </th>
        </tr>
    </table>
</form:form>