<c:choose>
    <c:when test="${fn:contains(requestUri, '/admin/board/notice')}">
        <spring:message code="menu.admin.sub6_1"/>
        <c:set var="boardName" value="notice"/>
    </c:when>
    <c:when test="${fn:contains(requestUri, '/admin/board/de')}">
        <spring:message code="menu.admin.sub6_2"/>
        <c:set var="boardName" value="de"/>
    </c:when>
    <c:when test="${fn:contains(requestUri, '/admin/board/hire')}">
        <spring:message code="menu.admin.sub6_3"/>
        <c:set var="boardName" value="hire"/>
    </c:when>
    <c:when test="${fn:contains(requestUri, '/admin/board/schedule')}">
        <spring:message code="menu.admin.sub6_4"/>
        <c:set var="boardName" value="schedule"/>
    </c:when>
</c:choose>