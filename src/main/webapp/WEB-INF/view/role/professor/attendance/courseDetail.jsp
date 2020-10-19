<%@include file="/WEB-INF/view/include/topTag.jsp" %>

<div class="card-body">

    <div class="row">
        <div class="col-md-4">
            <div class="form-group">
                <label><spring:message code="common.courseCode"/></label>
                <input type="text" class="form-control" value="${course.code}" disabled/>
                <%--<span class="form-text text-muted">Please enter your full name</span>--%>
            </div>






        </div>
        <div class="col-md-4">
            <div class="form-group">
                <label><spring:message code="common.courseTitle"/></label>
                <input type="text" class="form-control" value="${course.title}" disabled/>
                <%--<span class="form-text text-muted">We'll never share your email with anyone else</span>--%>
            </div>


        </div>
        <div class="col-md-4">
            <div class="form-group">
                <label><spring:message code="common.credit"/></label>
                <input type="text" class="form-control" value="${course.credit}" disabled/>
                <%--<span class="form-text text-muted">We'll never share your email with anyone else</span>--%>
            </div>

        </div>
    </div>


    <div class="row">
        <div class="col-md-12">
            <div class="form-group">
                <label><spring:message code="professor.uploadFile"/></label>
                <input type="file" class="form-control"/>
                <%--<span class="form-text text-muted">We'll never share your email with anyone else</span>--%>


            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-md-12">
            <div class="form-group" style="text-align:right">
                <button type="button" class="btn btn-primary mr-2"><spring:message code="common.save"/></button>
            </div>
        </div>
    </div>
</div>



<%@include file="/WEB-INF/view/include/footerScript.jsp" %>
<script>
$(document).ready(function() {

});
</script>