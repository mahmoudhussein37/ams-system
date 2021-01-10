<%@include file="/WEB-INF/view/include/topTag.jsp" %>

<div class="card-body">
<form:form id="file-form" commandName="uploadedFile" action="${baseUrl}/professor/classProgress/attendance/courseDetail?profCourseId=${pc.id}" cssClass="form-horizontal" enctype="multipart/form-data">

    <%@include file="/WEB-INF/view/role/common/professorCourse/basicInfo.jsp" %>

    <c:if test="${isEditable}">
    <div class="row">
        <div class="col-md-12">
            <div class="form-group">
                <label><spring:message code="professor.uploadFile"/></label>
                <form:input type="file" path="file" class="form-control"/>
                <%--<span class="form-text text-muted">We'll never share your email with anyone else</span>--%>


            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-md-12">
            <div class="form-group" style="text-align:right">
                <button type="submit" class="btn btn-primary mr-2 save-btn"><spring:message code="common.save"/></button>
            </div>
        </div>
    </div>
    </c:if>
    <c:if test="${not isEditable}">

    <div class="alert  alert-custom alert-success" role="alert">
        <div class="alert-icon"><i class="flaticon-warning"></i></div>
        <div class="alert-text"><h3><spring:message code="professor.period.attendance"/></h3></div>
    </div>
    </c:if>
</form:form>
</div>



<%@include file="/WEB-INF/view/include/footerScript.jsp" %>
<script>
$(document).ready(function() {


});
</script>