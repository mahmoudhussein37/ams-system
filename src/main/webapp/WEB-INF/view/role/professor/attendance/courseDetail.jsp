<%@include file="/WEB-INF/view/include/topTag.jsp" %>

<div class="card-body">
<form:form id="file-form" commandName="uploadedFile" action="${baseUrl}/professor/classProgress/attendance/courseDetail?courseId=${pc.id}" cssClass="form-horizontal" enctype="multipart/form-data">

    <div class="row">
        <div class="col-md-3">
            <div class="form-group">
                <label><spring:message code="common.courseCode"/></label>
                <input type="text" class="form-control" value="${pc.course.code}" disabled/>
                <%--<span class="form-text text-muted">Please enter your full name</span>--%>
            </div>






        </div>
        <div class="col-md-3">
            <div class="form-group">
                <label><spring:message code="common.courseTitle"/></label>
                <input type="text" class="form-control" value="${pc.course.title}" disabled/>
                <%--<span class="form-text text-muted">We'll never share your email with anyone else</span>--%>
            </div>


        </div>
        <div class="col-md-3">
            <div class="form-group">
                <label><spring:message code="common.divide"/></label>
                <input type="text" class="form-control" value="${pc.divide}" disabled/>
                    <%--<span class="form-text text-muted">We'll never share your email with anyone else</span>--%>
            </div>


        </div>
        <div class="col-md-3">
            <div class="form-group">
                <label><spring:message code="common.credit"/></label>
                <input type="text" class="form-control" value="${pc.course.credit}" disabled/>
                <%--<span class="form-text text-muted">We'll never share your email with anyone else</span>--%>
            </div>

        </div>
    </div>


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