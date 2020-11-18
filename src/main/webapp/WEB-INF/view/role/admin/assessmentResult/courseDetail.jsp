<%@include file="/WEB-INF/view/include/topTag.jsp" %>
<div class="print-div">
    <a href="#" class="btn btn-sm btn-light font-weight-bold print">
        <spring:message code="common.print"/>
    </a>
</div>
<div class="row">
    <div class="col-md-3">
        <div class="form-group">
            <label><spring:message code="common.semester"/></label>
            <input type="text" class="form-control" disabled value="${pc.semester.year} - ${pc.semester.semester}"/>

        </div>
    </div>
    <div class="col-md-3">

        <div class="form-group">
            <label><spring:message code="common.divide"/></label>
            <input type="text" class="form-control" disabled value="${pc.divide}"/>
            <%--<span class="form-text text-muted">We'll never share your email with anyone else</span>--%>
        </div>
    </div>
    <div class="col-md-3">
        <div class="form-group">
            <label><spring:message code="common.professor"/></label>
            <input type="text" class="form-control" disabled value="${pc.professorUser.getFullName()}"/>
        </div>
    </div>
</div>
<div class="row">
    <div class="col-md-3">
        <div class="form-group">
            <label><spring:message code="common.courseCode"/></label>
            <input type="text" class="form-control" disabled value="${pc.course.code}"/>
            <%--<span class="form-text text-muted">Please enter your full name</span>--%>
        </div>
    </div>
    <div class="col-md-3">
        <div class="form-group">
            <label><spring:message code="common.courseTitle"/></label>
            <input type="text" class="form-control" disabled value="${pc.course.title}"/>
            <%--<span class="form-text text-muted">Please enter your full name</span>--%>
        </div>
    </div>
</div>


<ul class="nav nav-tabs nav-tabs-line">
    <li class="nav-item">
        <a class="nav-link active" data-toggle="tab" href="#kt_tab_pane_1"><spring:message code="common.result"/></a>
    </li>
    <li class="nav-item">
        <a class="nav-link" data-toggle="tab" href="#kt_tab_pane_2"><spring:message code="common.comment"/></a>
    </li>

</ul>
<div class="tab-content mt-5" id="myTabContent">
    <div class="tab-pane fade show active" id="kt_tab_pane_1" role="tabpanel" aria-labelledby="kt_tab_pane_1">
        <%@include file="/WEB-INF/view/role/admin/assessmentResult/result.jsp" %>
    </div>
    <div class="tab-pane fade" id="kt_tab_pane_2" role="tabpanel" aria-labelledby="kt_tab_pane_2">
        <%@include file="/WEB-INF/view/role/admin/assessmentResult/comment.jsp" %>
    </div>
</div>



<%@include file="/WEB-INF/view/include/footerScript.jsp" %>
<script>
$(document).ready(function() {
    $("body").on('click', '.print', function (e) {
        e.preventDefault();
        openPage("${baseUrl}/admin/academicManagement/assessmentResult/courseDetail?print=true&courseId=${pc.id}");
    });
});
</script>