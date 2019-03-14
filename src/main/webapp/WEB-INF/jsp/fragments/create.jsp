<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<form:form method="post" modelAttribute="newReport" action="create">
    <table>
        <tr>
            <th>
                <spring:message code="table.add.nameForm" />
            </th>
        </tr>
        <tr>
            <td>
                <table class="element">
                    <tr>
                        <th><spring:message code="table.add.employee" /></th>
                    </tr>
                    <tr>
                        <td>
                            <form:select path="employee">
                                <form:options items="${employees}" itemValue="id" itemLabel="name"/>
                            </form:select>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <form:errors path="employee"  cssClass="error" />
                        </td>
                    </tr>
                </table>
            </td>
        </tr>

        <tr>
            <td>
                <table class="element">
                    <tr>
                        <th>
                            <spring:message code="table.add.specialty" />
                        </th>
                    </tr>
                    <tr>
                        <td>
                            <form:select path="specialty">
                                <form:options items="${specialties}" itemValue="id" itemLabel="name"/>
                            </form:select>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <form:errors path="specialty"  cssClass="error" />
                        </td>
                    </tr>
                </table>
            </td>
        </tr>

        <tr>
            <td>
                <table class="element">
                    <tr>
                        <th>
                            <spring:message code="table.add.date" />
                        </th>
                    </tr>
                    <tr>
                        <td>
                            <form:input path="strDate" />

                        </td>
                    </tr>
                    <tr>
                        <td>
                            <form:errors path="strDate"  cssClass="error" />
                        </td>
                    </tr>
                </table>
            </td>
        </tr>

        <tr>
            <td>
                <table class="element">
                    <tr>
                        <th>
                            <spring:message code="table.add.timeStart" />
                        </th>
                    </tr>
                    <tr>
                        <td>
                            <form:input path="strTimeStart" />
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <form:errors path="strTimeStart"  cssClass="error" />
                        </td>
                    </tr>
                </table>
            </td>
        </tr>

        <tr>
            <td>
                <table class="element">
                    <tr>
                        <th>
                            <spring:message code="table.add.timeEnd" />
                        </th>
                    </tr>
                    <tr>
                        <td>
                            <form:input path="strTimeEnd" />
                            <br />

                        </td>
                    </tr>
                    <tr>
                        <td>
                            <form:errors path="strTimeEnd"  cssClass="error" />
                        </td>
                    </tr>
                </table>
            </td>
        </tr>

        <tr>
            <td>
                <table class="element">
                    <tr>
                        <th>
                            <spring:message code="table.add.description" />
                        </th>
                    </tr>
                    <tr>
                        <td>
                            <form:input path="description" />
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <form:errors path="description"  cssClass="error" />
                        </td>
                    </tr>
                </table>
            </td>
        </tr>
        <tr>
            <td>
                <button class="btn" type="submit">Добавить</button>
            </td>
        </tr>

    </table>
</form:form>
