<%@include file="/WEB-INF/view/include/topTag.jsp" %>
<c:forEach var="m" items="${majorList}">
    <option value="${m.id}">${m.name}</option>
</c:forEach>