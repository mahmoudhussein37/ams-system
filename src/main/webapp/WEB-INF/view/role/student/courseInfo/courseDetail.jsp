<%@include file="/WEB-INF/view/include/topTag.jsp" %>

<div class="row">

    <div class="col-md-12">

        <div class="form-group">
            <label><spring:message code="professor.courseOverview"/></label>
            <textarea class="form-control" rows="6" disabled>${course.overview}</textarea>
            <%--<span class="form-text text-muted">Please enter your full name</span>--%>
        </div>
    </div>
</div>
<div class="row">

    <div class="col-md-12">

        <div class="form-group">
            <label><spring:message code="professor.learningObjectives"/></label>
            <textarea class="form-control" rows="6" disabled>${course.learningObjective}</textarea>
            <%--<span class="form-text text-muted">Please enter your full name</span>--%>
        </div>
    </div>
</div>



<%@include file="/WEB-INF/view/include/footerScript.jsp" %>
<script>

</script>