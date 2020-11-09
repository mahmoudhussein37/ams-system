<%@include file="/WEB-INF/view/include/topTag.jsp" %>
<form:form modelAttribute="profUser" action="${baseUrl}/admin/profManagement/profInformation/profDetail" method="post" class="form">
    <h3 class="font-size-lg text-dark font-weight-bold mb-6"><spring:message code="common.information"/></h3>
    <div class="row">
        <div class="col-md-3">

            <div class="form-group">
                <label><spring:message code="common.profNumber"/></label>
                <input type="text" class="form-control" value="${profUser.number}" disabled/>
                    <%--<span class="form-text text-muted">Please enter your full name</span>--%>
            </div>
        </div>
        <div class="col-md-3">
            <div class="form-group">
                <label><spring:message code="common.username"/></label>
                <form:input type="text" class="form-control"  path="username"/>
                    <%--<span class="form-text text-muted">We'll never share your email with anyone else</span>--%>
            </div>

        </div>
        <div class="col-md-3">
            <div class="form-group">
                <label><spring:message code="common.firstName"/></label>
                <form:input type="text" class="form-control"  path="contact.firstName"/>
                    <%--<span class="form-text text-muted">We'll never share your email with anyone else</span>--%>
            </div>

        </div>
        <div class="col-md-3">
            <div class="form-group">
                <label><spring:message code="common.lastName"/></label>
                <form:input type="text" class="form-control"  path="contact.lastName"/>
                    <%--<span class="form-text text-muted">We'll never share your email with anyone else</span>--%>
            </div>

        </div>

    </div>
    <div class="row">
        <div class="col-md-3">
            <div class="form-group">
                <label><spring:message code="common.department"/></label>
                <form:select path="divisionId" class="form-control">
                    <c:forEach var="division" items="${divisions}">
                        <form:option value="${division.id}">${division.name}</form:option>
                    </c:forEach>
                </form:select>
                    <%--<span class="form-text text-muted">We'll never share your email with anyone else</span>--%>
            </div>

        </div>
        <div class="col-md-3">

            <div class="form-group">
                <label><spring:message code="common.activation"/></label>
                <form:select path="enabled" class="form-control">
                    <form:option value="true">Y</form:option>
                    <form:option value="false">N</form:option>
                </form:select>

                    <%--<span class="form-text text-muted">We'll never share your email with anyone else</span>--%>
            </div>
        </div>
    </div>

    <button type="submit" class="btn btn-primary mr-2"><spring:message code="common.save"/></button>
</form:form>



<%@include file="/WEB-INF/view/include/footerScript.jsp" %>
<script>

    $(document).ready(function() {
        <c:if test="${not empty result}">
        alert("<spring:message code='common.success'/>");
        location.href="${baseUrl}/admin/profManagement/profInformation";
        </c:if>
        //changeMajor("#divisionId", "#majorId", true);
    });
</script>