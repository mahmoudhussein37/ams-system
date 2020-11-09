<%@include file="/WEB-INF/view/include/topTag.jsp" %>
<%@include file="/WEB-INF/view/include/head.jsp" %>
<!--end::Head-->

<!--begin::Body-->
<body id="kt_body"  class="header-fixed header-mobile-fixed page-loading"  >
<%@include file="/WEB-INF/view/include/headerBar.jsp" %>
<!--begin::Content-->
<div class="content  d-flex flex-column flex-column-fluid" id="kt_content">
    <!--begin::Entry-->
    <div class="d-flex flex-column-fluid">
        <!--begin::Container-->
        <div class=" container ">
            <div class="row">
                <div class="col-md-12">
                    <!--begin::Card-->
                    <div class="card card-custom">
                        <div class="card-header">
                            <h3 class="card-title font-weight-bolder"><a class="btn btn-light" href="${baseUrl}/admin/academicManagement/assessmentFactor"><i class="fa fa-arrow-left"></i> <spring:message code="common.back"/></a> &nbsp;&nbsp;&nbsp;${course.code}: ${course.title}</h3>

                        </div>
                        <div class="card-body">
                            <h3 class="font-size-lg text-dark font-weight-bold mb-6"><spring:message code="admin.createdAssessmentFactor"/></h3>
                            <div class="row">
                                <div class="col-md-12">
                                    <div class="form-group">
                                        <table class="table rounded">
                                            <thead>
                                            <tr class="table-secondary text-center">

                                                <td>
                                                    <spring:message code="admin.analyticalStandard"/>
                                                </td>
                                                <td>
                                                    <spring:message code="admin.question"/>

                                                </td>
                                                <td>

                                                </td>


                                            </tr>
                                            </thead>
                                            <tbody>
                                            <c:forEach var="af" items="${assessmentFactors}" varStatus="varStatus">
                                                <c:if test="${af.enabled}">
                                                    <tr class="text-center">
                                                        <td>
                                                            ${af.title}
                                                        </td>
                                                        <td>
                                                                ${af.question}
                                                        </td>
                                                        <td>
                                                            <button class="btn btn-light btm-sm change-status-row-btn" data-id="${af.id}" data-to-status="false"><spring:message code="common.disable"/></button>
                                                            <button class="btn btn-light btm-sm delete-row-btn" data-id="${af.id}"><spring:message code="common.delete"/></button>
                                                        </td>
                                                    </tr>

                                                </c:if>

                                            </c:forEach>
                                            <c:forEach var="af" items="${assessmentFactors}" varStatus="varStatus">
                                                <c:if test="${not af.enabled}">
                                                    <tr class="text-center disabled-tr">
                                                        <td>
                                                                ${af.title}
                                                        </td>
                                                        <td>
                                                                ${af.question}
                                                        </td>
                                                        <td>
                                                            <button class="btn btn-light btm-sm change-status-row-btn" data-id="${af.id}" data-to-status="true"><spring:message code="common.enable"/></button>
                                                        </td>
                                                    </tr>
                                                </c:if>

                                            </c:forEach>
                                            <%--<tr class="table-light text-center">
                                                <td>1</td>
                                                <td>
                                                    Design/Material
                                                </td>
                                                <td>
                                                    It proceed according to the proposed syllabus
                                                </td>

                                            </tr>


                                            </tbody>--%>
                                        </table>


                                    </div>
                                </div>
                            </div>

                            <br/><br/>
                            <div class="separator separator-solid my-5"></div>
                            <br/><br/>
                            <form:form modelAttribute="assessmentFactor" action="${baseUrl}/admin/academicManagement/assessmentFactor/manageAf?courseId=${course.id}" method="post" class="assessment-factor-form">
                                <h3 class="font-size-lg text-dark font-weight-bold mb-6"><spring:message code="admin.createAssessmentFactor"/></h3>
                                <div class="row">
                                    <div class="col-md-3">
                                        <div class="form-group">
                                            <label><spring:message code="common.courseCode"/></label>
                                            <form:input path="courseId" type="text" class="form-control" disabled="true"/>
                                                <%--<span class="form-text text-muted">Please enter your full name</span>--%>
                                        </div>
                                    </div>
                                    <div class="col-md-3">

                                        <div class="form-group">
                                            <label><spring:message code="common.courseTitle"/></label>
                                            <input type="text" class="form-control"  value="${course.title}" disabled="true"/>
                                                <%--<span class="form-text text-muted">We'll never share your email with anyone else</span>--%>
                                        </div>
                                    </div>

                                </div>

                                <div class="row">
                                    <div class="col-md-12">
                                        <div class="form-group">
                                            <label><spring:message code="admin.analyticalStandard"/></label>
                                            <form:input path="title" type="text" class="form-control"/>
                                                <%--<span class="form-text text-muted">Please enter your full name</span>--%>
                                        </div>
                                    </div>


                                </div>
                                <div class="row">
                                    <div class="col-md-12">
                                        <div class="form-group">
                                            <label><spring:message code="admin.question"/></label>
                                            <form:input path="question" type="text" class="form-control"/>
                                                <%--<span class="form-text text-muted">Please enter your full name</span>--%>
                                        </div>
                                    </div>


                                </div>
                                <button type="submit" class="btn btn-primary mr-2 submit-assessment-factor"><spring:message code="common.submit"/></button>
                            </form:form>


                        </div>
                    </div>
                    <!--end::Card-->
                </div>
            </div>


        </div>
        <!--end::Container-->
    </div>
    <!--end::Entry-->
</div>
<!--end::Content-->

<%@include file="/WEB-INF/view/include/footerBar.jsp" %>


<%@include file="/WEB-INF/view/include/userPanel.jsp" %>


<%@include file="/WEB-INF/view/include/footerScript.jsp" %>

<script>



    $(document).ready(function() {
        $("body").on("click", ".delete-row-btn", function(e) {
            e.preventDefault();
            var id = $(this).attr("data-id");
            $.post("${baseUrl}/admin/academicManagement/assessmentFactor/manageAf/deleteAf?id=" + id, function(result) {
                location.reload();
            });
        });

        $("body").on("click", ".change-status-row-btn", function(e) {
            e.preventDefault();
            var id = $(this).attr("data-id");
            var status = $(this).attr("data-to-status");
            $.post("${baseUrl}/admin/academicManagement/assessmentFactor/manageAf/changeStatus?id=" + id + "&status=" + status, function(result) {
                if(result == true) {
                    location.reload();
                }

            });
        });
    });

</script>
</body>
</html>