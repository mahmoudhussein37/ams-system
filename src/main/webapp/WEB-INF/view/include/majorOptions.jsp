<%@include file="/WEB-INF/view/include/topTag.jsp" %>
<c:forEach var="m" items="${majorList}">
    <option value="${m.id}" <c:if test="${defaultSelected ne 0 and m.id eq defaultSelected}">selected</c:if>>${m.name}</option>
</c:forEach>