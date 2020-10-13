<%@include file="/WEB-INF/view/include/topTag.jsp" %>
<c:forEach var="p" items="${profList}">
    <option value="${p.id}" data-division-id="${p.divisionId}" <c:if test="${defaultSelected ne 0 and p.id eq defaultSelected}">selected</c:if>>${p.getFullName()}</option>
</c:forEach>