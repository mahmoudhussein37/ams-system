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
                            <h3 class="card-title font-weight-bolder">
                                <a class="btn btn-light" href="${baseUrl}/admin/courseManagement/cOpen/manageDivide?courseId=${profCourse.courseId}"><i class="fa fa-arrow-left"></i> <spring:message code="common.back"/></a> &nbsp;&nbsp;&nbsp;[${profCourse.semester.year} - ${profCourse.semester.semester}] ${profCourse.course.code}: ${profCourse.course.title} - <spring:message code="common.divide"/> : ${profCourse.divide}</h3>
                            </h3>


                        </div>
                        <div class="card-body">
                            <h3 class="font-size-lg text-dark font-weight-bold mb-6"><spring:message code="admin.createdTimeTable"/></h3>
                            <div class="row">
                                <div class="col-md-12">
                                    <div class="form-group">
                                        <table class="table rounded" id="course-list">
                                            <thead>
                                            <tr class="table-secondary text-center">
                                                <th class="pl-0" style=""><spring:message code="common.weekday"/></th>
                                                <td>
                                                    <spring:message code="common.startPeriod"/>
                                                </td>
                                                <td>
                                                    <spring:message code="common.endPeriod"/>
                                                </td>
                                                <td>

                                                </td>


                                            </tr>
                                            </thead>
                                            <tbody>
                                            <c:forEach var="ct" items="${classTimes}" varStatus="varStatus">
                                            <tr class="text-center">
                                                <td>
                                                        <spring:message code="common.week${ct.w}"/>
                                                </td>
                                                <td>
                                                        ${ct.s}
                                                </td>
                                                <td>
                                                        ${ct.e}
                                                </td>

                                                <td>
                                                    <button style="width:100%;margin-bottom:10px;" class="btn btn-light btm-sm delete-row-btn" data-id="${ct.id}"><spring:message code="common.delete"/></button>
                                                </td>
                                            </tr>
                                            </c:forEach>

                                        </table>


                                    </div>
                                </div>
                            </div>

                            <br/><br/>
                            <div class="separator separator-solid my-5"></div>
                            <br/><br/>
                            <form:form modelAttribute="classTime" action="${baseUrl}/admin/courseManagement/cOpen/manageTime?profCourseId=${profCourse.id}" method="post" class="form">
                                <form:hidden path="profCourseId" value="${profCourse.id}"/>
                                <h3 class="font-size-lg text-dark font-weight-bold mb-6"><spring:message code="admin.createTime"/></h3>
                                <div class="row">
                                    <div class="col-md-3">
                                        <div class="form-group">
                                            <label><spring:message code="common.courseCode"/></label>
                                            <input type="text" class="form-control" disabled value="${profCourse.course.code}"/>
                                                <%--<span class="form-text text-muted">Please enter your full name</span>--%>
                                        </div>
                                    </div>
                                    <div class="col-md-3">
                                        <div class="form-group">
                                            <label><spring:message code="common.courseTitle"/></label>
                                            <input type="text" class="form-control" disabled value="${profCourse.course.title}"/>
                                                <%--<span class="form-text text-muted">Please enter your full name</span>--%>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-3">
                                        <div class="form-group">
                                            <label><spring:message code="common.weekday"/></label>
                                            <form:select path="w" class="form-control" >
                                                <c:forEach var="s" begin="0" end="6">
                                                    <form:option value="${s}"><spring:message code="common.week${s}"/></form:option>
                                                </c:forEach>
                                            </form:select>
                                        </div>
                                    </div>
                                    <div class="col-md-3">

                                        <div class="form-group">
                                            <label><spring:message code="common.startPeriod"/></label>
                                            <form:select path="s" class="form-control">
                                                <c:forEach var="d" begin="0" end="10">
                                                    <form:option value="${d}">${d}</form:option>
                                                </c:forEach>
                                            </form:select>
                                                <%--<span class="form-text text-muted">We'll never share your email with anyone else</span>--%>
                                        </div>
                                    </div>
                                    <div class="col-md-3">
                                        <div class="form-group">
                                            <label><spring:message code="common.endPeriod"/></label>
                                            <form:select path="e" class="form-control">
                                                <c:forEach var="d" begin="0" end="10">
                                                    <form:option value="${d}">${d}</form:option>
                                                </c:forEach>
                                            </form:select>
                                        </div>
                                    </div>

                                </div>

                                <button type="submit" class="btn btn-primary mr-2"><spring:message code="common.create"/></button>
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

    //$("#course-list").DataTable();

    $(document).ready(function() {
        <c:if test="${not empty result}">
        alert("<spring:message code='common.success' javaScriptEscape="true" />");
        location.href="${baseUrl}/admin/courseManagement/cOpen/manageTime?profCourseId=${profCourse.id}";
        </c:if>
        $("body").on("click", ".delete-row-btn", function(e) {
            e.preventDefault();
            var id = $(this).attr("data-id");
            $.post("${baseUrl}/admin/courseManagement/cOpen/manageTime/deleteTime?id=" + id, function(result) {
                alert("<spring:message code="common.success" javaScriptEscape="true" />");

                location.reload();

            });
        });


    });

</script>
</body>
</html>