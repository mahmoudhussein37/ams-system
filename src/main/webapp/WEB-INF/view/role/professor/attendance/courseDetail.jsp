<%@include file="/WEB-INF/view/include/topTag.jsp" %>

<div class="card-body">
<form:form id="file-form" commandName="uploadedFile" action="${baseUrl}/professor/classProgress/attendance/courseDetail?profCourseId=${pc.id}" cssClass="form-horizontal" enctype="multipart/form-data">

    <%@include file="/WEB-INF/view/role/common/professorCourse/basicInfo.jsp" %>


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
</form:form>
</div>



<%@include file="/WEB-INF/view/include/footerScript.jsp" %>
<script>
$(document).ready(function() {


});
</script>