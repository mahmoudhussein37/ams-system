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
<button type="submit" class="btn btn-primary mr-2"><spring:message code="common.save"/></button>
</form:form>

<%@include file="/WEB-INF/view/include/footerScript.jsp" %>
<script>

</script>