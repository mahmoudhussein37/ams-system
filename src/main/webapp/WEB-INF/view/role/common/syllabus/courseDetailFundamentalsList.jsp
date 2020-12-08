<tr class="text-center">
    <td>
        1
    </td>
    <td>
        <form:input path="clo1" cssClass="form-control" disabled="true"/>
    </td>
    <td>
        <form:input path="la1" cssClass="form-control" disabled="true"/>
    </td>
    <td style="text-align:left">
        <c:set var="sList" value="${lectureFundamentals.toStringList(lectureFundamentals.reflect1)}"/>
        <c:forEach var="rfString" items="${lectureFundamentals.toStringList(lectureFundamentals.reflect1)}" varStatus="varStatus">
            <c:if test="${not empty rfString}">
                <spring:message code="professor.grade.${rfString}"/><c:if test="${varStatus.index < fn:length(sList) - 1}">,&nbsp;</c:if>
            </c:if>
        </c:forEach>
    </td>
    <td>
        <form:input path="obj1" cssClass="form-control" disabled="true"/>
    </td>
    <td>
        <form:input path="comp1" cssClass="form-control" disabled="true"/>
    </td>
</tr>

<tr class="text-center">
    <td>
        2
    </td>
    <td>
        <form:input path="clo2" cssClass="form-control" disabled="true"/>
    </td>
    <td>
        <form:input path="la2" cssClass="form-control" disabled="true"/>
    </td>
    <td style="text-align:left">
        <c:set var="sList" value="${lectureFundamentals.toStringList(lectureFundamentals.reflect2)}"/>
        <c:forEach var="rfString" items="${lectureFundamentals.toStringList(lectureFundamentals.reflect2)}" varStatus="varStatus">
            <c:if test="${not empty rfString}">
                <spring:message code="professor.grade.${rfString}"/><c:if test="${varStatus.index < fn:length(sList) - 1}">,&nbsp;</c:if>
            </c:if>
        </c:forEach>

    </td>
    <td>
        <form:input path="obj2" cssClass="form-control" disabled="true"/>
    </td>
    <td>
        <form:input path="comp2" cssClass="form-control" disabled="true"/>
    </td>
</tr>

<tr class="text-center">
    <td>
        3
    </td>
    <td>
        <form:input path="clo3" cssClass="form-control" disabled="true"/>
    </td>
    <td>
        <form:input path="la3" cssClass="form-control" disabled="true"/>
    </td>
    <td style="text-align:left">
        <c:set var="sList" value="${lectureFundamentals.toStringList(lectureFundamentals.reflect3)}"/>
        <c:forEach var="rfString" items="${lectureFundamentals.toStringList(lectureFundamentals.reflect3)}" varStatus="varStatus">
            <c:if test="${not empty rfString}">
                <spring:message code="professor.grade.${rfString}"/><c:if test="${varStatus.index < fn:length(sList) - 1}">,&nbsp;</c:if>
            </c:if>
        </c:forEach>

    </td>
    <td>
        <form:input path="obj3" cssClass="form-control" disabled="true"/>
    </td>
    <td>
        <form:input path="comp3" cssClass="form-control" disabled="true"/>
    </td>
</tr>

<tr class="text-center">
    <td>
        4
    </td>
    <td>
        <form:input path="clo4" cssClass="form-control" disabled="true"/>
    </td>
    <td>
        <form:input path="la4" cssClass="form-control" disabled="true"/>
    </td>
    <td style="text-align:left">
        <c:set var="sList" value="${lectureFundamentals.toStringList(lectureFundamentals.reflect4)}"/>
        <c:forEach var="rfString" items="${lectureFundamentals.toStringList(lectureFundamentals.reflect4)}" varStatus="varStatus">
            <c:if test="${not empty rfString}">
                <spring:message code="professor.grade.${rfString}"/><c:if test="${varStatus.index < fn:length(sList) - 1}">,&nbsp;</c:if>
            </c:if>
        </c:forEach>

    </td>
    <td>
        <form:input path="obj4" cssClass="form-control" disabled="true"/>
    </td>
    <td>
        <form:input path="comp4" cssClass="form-control" disabled="true"/>
    </td>
</tr>

<tr class="text-center">
    <td>
        5
    </td>
    <td>
        <form:input path="clo5" cssClass="form-control" disabled="true"/>
    </td>
    <td>
        <form:input path="la5" cssClass="form-control" disabled="true"/>
    </td>
    <td style="text-align:left">
        <c:set var="sList" value="${lectureFundamentals.toStringList(lectureFundamentals.reflect5)}"/>
        <c:forEach var="rfString" items="${lectureFundamentals.toStringList(lectureFundamentals.reflect5)}" varStatus="varStatus">
            <c:if test="${not empty rfString}">
                <spring:message code="professor.grade.${rfString}"/><c:if test="${varStatus.index < fn:length(sList) - 1}">,&nbsp;</c:if>
            </c:if>
        </c:forEach>

    </td>
    <td>
        <form:input path="obj5" cssClass="form-control" disabled="true"/>
    </td>
    <td>
        <form:input path="comp5" cssClass="form-control" disabled="true"/>
    </td>
</tr>

<tr class="text-center">
    <td>
        6
    </td>
    <td>
        <form:input path="clo6" cssClass="form-control" disabled="true"/>
    </td>
    <td>
        <form:input path="la6" cssClass="form-control" disabled="true"/>
    </td>
    <td style="text-align:left">
        <c:set var="sList" value="${lectureFundamentals.toStringList(lectureFundamentals.reflect6)}"/>
        <c:forEach var="rfString" items="${lectureFundamentals.toStringList(lectureFundamentals.reflect6)}" varStatus="varStatus">
            <c:if test="${not empty rfString}">
                <spring:message code="professor.grade.${rfString}"/><c:if test="${varStatus.index < fn:length(sList) - 1}">,&nbsp;</c:if>
            </c:if>
        </c:forEach>

    </td>
    <td>
        <form:input path="obj6" cssClass="form-control" disabled="true"/>
    </td>
    <td>
        <form:input path="comp6" cssClass="form-control" disabled="true"/>
    </td>
</tr>