<%@include file="/WEB-INF/view/include/topTag.jsp" %>
<form:form modelAttribute="course" action="${baseUrl}/admin/courseManagement/courseDetail" method="post" class="form">
    <h3 class="font-size-lg text-dark font-weight-bold mb-6"><spring:message code="admin.editCourse"/></h3>
    <div class="row">
        <div class="col-md-3">
            <div class="form-group">
                <label><spring:message code="common.courseCode"/></label>
                <form:input path="code" type="text" class="form-control"/>
                    <%--<span class="form-text text-muted">Please enter your full name</span>--%>
            </div>
        </div>
        <div class="col-md-3">

            <div class="form-group">
                <label><spring:message code="common.courseTitle"/></label>
                <form:input path="title" type="text" class="form-control"/>
                    <%--<span class="form-text text-muted">We'll never share your email with anyone else</span>--%>
            </div>
        </div>
        <div class="col-md-3">
            <div class="form-group">
                <label><spring:message code="common.department"/></label>
                <form:input path="divisionId" type="text" class="form-control" readonly="true"/>
                    <%--<span class="form-text text-muted">Please enter your full name</span>--%>
            </div>
        </div>
            <%--<div class="col-md-3">
                <div class="form-group">
                    <label><spring:message code="common.major"/></label>
                    <form:input path="majorId" type="text" class="form-control"/>
                </div>
            </div>--%>
    </div>
    <div class="row">
        <div class="col-md-12">
            <div class="form-group">
                <label><spring:message code="professor.achievements"/></label>
                <table class="table rounded">
                    <thead>
                    <tr class="table-secondary text-center">

                        <c:forEach var="achi" begin="1" end="10">
                            <td>
                                    ${achi}
                            </td>
                        </c:forEach>
                    </tr>
                    </thead>
                    <tbody>
                    <tr class="table-light text-center">
                        <c:forEach var="achi" begin="1" end="10">
                            <td>
                                <spring:message code="professor.achieve${achi}"/>
                            </td>
                        </c:forEach>
                    </tr>
                    <tr class="text-center">
                        <c:forEach var="achi" begin="1" end="10">
                            <td>
                                <form:checkbox path="achieve${achi}"/>
                            </td>
                        </c:forEach>
                    </tr>

                    </tbody>
                </table>


            </div>
        </div>
    </div>
    <button type="submit" class="btn btn-primary mr-2"><spring:message code="common.save"/></button>
</form:form>

<%@include file="/WEB-INF/view/include/footerScript.jsp" %>
<script>

</script>