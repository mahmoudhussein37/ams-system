<%@include file="/WEB-INF/view/include/topTag.jsp" %>

<h3 class="font-size-lg text-dark font-weight-bold mb-6"><spring:message code="admin.createdAssessmentFactor"/></h3>
<div class="row">
    <div class="col-md-12">
        <div class="form-group">
            <table class="table rounded">
                <thead>
                <tr class="table-secondary text-center">

                    <td><spring:message code="common.no"/></td>
                    <td>
                        Analytical Standard
                    </td>
                    <td>
                        Question
                    </td>


                </tr>
                </thead>
                <tbody>
                <tr class="table-light text-center">
                    <td>1</td>
                    <td>
                        Design/Material
                    </td>
                    <td>
                        It proceed according to the proposed syllabus
                    </td>

                </tr>


                </tbody>
            </table>


        </div>
    </div>
</div>

<br/><br/>
<div class="separator separator-solid my-5"></div>
<br/><br/>
<form:form modelAttribute="course" action="${baseUrl}/admin/courseManagement/courseDetail" method="post" class="form">
    <h3 class="font-size-lg text-dark font-weight-bold mb-6"><spring:message code="admin.createAssessmentFactor"/></h3>
    <div class="row">
        <div class="col-md-3">
            <div class="form-group">
                <label><spring:message code="common.courseCode"/></label>
                <form:input path="code" type="text" class="form-control" disabled="true"/>
                    <%--<span class="form-text text-muted">Please enter your full name</span>--%>
            </div>
        </div>
        <div class="col-md-3">

            <div class="form-group">
                <label><spring:message code="common.courseTitle"/></label>
                <form:input path="title" type="text" class="form-control" disabled="true"/>
                    <%--<span class="form-text text-muted">We'll never share your email with anyone else</span>--%>
            </div>
        </div>

    </div>

    <div class="row">
        <div class="col-md-12">
            <div class="form-group">
                <label>Analytical Standard</label>
                <input type="text" class="form-control" />
                    <%--<span class="form-text text-muted">Please enter your full name</span>--%>
            </div>
        </div>


    </div>
    <div class="row">
        <div class="col-md-12">
            <div class="form-group">
                <label>Question</label>
                <input type="text" class="form-control" />
                    <%--<span class="form-text text-muted">Please enter your full name</span>--%>
            </div>
        </div>


    </div>
    <button type="submit" class="btn btn-primary mr-2"><spring:message code="common.submit"/></button>
</form:form>

<%@include file="/WEB-INF/view/include/footerScript.jsp" %>
<script>

</script>